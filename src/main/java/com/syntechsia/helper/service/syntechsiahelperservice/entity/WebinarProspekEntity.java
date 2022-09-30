package com.syntechsia.helper.service.syntechsiahelperservice.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Builder(toBuilder = true)
@Entity(name = "WEBINAR_PROSPEK")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebinarProspekEntity {

    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String education;
    private String activity;
    private String invitedStatus;

    @PrePersist
    public void onCreated(){
        this.id = UUID.randomUUID().toString();
    }
}
