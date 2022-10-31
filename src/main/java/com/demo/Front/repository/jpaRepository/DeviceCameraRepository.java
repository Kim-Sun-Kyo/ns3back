package com.demo.Front.repository.jpaRepository;

import com.demo.Front.domain.entity.DeviceCamera;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceCameraRepository extends JpaRepository<DeviceCamera, Long> {
    DeviceCamera findByCameraIdAndDeviceId(String cameraName, String deviceId);
}
