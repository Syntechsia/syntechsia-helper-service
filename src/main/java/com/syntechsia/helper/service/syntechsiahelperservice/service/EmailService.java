package com.syntechsia.helper.service.syntechsiahelperservice.service;

import com.syntechsia.helper.service.syntechsiahelperservice.model.EmailRequest;
import com.syntechsia.helper.service.syntechsiahelperservice.model.EmailResponse;

public interface EmailService {

    public EmailResponse sendEmail(EmailRequest emailRequest);
    void sendEmailByScheduler(String statusSendEmail);
    void sendEmailWebinarByScheduler(String invitedStatus);
}
