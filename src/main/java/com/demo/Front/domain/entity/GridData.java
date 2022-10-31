package com.demo.Front.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class GridData {
    @Id
    private Long id;
    private Long clientKey;
    private Long deviceKey;
    private Long adminKey;
    private Long managerKey;
    private Long messageKey;
    private String clientName;
    private String deviceId;
    private String cameraId;
    private String adminName;
    private String managerName;
    private String msgType;
    private String event385;
    private String event386;
    private String event387;
    private String event388;
    private String event389;
    private String event390;
    private String event391;
    private String event394;
    private String event395;
    private String event396;
    private String event398;
    private String event641;
    private String event705;
    private String event101;
    private String event102;
    private String event103;
    private Long event385_key;
    private Long event386_key;
    private Long event387_key;
    private Long event388_key;
    private Long event389_key;
    private Long event390_key;
    private Long event391_key;
    private Long event394_key;
    private Long event395_key;
    private Long event396_key;
    private Long event398_key;
    private Long event641_key;
    private Long event705_key;
    private Long event101_key;

    private Long event102_key;

    private Long event103_key;

}
