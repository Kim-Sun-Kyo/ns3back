package com.demo.Front.api;

import com.demo.Front.domain.dto.LoginVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1")

public class LoginApi {
    private final static String USERID = "admin";
    private final static String PASSWORD = "1234";

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request, @RequestBody LoginVo data){
        String id = data.getUserid();
        String pw = data.getPassword();
        String msg="";
        String res="";
        System.out.println("********************login******************");

        if(USERID.equals(id) && PASSWORD.equals(pw)){
            msg = "Success";
//            res = "Success";
            return new ResponseEntity<>(msg,HttpStatus.OK);

        }
        else{
            msg = "Fail";
//            res = "Fail";
            return new ResponseEntity<>(msg,HttpStatus.OK);

        }
//        Map<String,Object> result = new HashMap<>();
//        result.put("msg",msg);
//        result.put("result",res);
//        return ResponseEntity.ok(result);

    }

}
