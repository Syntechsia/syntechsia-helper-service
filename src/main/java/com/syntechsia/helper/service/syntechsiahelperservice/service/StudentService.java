package com.syntechsia.helper.service.syntechsiahelperservice.service;

import com.syntechsia.helper.service.syntechsiahelperservice.entity.StudentEntity;

import java.util.List;

public interface StudentService {
    List<StudentEntity> getAllStudentByStatusSendEmail(String statuSendEmail);
    boolean updateStudent(Long id);

    void sendEmail(String statusSendEmail);
}
