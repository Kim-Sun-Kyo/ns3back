package com.demo.Front.api;

import com.demo.Front.domain.dto.ManagerVo;
import com.demo.Front.service.ManagerSettingService;
import com.demo.KaKao.service.KakaoApiService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Manager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ManagerSettingApi {
    private final ManagerSettingService service;
    @GetMapping("/front/Manager")
    public ResponseEntity<?> getManager(HttpServletRequest request){
        return service.getManager();
    }

    @GetMapping("/front/getFriend")
    public ResponseEntity<?> getFriend(HttpServletRequest request){

        return service.getFriend();
    }
    @GetMapping("/front/getClient")
    public ResponseEntity<?> getClient(HttpServletRequest request){

        return service.getClient();
    }
    @PostMapping("/front/saveManager")
    public ResponseEntity<?> saveManager(HttpServletRequest request, @RequestBody List<ManagerVo> vo){
        System.out.println(vo);
        return service.saveManager(vo);
    }
}
