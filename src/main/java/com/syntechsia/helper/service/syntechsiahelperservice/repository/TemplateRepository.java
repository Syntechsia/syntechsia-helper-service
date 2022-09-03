package com.syntechsia.helper.service.syntechsiahelperservice.repository;

import com.syntechsia.helper.service.syntechsiahelperservice.entity.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<TemplateEntity, Long> {

    TemplateEntity findByTemplateName(String templateName);
}
