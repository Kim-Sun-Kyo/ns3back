package com.demo.SMS.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Table(name = "message_template")
@ToString
public class MessageTemplate {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long managerId;

    private Long deviceId;

    private Long msgId;

    private Long policyId;

    private String checkyn;
}
