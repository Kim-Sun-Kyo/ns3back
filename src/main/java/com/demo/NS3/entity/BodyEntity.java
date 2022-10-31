package com.demo.NS3.entity;

import com.demo.NS3.vo.ApiBodyVo;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "std_event_record_push")
public class BodyEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long Id;
    @Column(name = "camera_name")
    private String cameraname;
    @Column(name = "device_id")
    private String deviceid;
    private Long channel;
    private String events_type;
    private String events_off_type;
    private Long events_status;
    private Long obj_label;
    private Long pos_bottom;
    private Long pos_top;
    private Long pos_left;
    private Long pos_right;
    private Long quality;
    private String snap_feat;
    @Column(name = "snap_id")
    private String snapid;
    private String snap_path;
    private Long position;
    private String trigger;
    @Column(name = "send_flag")
    private String sendflag;
    private String events_type_org;
//    @CreatedDate
//    @Column(updatable = false)
//    private LocalDateTime if_date;
    public BodyEntity(ApiBodyVo vo) {
        this.cameraname = vo.getCamera_name();
        this.deviceid = vo.getDevice_id();
        this.channel = vo.getChannel();
        this.events_type = vo.getEvents_type();
        this.events_off_type = vo.getEvents_off_type();
        this.events_status = vo.getEvents_status();
        this.obj_label = vo.getObj_label();
        this.pos_bottom = vo.getPos_bottom();
        this.pos_top = vo.getPos_top();
        this.pos_left = vo.getPos_left();
        this.pos_right = vo.getPos_right();
        this.quality = vo.getQuality();
        this.snap_feat = vo.getSnap_feat();
        this.snapid = vo.getSnap_id();
        this.snap_path = vo.getSnap_path();
        this.position = vo.getChannel();
        this.trigger = vo.getTrigger();
        this.sendflag="R";
    }
}
