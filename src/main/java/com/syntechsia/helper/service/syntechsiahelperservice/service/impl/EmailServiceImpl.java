package com.syntechsia.helper.service.syntechsiahelperservice.service.impl;

import com.syntechsia.helper.service.syntechsiahelperservice.entity.TemplateEntity;
import com.syntechsia.helper.service.syntechsiahelperservice.model.EmailRequest;
import com.syntechsia.helper.service.syntechsiahelperservice.model.EmailResponse;
import com.syntechsia.helper.service.syntechsiahelperservice.repository.TemplateRepository;
import com.syntechsia.helper.service.syntechsiahelperservice.service.EmailService;
import com.syntechsia.helper.service.syntechsiahelperservice.util.ConstantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.mail.internet.MimeMessage;
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

    @Autowired
    public EmailServiceImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
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
                mailSender.send(message);
                response = new EmailResponse(ConstantUtil.SUCCESS, ConstantUtil.SUCCESS_STATUS);
            } else {
                log.info("template not found for template name {}", emailRequest.getTemplateName());
                response = new EmailResponse(ConstantUtil.FAILED, ConstantUtil.FAILED_STATUS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error : ", e);
            response = new EmailResponse("Error : ".concat(e.getMessage()), ConstantUtil.FAILED_STATUS);
        }
        return response;
    }

}
