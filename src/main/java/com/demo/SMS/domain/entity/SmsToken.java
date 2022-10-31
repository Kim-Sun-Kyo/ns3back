package com.demo.SMS.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SmsToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String payload;

    private String accessToken;

    private String snapId;

    private String cameraName;

    private String deviceId;

    private String clientName;

    @Builder
    public SmsToken(String payload, String accessToken, String snapId, String cameraName, String deviceId, String clientName) {
        this.payload = payload;
        this.accessToken = accessToken;
        this.snapId = snapId;
        this.cameraName = cameraName;
        this.deviceId = deviceId;
        this.clientName = clientName;
    }
}
