package com.demo.KaKao.api;

import com.demo.KaKao.HttpCallService;
import com.demo.KaKao.entity.Token;
import com.demo.KaKao.repository.TokenRepository;
import com.demo.KaKao.service.KakaoAuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KakaoController {

    private final KakaoAuthenticationService authService;

    /* 카카오 로그인 창 호출 */
    @RequestMapping("/login/getKakaoAuthUrl")
    public String getKakaoAuthUrl(HttpServletRequest request) {
        log.info("*** 카카오 로그인 창 호출 ***");
        return authService.createAuthUrl();
    }

    /* 카카오 연동정보 조회 */
    @RequestMapping("/login/oauth_kakao")
    public void OauthKakao(HttpServletRequest request) {
        log.info("*** 카카오 인가코드 받음 ***");
        String code = request.getParameter("code");
        String error = request.getParameter("error");

//         카카오 로그인 페이지에서 취소버튼 눌렀을 경우 error 발생!
//         "access_denied" -> 사용자가 동의화면에서 동의하고 계속하기를 누르지 않고 로그인을 취소한 경우.
//        if(error != null) {
//            if(error.equals("access_denied")) {
//                return "redirect:/login";
//            }
//        }
        authService.getAccessToken(code);

    }



}
