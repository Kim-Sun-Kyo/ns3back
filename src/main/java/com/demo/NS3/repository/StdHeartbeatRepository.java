package com.demo.NS3.repository;

import com.demo.NS3.entity.StdHeartbeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StdHeartbeatRepository extends JpaRepository<StdHeartbeatEntity,Long> {
    Optional<StdHeartbeatEntity> findByDeviceId(String deviceId);
}
