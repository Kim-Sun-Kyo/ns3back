package com.demo.NS3.repository;

import com.demo.NS3.entity.FaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FaceRepository extends JpaRepository<FaceEntity,Long> {
    Optional<FaceEntity> findBySnapId(String snapId);
    List<FaceEntity> findAllBySendFlag(String sendFlag);
}
