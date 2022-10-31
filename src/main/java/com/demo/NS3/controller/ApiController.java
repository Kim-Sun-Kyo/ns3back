package com.demo.NS3.controller;

import com.demo.NS3.service.ApiService;
import com.demo.SMS.service.SMSTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;


import org.jetbrains.annotations.Nullable;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiController {
    private final ApiService service;

    private final SMSTokenService tokenService;

    @PostMapping("/v1/nebula/push")
    public ResponseEntity<?> test(@NotNull MultipartHttpServletRequest request) throws Exception {
        System.out.println(request.getRemoteAddr());
        return service.saveData(request);
    }

    @GetMapping(value = "/v1/img/{snapid}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@Nullable @PathVariable(value = "snapid") String snapid) throws IOException {
        return service.getImage(snapid);
    }

}
