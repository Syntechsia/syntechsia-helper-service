package com.syntechsia.helper.service.syntechsiahelperservice.repository;

import com.syntechsia.helper.service.syntechsiahelperservice.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    List<StudentEntity> findAllByStatusSendEmail(String statusSendEmail);
}
