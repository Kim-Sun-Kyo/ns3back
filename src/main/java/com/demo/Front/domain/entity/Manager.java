package com.demo.Front.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static lombok.AccessLevel.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clientId;
    private String managerName;
    private String kakaoId;
    private String phoneNumber;
    private String kakaoUuid;
    private String kakaoNickname;
    private boolean kakaoFlag;
}
