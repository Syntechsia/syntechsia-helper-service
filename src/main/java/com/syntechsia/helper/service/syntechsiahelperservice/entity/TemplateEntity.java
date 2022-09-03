package com.syntechsia.helper.service.syntechsiahelperservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "TEMPLATE")
@Data
@NoArgsConstructor
public class TemplateEntity {

    @Id
    private Long id;
    private String templateName;
    private String template;
}
