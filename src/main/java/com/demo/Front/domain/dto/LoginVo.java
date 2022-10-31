package com.demo.Front.domain.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginVo {
    private String userid;
    private String password;
}
