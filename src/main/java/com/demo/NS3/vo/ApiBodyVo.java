package com.demo.NS3.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiBodyVo {
    public String camera_name;
    public String device_id;
    public Long channel;
    public String events_type;
    public String trigger;
    public String events_off_type;
    public Long events_status;
    public Long obj_label;
    public Long pos_bottom;
    public Long pos_top;
    public Long pos_left;
    public Long pos_right;
    public String position;
    public Long quality;
    public String snap_feat;
    public String snap_id;
    public String snap_path;
    public body_attr body_attr;
    public events_attr events_attr;
    @Data
    public class body_attr{
        public String cap_style;
        public String coat_color;
        public String gender_code;
        public String st_age;
        public String st_phone_status;
        public String st_pose;
        public String st_reflective_clothes;
        public String st_respirator_v2;
        public String st_smoking;
        public String st_uniform;
    }
    @Data
    public class events_attr{
        public Long crowd_num;
        public Long crowd_threshold;
        public String helmet_type;
        public Long leave_num_threshold;
        public Long leave_time_threshold;
        public Long over_boundary_direction;
        public Long over_boundary_num1;
        public Long over_boundary_num2;
        public String work_cloth_color;
        public String work_cloth_type;
    }
    public ApiBodyVo() {}
    public static ApiBodyVo of() {
        return new ApiBodyVo();
    }
}
