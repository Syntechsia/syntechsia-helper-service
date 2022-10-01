package com.syntechsia.helper.service.syntechsiahelperservice.scheduler;

import com.syntechsia.helper.service.syntechsiahelperservice.service.EmailService;
import com.syntechsia.helper.service.syntechsiahelperservice.util.ConstantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SendEmailScheduler {

    private final EmailService emailService;

    @Autowired
    public SendEmailScheduler(EmailService emailService) {
        this.emailService = emailService;
    }

//    @Scheduled(cron = "${cron.send.email}")
    public void sendEmailExecuter(){
        log.info("send email scheduler start");
        emailService.sendEmailByScheduler(ConstantUtil.FAILED);
        log.info("send email scheduler end");
    }

}
