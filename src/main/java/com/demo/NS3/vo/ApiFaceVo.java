package com.demo.NS3.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiFaceVo {
    public Long alarm_type;
    public Long alive_type;
    public Long appear_count;
    public String camera_name;
    public Long channel;
    public String device_id;
    public Long event_type;
    public String img_id;
    public String img_path;
    public Long lib_id;
    public String lib_name;
    public Long lib_type;
    public Long obj_label;
    public Long ora_pos_bottom;
    public Long ora_pos_left;
    public Long ora_pos_right;
    public Long ora_pos_top;
    public String person_addr;
    public String person_age;
    public String person_gender;
    public String person_idcard;
    public String person_name;
    public Long pos_bottom;
    public Long pos_left;
    public Long pos_right;
    public Long pos_top;
    public String position;
    public Long quality;
    public Long ranking;
    public Long similarity;
    public String snap_feat;
    public String snap_id;
    public String snap_path;
    public Long stranger_appear_channel;
    public String threshold;
    public String trigger;
    public String wander_channels;
    public String wander_deviceID;
    public Long wander_thresHold;
    public String wander_trigger;
    public face_attr face_attr;
    @Data
    public class face_attr{
        public String cap_style;
        public String gender_code;
        public String glass_style;
        public String mustache_style;
        public String respirator_color;
        public String st_age;
        public String st_age_value;
        public String st_expression;
        public String st_helmet_style;
        public String st_respirator;
    }
    public ApiFaceVo(){}
    public ApiFaceVo of(){return new ApiFaceVo();}
}
