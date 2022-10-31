package com.demo.KaKao.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class KakaoSenderVO {
    private String profileNickname;
    private String kakaoUuid;

    public KakaoSenderVO(String profileNickname, String kakaoUuid) {
        this.profileNickname = profileNickname;
        this.kakaoUuid = kakaoUuid;
    }
}
