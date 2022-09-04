package com.syntechsia.helper.service.syntechsiahelperservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailResponse {

    private String message;
    private String status;

    public EmailResponse(String message, String status) {
        this.message = message;
        this.status = status;
    }
}
