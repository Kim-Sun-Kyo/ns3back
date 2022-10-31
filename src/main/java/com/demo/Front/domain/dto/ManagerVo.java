package com.demo.Front.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerVo {
    private Long id;
    private String client_name;
    private String manager_name;
    private String kakao_id;
    private String phone_number;
    private String kakao_uuid;
    private String kakao_nickname;
}
