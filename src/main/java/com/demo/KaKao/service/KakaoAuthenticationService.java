package com.demo.KaKao.service;

import com.demo.KaKao.HttpCallService;
import com.demo.KaKao.entity.Token;
import com.demo.KaKao.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoAuthenticationService extends HttpCallService {

    private static final String KAKAO_AUTH_URL = "https://kauth.kakao.com";
    private static final String REDIRECT_URI = "http://localhost:8092/login/oauth_kakao";

    private static final String ADMIN_ID = "2439553072";
    private static final String REQUEST_KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_REST_API_KEY = "e0120ef8a24e1dc5f7c55e7ef58b6aee";

    private static final String KAKAO_SECRET_KEY = "ixXwd0lwyRqdKjEocW9t0s6lMBVfA8eo";

    private final TokenRepository tokenRepository;

    public String createAuthUrl() {
        return KAKAO_AUTH_URL + "/oauth/authorize?client_id=" + KAKAO_REST_API_KEY + "&redirect_uri=" + REDIRECT_URI + "&response_type=code&scope=talk_message";
    }


    /* 카카오 로그인 access_token 리턴 */
    public ResponseEntity<HttpStatus> getAccessToken(String code) {
        HttpHeaders header = new HttpHeaders();
        String accessToken = "";
        String refreshToken = "";
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        header.set("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        parameters.add("code", code);
        parameters.add("grant_type", "authorization_code");
        parameters.add("client_id", KAKAO_REST_API_KEY);
        parameters.add("redirect_url", REDIRECT_URI);
        parameters.add("scope", "talk_message");
        parameters.add("client_secret", KAKAO_SECRET_KEY);

        HttpEntity<?> requestEntity = httpClientEntity(header, parameters);

        ResponseEntity<String> response = httpRequest(REQUEST_KAKAO_TOKEN_URL, HttpMethod.POST, requestEntity);
        JSONObject jsonData = new JSONObject(response.getBody());
        accessToken = jsonData.get("access_token").toString();
        refreshToken = jsonData.get("refresh_token").toString();
        String refresh_token_expires_in = jsonData.get("refresh_token_expires_in").toString();

        if (accessToken.isEmpty() || refreshToken.isEmpty()) {
            log.info("Issuing token is fail! do request for Kakao to solve this problem");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            log.info("Issuing token is success!");

            Token token = Token.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .userId(ADMIN_ID)
                    .loginTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")))
                    .loginYn("Y")
                    .build();
            tokenRepository.save(token);
            return new ResponseEntity<>(HttpStatus.CREATED);


        }
    }
}
