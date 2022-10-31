package com.demo.Front.api;

import com.demo.Front.service.MsgSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MsgSettingApi {
    private final MsgSettingService service;
    @GetMapping("/front/grid")
    public ResponseEntity<?> getMsgSettingGrid(HttpServletRequest request){
        return service.getMstSettingGrid();
    }

    @PostMapping("/front/save")
    public ResponseEntity<?> savelist(HttpServletRequest request, @RequestBody List<String> data){
        System.out.println(data);
        return service.save(data);
    }
}
