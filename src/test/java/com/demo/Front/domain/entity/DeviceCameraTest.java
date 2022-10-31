package com.demo.Front.domain.entity;

import com.demo.Front.repository.jpaRepository.DeviceCameraRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DeviceCameraTest {
    @Autowired
    DeviceCameraRepository repository;
    @Test
    void EntityTest() {
//        cameraName = Face+Bodytest, device_id = office2
        String cameraId = "Face+Bodytest";
        String deviceId = "office2";
        DeviceCamera findDevice = repository.findByCameraIdAndDeviceId(cameraId, deviceId);
        System.out.println("findDevice = " + findDevice);
    }
}