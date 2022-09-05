package com.syntechsia.helper.service.syntechsiahelperservice.service.impl;

import com.syntechsia.helper.service.syntechsiahelperservice.entity.StudentEntity;
import com.syntechsia.helper.service.syntechsiahelperservice.repository.StudentRepository;
import com.syntechsia.helper.service.syntechsiahelperservice.service.StudentService;
import com.syntechsia.helper.service.syntechsiahelperservice.util.ConstantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentEntity> getAllStudentByStatusSendEmail(String statuSendEmail) {
        log.info("start get all students with status send email : {}", statuSendEmail);
        List<StudentEntity> studentEntities;
        try {
           studentEntities =  studentRepository.findAllByStatusSendEmail(statuSendEmail);
        }catch (Exception e){
            log.error("error get all students with error",e.getMessage());
            studentEntities =  null;
        }
        return studentEntities;
    }

    @Override
    public boolean updateStudent(Long id) {
        log.info("start update status send email student with id {}",id);
        StudentEntity studentEntity;
        boolean response = Boolean.FALSE;
        try {
            studentEntity = studentRepository.getReferenceById(id);
            if (!ObjectUtils.isEmpty(studentEntity)){
                studentEntity.setStatusSendEmail(ConstantUtil.SUCCESS);
                studentRepository.save(studentEntity);
                response = Boolean.TRUE;
            }
        }catch (Exception e){
            log.error("error update status send email student with error {}", e.getMessage());
        }
        return response;
    }


}
