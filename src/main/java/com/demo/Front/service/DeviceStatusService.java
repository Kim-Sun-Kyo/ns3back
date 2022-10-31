package com.demo.Front.service;

import com.demo.NS3.entity.StdHeartbeatEntity;
import com.demo.NS3.repository.StdHeartbeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class DeviceStatusService {
    private final StdHeartbeatRepository heartbeatRepository;

    public ResponseEntity<?> getDeviceStatus(){
        List<StdHeartbeatEntity> entity = heartbeatRepository.findAll();
        return ResponseEntity.ok(entity);
    }
}
