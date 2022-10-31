package com.demo.SMS.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Getter
@NoArgsConstructor
public class MessageRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clientName;
    private String deviceName;
    private String cameraName;
    private String adminName;
    private String managerName;
    private String eventType;
    private String msgType;
    private String trigger;
    private String sendTime;
    private String successYn;

    @Builder
    public MessageRecord(String clientName, String deviceName, String cameraName, String adminName, String managerName, String eventType, String msgType, String trigger, String sendTime, String successYn) {
        this.clientName=clientName;
        this.deviceName=deviceName;
        this.cameraName=cameraName;
        this.adminName=adminName;
        this.managerName=managerName;
        this.eventType=eventType;
        this.msgType=msgType;
        this.trigger=trigger;
        this.sendTime=sendTime;
        this.successYn=successYn;
    }
}
