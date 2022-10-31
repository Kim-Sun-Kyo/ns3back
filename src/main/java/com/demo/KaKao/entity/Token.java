package com.demo.KaKao.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "adm_login")
public class Token {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accessToken;

    @Column(nullable = false)
    private String refreshToken;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String loginYn;

    @Column(nullable = false)
    private String loginTime;

    @Builder
    public Token(String accessToken, String refreshToken, String userId, String loginYn, String loginTime) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.loginYn = loginYn;
        this.loginTime = loginTime;
    }
}