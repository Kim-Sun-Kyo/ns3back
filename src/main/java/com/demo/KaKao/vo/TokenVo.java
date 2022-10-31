package com.demo.KaKao.vo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class TokenVo {
    private String accessToken;
    private String refreshToken;
    private Integer refreshTokenExpiresIn;
//    private String loginYn;
//
//    public TokenEntity toEntity(String accessToken, String refreshToken, String refreshTokenExpiresIn, String loginYn) {
//        return TokenEntity.builder()
//                .accesstoken(accessToken)
//                .refreshToken(refreshToken)
//                .refreshTokenExpiresIn(Integer.valueOf(refreshTokenExpiresIn))
//                .loginYn("Y")
//                .loginTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")))
//                .build();
//    }
}
