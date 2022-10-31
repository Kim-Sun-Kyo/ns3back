package com.demo.KaKao.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class KakaoFriendsVO {
    private String profileNickname;
    private String kakaoUuid;

    public KakaoFriendsVO(String profileNickname, String kakaoUuid) {
        this.profileNickname = profileNickname;
        this.kakaoUuid = kakaoUuid;
    }
}
