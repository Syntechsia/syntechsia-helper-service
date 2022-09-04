package com.syntechsia.helper.service.syntechsiahelperservice.controller;

import com.syntechsia.helper.service.syntechsiahelperservice.model.EmailRequest;
import com.syntechsia.helper.service.syntechsiahelperservice.model.EmailResponse;
import com.syntechsia.helper.service.syntechsiahelperservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send")
public class EmailSenderController {

    private EmailService emailService;

    @Autowired
    public EmailSenderController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/email")
    public EmailResponse sendEmailToStudent(@RequestBody EmailRequest emailRequest) {
       return emailService.sendEmail(emailRequest);
    }
}
