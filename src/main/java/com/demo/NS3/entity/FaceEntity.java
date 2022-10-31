package com.demo.NS3.entity;

import com.demo.NS3.vo.ApiFaceVo;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@RequiredArgsConstructor

@Table(name = "std_face_record_push")
public class FaceEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long alarmType;
    private Long aliveType;
    private Long appearCount;
    private String cameraName;
    private Long channel;
    private String deviceId;
    private Long eventType;
    private String imgId;
    private String imgPath;
    private Long libId;
    private String libName;
    private Long libType;
    private Long objLabel;
    private Long oraPosBottom;
    private Long oraPosLeft;
    private Long oraPosRight;
    private Long oraPosTop;
    private String personAddr;
    private String personAge;
    private String personGender;
    private String personIdcard;
    private String personName;
    private Long posBottom;
    private Long posLeft;
    private Long posRight;
    private Long posTop;
    private String position;
    private Long quality;
    private Long ranking;
    private Long similarity;
    private String snapFeat;
    private String snapId;
    private String snapPath;
    private Long strangerAppearChannel;
    private String threshold;
    private String trigger;
    private String wanderChannels;
    private String wanderDeviceID;
    private Long wanderThreshold;
    private String wanderTrigger;
    private String capStyle;
    private String genderCode;
    private String glassStyle;
    private String mustacheStyle;
    private String respiratorColor;
    private String stAge;
    private String stAgeValue;
    private String stExpression;
    private String stHelmetStyle;
    private String stRespirator;

    private String sendFlag;
    private String localPath;

    public FaceEntity(ApiFaceVo vo){
        this.alarmType=vo.getAlarm_type();
        this.aliveType=vo.getAlive_type();
        this.appearCount=vo.getAppear_count();
        this.cameraName=vo.getCamera_name();
        this.channel=vo.getChannel();
        this.deviceId=vo.getDevice_id();
        this.eventType=vo.getEvent_type();
        this.imgId=vo.getImg_id();
        this.imgPath=vo.getImg_path();
        this.libId=vo.getLib_id();
        this.libName=vo.getLib_name();
        this.libType=vo.getLib_type();
        this.objLabel=vo.getObj_label();
        this.oraPosBottom=vo.getOra_pos_bottom();
        this.oraPosLeft=vo.getOra_pos_left();
        this.oraPosRight=vo.getOra_pos_right();
        this.oraPosTop=vo.getOra_pos_top();
        this.personAddr=vo.getPerson_addr();
        this.personAge=vo.getPerson_age();
        this.personGender=vo.getPerson_gender();
        this.personIdcard=vo.getPerson_idcard();
        this.personName=vo.getPerson_name();
        this.posBottom=vo.getPos_bottom();
        this.posLeft=vo.getPos_left();
        this.posRight=vo.getPos_right();
        this.posTop=vo.getPos_top();
        this.position=vo.getPosition();
        this.quality=vo.getQuality();
        this.ranking=vo.getRanking();
        this.similarity=vo.getSimilarity();
        this.snapFeat=vo.getSnap_feat();
        this.snapId=vo.getSnap_id();
        this.snapPath=vo.getSnap_path();
        this.strangerAppearChannel=vo.getStranger_appear_channel();
        this.threshold=vo.getThreshold();
        this.trigger=vo.getTrigger();
        this.wanderChannels=vo.getWander_channels();
        this.wanderDeviceID=vo.getWander_deviceID();
        this.wanderThreshold=vo.getWander_thresHold();
        this.wanderTrigger=vo.getWander_trigger();
        this.capStyle=vo.face_attr.getCap_style();
        this.genderCode=vo.face_attr.gender_code;
        this.glassStyle=vo.face_attr.glass_style;
        this.mustacheStyle=vo.face_attr.getMustache_style();
        this.respiratorColor=vo.face_attr.getRespirator_color();
        this.stAge=vo.face_attr.getSt_age();
        this.stAgeValue=vo.face_attr.getSt_age_value();
        this.stExpression=vo.face_attr.getSt_expression();
        this.stHelmetStyle=vo.face_attr.getSt_helmet_style();
        this.stRespirator=vo.face_attr.getSt_respirator();

        this.sendFlag="R";
    }



}
