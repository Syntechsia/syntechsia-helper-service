package com.syntechsia.helper.service.syntechsiahelperservice.service.impl;

import com.syntechsia.helper.service.syntechsiahelperservice.entity.StudentEntity;
import com.syntechsia.helper.service.syntechsiahelperservice.entity.TemplateEntity;
import com.syntechsia.helper.service.syntechsiahelperservice.entity.WebinarProspekEntity;
import com.syntechsia.helper.service.syntechsiahelperservice.model.EmailRequest;
import com.syntechsia.helper.service.syntechsiahelperservice.model.EmailResponse;
import com.syntechsia.helper.service.syntechsiahelperservice.repository.TemplateRepository;
import com.syntechsia.helper.service.syntechsiahelperservice.repository.WebinarProspekRepository;
import com.syntechsia.helper.service.syntechsiahelperservice.service.EmailService;
import com.syntechsia.helper.service.syntechsiahelperservice.service.StudentService;
import com.syntechsia.helper.service.syntechsiahelperservice.util.ConstantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;


@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.host}")
    public String mailHost;
    @Value("${spring.mail.port}")
    public int mailPort;
    @Value("${spring.mail.username}")
    public String mailUsername;
    @Value("${spring.mail.password}")
    public String mailPassword;

    private final TemplateRepository templateRepository;
    private final WebinarProspekRepository webinarProspekRepository;
    private final StudentService studentService;

    @Autowired
    public EmailServiceImpl(TemplateRepository templateRepository, WebinarProspekRepository webinarProspekRepository, StudentService studentService) {
        this.templateRepository = templateRepository;
        this.webinarProspekRepository = webinarProspekRepository;
        this.studentService = studentService;
    }

    @Override
    public EmailResponse sendEmail(EmailRequest emailRequest) {
        log.info("Start sending email with request {}", emailRequest);
        boolean html = true;
        EmailResponse response;
        try {
            TemplateEntity templateEntity = templateRepository.findByTemplateName(emailRequest.getTemplateName());
            if (!ObjectUtils.isEmpty(templateEntity)) {
                JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
                mailSender.setHost(mailHost);
                mailSender.setPort(mailPort);
                mailSender.setUsername(mailUsername);
                mailSender.setPassword(mailPassword);

                Properties properties = new Properties();
                properties.setProperty("mail.smtp.auth", String.valueOf(Boolean.TRUE));
                properties.setProperty("mail.smtp.starttls.enable", String.valueOf(Boolean.TRUE));

                mailSender.setJavaMailProperties(properties);
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message);
                helper.setFrom(mailUsername);
                helper.setTo(emailRequest.getEmailTo());
                switch (emailRequest.getTemplateName()) {
                    case "registerTemplate" :
                        helper.setSubject("Selamat "
                                .concat(emailRequest.getStudentName().toUpperCase())
                                .concat("!")
                                .concat( " siap berkarir sebagai "
                                        .concat(emailRequest.getProgramName())
                                        .concat(" dengan Syntechsia Academy?")));
                        helper.setText(templateEntity.getTemplate()
                                .replace("{STUDENT_NAME}", emailRequest.getStudentName().toUpperCase())
                                .replace("{PROGRAM_NAME}", emailRequest.getProgramName())
                                .replace("{NIK}", emailRequest.getNik()), html);
                    break;
                    case "WEBINAR" :
                        helper.setSubject("Undangan untuk mengikuti webinar dari Syntechsia Academy!");
                        helper.setText(templateEntity.getTemplate().replace("{NAME}", emailRequest.getStudentName().toUpperCase()), html);
                    break;
                    default:
                        log.info("Template not found");
                }
                mailSender.send(message);
                response = new EmailResponse(ConstantUtil.SUCCESS, ConstantUtil.SUCCESS_STATUS);
            } else {
                log.info("template not found for template name {}", emailRequest.getTemplateName());
                response = new EmailResponse(ConstantUtil.FAILED, ConstantUtil.FAILED_STATUS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error sending email : ", e);
            response = new EmailResponse("Error : ".concat(e.getMessage()), ConstantUtil.FAILED_STATUS);
        }
        return response;
    }

    @Override
    public void sendEmailByScheduler(String statusSendEmail) {
        List<StudentEntity> students = studentService.getAllStudentByStatusSendEmail(statusSendEmail);
        for (StudentEntity data: students) {
            EmailRequest emailRequest = new EmailRequest(data.getEmail(),data.getName(),data.getNik(), data.getProgram(), "registerTemplate");
            if (sendEmail(emailRequest).getStatus().equals(ConstantUtil.SUCCESS_STATUS)){
                studentService.updateStudent(data.getId());
            }
        }
    }

    @Override
    public void sendEmailWebinarByScheduler(String invitedStatus) {
        List<WebinarProspekEntity> webinarProspekEntities = webinarProspekRepository.findAllByInvitedStatus(invitedStatus);
        for (WebinarProspekEntity data : webinarProspekEntities) {
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setEmailTo(data.getEmail());
            emailRequest.setStudentName(data.getName());
            emailRequest.setTemplateName("WEBINAR");
            if (sendEmail(emailRequest).getStatus().equals(ConstantUtil.SUCCESS_STATUS)) {
                data.setInvitedStatus(ConstantUtil.DONE_INVITE);
                webinarProspekRepository.save(data);
            }
        }
    }
}
