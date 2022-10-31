package com.demo.Front.api;

import com.demo.Front.service.MessageRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MessageRecordApi {
    private final MessageRecordService service;
    @GetMapping("/front/getlog")
    public ResponseEntity<?> getMsgSettingGrid(HttpServletRequest request){
        return service.getLog();
    }

}
