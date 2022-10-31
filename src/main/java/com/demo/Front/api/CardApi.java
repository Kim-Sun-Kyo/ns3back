package com.demo.Front.api;

import com.demo.Front.service.CardService;
import com.demo.SMS.service.SMSTokenService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class CardApi {
    private final CardService service;
    private final SMSTokenService smsTokenService;
    @GetMapping("/front/urlKey")
    public ResponseEntity<?> getUrlKey(@RequestParam(value = "key", required = false) String key, HttpServletRequest request) throws IOException {
        return smsTokenService.findInfo(key);
    }

    @GetMapping("/front/getCard")
    public ResponseEntity<?> getDetail(@RequestParam(value = "client_name", required = false) String clientName,
                                       @RequestParam(value = "device_name", required = false) String deviceName,
                                       @RequestParam(value = "camera_name", required = false) String cameraName,
                                       @RequestParam(value = "snapId",required = false) String snapId, HttpServletRequest request) {
        return service.getCard(clientName, deviceName,cameraName,snapId);
    }





}
