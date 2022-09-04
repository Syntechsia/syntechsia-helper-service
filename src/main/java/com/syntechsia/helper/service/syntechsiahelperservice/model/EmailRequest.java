package com.syntechsia.helper.service.syntechsiahelperservice.model;

import lombok.Data;


@Data
public class EmailRequest {

    private String emailTo;
    private String studentName;
    private String nik;
    private String programName;
    private String templateName;
}
