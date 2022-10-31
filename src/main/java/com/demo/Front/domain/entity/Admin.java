package com.demo.Front.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString
public class Admin {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String adminName;

    private String phoneNumber;

    private Long clientId;
}
