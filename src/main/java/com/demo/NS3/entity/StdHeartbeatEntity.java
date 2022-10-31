package com.demo.NS3.entity;

import lombok.Data;
import org.intellij.lang.annotations.Identifier;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "std_heartbeat_push")
public class StdHeartbeatEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String deviceId;
    private String trigger;
    private String onoff;
}
