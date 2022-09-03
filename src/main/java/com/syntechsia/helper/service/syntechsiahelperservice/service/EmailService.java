package com.syntechsia.helper.service.syntechsiahelperservice.service;

import com.syntechsia.helper.service.syntechsiahelperservice.model.EmailRequest;

public interface EmailService {

    public void sendEmail(EmailRequest emailRequest);
}
