package com.syntechsia.helper.service.syntechsiahelperservice.repository;

import com.syntechsia.helper.service.syntechsiahelperservice.entity.WebinarProspekEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebinarProspekRepository extends JpaRepository<WebinarProspekEntity, String> {
    List<WebinarProspekEntity> findAllByInvitedStatus(String statusSendEmail);
}
