package com.demo.NS3.repository;

import com.demo.NS3.entity.BodyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BodyRepository extends JpaRepository<BodyEntity,Long> {
    Optional<BodyEntity> findByDeviceidAndCameranameAndTrigger(String device_id, String camera_name, String trigger);
    Optional<BodyEntity> findBySnapid(String snap_id);
    List<BodyEntity> findBySendflag(String sendflag);
//    Optional<BodyEntity> findBySendflag(String sendflag);
    List<BodyEntity> findAllBySendflag(String sendFlag);
}
