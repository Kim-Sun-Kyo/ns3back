package com.demo.SMS.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EventSender {
    @Id
    private Long id;
    private String clientName;
    private String adminName;
    private String managerName;
    private String phoneNumber;
    private String kakaoId;
    private String kakaoUuid;
    private String kakaoEmail;
    private String kakaoNickname;
    private boolean kakaoFlag;
    private String cameraName;
    private String deviceName;
    private String content;
    private String msgType;
    private String msgTypeNew;

    public EventSender(String managerName, String phoneNumber, String kakaoId, String kakaoUuid, String kakaoEmail, String kakaoNickname, boolean kakaoFlag, String cameraName, String deviceName, String content, String msgType, String msgTypeNew) {
        this.managerName = managerName;
        this.phoneNumber = phoneNumber;
        this.kakaoId = kakaoId;
        this.kakaoUuid = kakaoUuid;
        this.kakaoEmail = kakaoEmail;
        this.kakaoNickname = kakaoNickname;
        this.kakaoFlag = kakaoFlag;
        this.cameraName = cameraName;
        this.deviceName = deviceName;
        this.content = content;
        this.msgType = msgType;
        this.msgTypeNew = msgTypeNew;
    }
}
