package com.syntechsia.helper.service.syntechsiahelperservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class EmailRequest {
    private String emailTo;
    private String studentName;
    private String nik;
    private String programName;
    private String templateName;

    public EmailRequest(String emailTo, String studentName, String nik, String programName, String templateName) {
        this.emailTo = emailTo;
        this.studentName = studentName;
        this.nik = nik;
        this.programName = programName;
        this.templateName = templateName;
    }
}
