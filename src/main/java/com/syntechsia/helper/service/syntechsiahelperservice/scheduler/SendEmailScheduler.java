package com.syntechsia.helper.service.syntechsiahelperservice.scheduler;

import com.syntechsia.helper.service.syntechsiahelperservice.service.StudentService;
import com.syntechsia.helper.service.syntechsiahelperservice.util.ConstantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SendEmailScheduler {
    private StudentService studentService;

    @Autowired
    public SendEmailScheduler(StudentService studentService) {
        this.studentService = studentService;
    }

    @Scheduled(cron = "${cron.send.email}")
    public void sendEmailExecuter(){
        log.info("send email scheduler start");
        studentService.sendEmail(ConstantUtil.FAILED);
        log.info("send email scheduler end");
    }

}
