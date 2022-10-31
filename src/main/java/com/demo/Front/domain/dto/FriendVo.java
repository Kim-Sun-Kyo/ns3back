package com.demo.Front.domain.dto;

import lombok.Data;

@Data
public class FriendVo {
    private String kakao_id;
    private String kakao_uuid;
    private String kakao_nickname;

    public FriendVo(String kakao_id, String profileNickname, String kakaoUuid) {
        this.kakao_id = kakao_id;
        this.kakao_nickname = profileNickname;
        this.kakao_uuid = kakaoUuid;
    }
}
