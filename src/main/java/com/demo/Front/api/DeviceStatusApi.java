package com.demo.Front.api;

import com.demo.Front.service.DeviceStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/v1")
@RestController
@RequiredArgsConstructor
public class DeviceStatusApi {
    private final DeviceStatusService service;
    @GetMapping("/front/status")
    public ResponseEntity<?> getStatus(HttpServletRequest request){
        return service.getDeviceStatus();
    }
}
