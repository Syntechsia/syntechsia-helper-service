package com.syntechsia.helper.service.syntechsiahelperservice.controller;

import com.syntechsia.helper.service.syntechsiahelperservice.model.EmailRequest;
import com.syntechsia.helper.service.syntechsiahelperservice.model.EmailResponse;
import com.syntechsia.helper.service.syntechsiahelperservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manual")
public class ManualSchedulerController {

    private EmailService emailService;

    @Autowired
    public ManualSchedulerController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send/email")
    public void sendEmailToStudent(@RequestParam String statusSendEmail) {
        emailService.sendEmailByScheduler(statusSendEmail);
    }
}
