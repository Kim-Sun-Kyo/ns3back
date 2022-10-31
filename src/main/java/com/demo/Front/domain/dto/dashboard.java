package com.demo.Front.domain.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.query.NativeQuery;

@Getter
@Setter
public class dashboard {
    private Long id;
    private Long clientKey;
    private Long deviceKey;
    private Long adminKey;
    private Long managerKey;
    private Long messageKey;
    private Long policyKey;
    private String clientName;
    private String deviceId;
    private String cameraId;
    private String adminName;
    private String managerName;
    private String msgType;
    private String eventType;
    private String event_380;
    private String event_390;
    private String event_400;
    private String event_410;
    private String event_420;

    public dashboard(Long id, Long clientKey, Long deviceKey, Long adminKey, Long managerKey, Long messageKey, Long policyKey,
                     String clientName, String deviceId, String cameraId, String adminName, String managerName,
                     String msgType, String eventType, String event_380, String event_390, String event_400, String event_410, String event_420) {
        this.id = id;
        this.clientKey = clientKey;
        this.deviceKey = deviceKey;
        this.adminKey = adminKey;
        this.managerKey = managerKey;
        this.messageKey = messageKey;
        this.policyKey = policyKey;
        this.clientName = clientName;
        this.deviceId = deviceId;
        this.cameraId = cameraId;
        this.adminName = adminName;
        this.managerName = managerName;
        this.msgType = msgType;
        this.eventType = eventType;
        this.event_380 = event_380;
        this.event_390 = event_390;
        this.event_400 = event_400;
        this.event_410 = event_410;
        this.event_420 = event_420;
    }
}