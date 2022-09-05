package com.syntechsia.helper.service.syntechsiahelperservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "student")
@Data
@NoArgsConstructor
public class StudentEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nik;
    private String name;
    private Integer gender;
    private Date dateOfBirth;
    private String phoneNumber;
    private String email;
    private String education;
    private String address;
    private String payment;
    private String batch;
    private String program;
    private Integer status;
    private Date createdDate;
    private Date updatedDate;
    private String statusSendEmail;
}
