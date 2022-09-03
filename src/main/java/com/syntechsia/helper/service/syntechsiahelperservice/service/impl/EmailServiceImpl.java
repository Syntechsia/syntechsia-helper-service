package com.syntechsia.helper.service.syntechsiahelperservice.service.impl;

import com.syntechsia.helper.service.syntechsiahelperservice.entity.TemplateEntity;
import com.syntechsia.helper.service.syntechsiahelperservice.model.EmailRequest;
import com.syntechsia.helper.service.syntechsiahelperservice.repository.TemplateRepository;
import com.syntechsia.helper.service.syntechsiahelperservice.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
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

    private TemplateRepository templateRepository;

    @Autowired
    public EmailServiceImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Override
    public void sendEmail(EmailRequest emailRequest) {
        boolean html = true;
        log.info("Start sending email with request {}", emailRequest);
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
                helper.setText(templateEntity.getTemplate(), html);
                mailSender.send(message);
            } else {
                log.info("template not found for template name {}", emailRequest.getTemplateName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error : ", e);
        }
    }

}
