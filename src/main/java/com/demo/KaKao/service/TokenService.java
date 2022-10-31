package com.demo.KaKao.service;

import com.demo.KaKao.HttpCallService;
import com.demo.KaKao.entity.Token;
import com.demo.KaKao.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TokenService extends HttpCallService {
    private static final String REQUEST_RENEWAL_TOKEN_URL = "https://kauth.kakao.com/oauth/token";

    private final TokenRepository repository;

    /*토큰 갱신하기
    * response 중 refresh_token 값은 요청 시 사용된 refresh_token 의 만료시간이 1개월 미만으로 남았을 때만 갱신되어 전달
    * 따라서 refresh_token 과 refresh_token_expires_in은 결과 값에 포함되지 않을 수 있다는 점을 응답처리 시 유의
    * */

    public String renewalToken(String refreshToken, String user) {
        log.info("***** renewal Token *****");

        HttpHeaders header = new HttpHeaders();
        header.set("Content-Type", APP_TYPE_URL_ENCODED);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type","refresh_token");
        parameters.add("client_id","e0120ef8a24e1dc5f7c55e7ef58b6aee");
        parameters.add("refresh_token",refreshToken);
        parameters.add("client_secret","ixXwd0lwyRqdKjEocW9t0s6lMBVfA8eo");

        HttpEntity<?> requestEntity = httpClientEntity(header, parameters);
        ResponseEntity<String> response = httpRequest(REQUEST_RENEWAL_TOKEN_URL, HttpMethod.POST, requestEntity);
        JSONObject jsonData = new JSONObject(response.getBody());
        String access_token = jsonData.get("access_token").toString();
        Token findUser = repository.findByUserId(user);
        // refresh_token & refresh_token_expires_in exist in response

        if(jsonData.has("refresh_token") && jsonData.has("refresh_token_expires_in")) {
            String refresh_token = jsonData.get("refresh_token").toString();
            String refresh_token_expires_in = jsonData.get("refresh_token_expires_in").toString();
            findUser.setAccessToken(access_token);
            findUser.setRefreshToken(refresh_token);
            repository.save(findUser);
            return access_token;
        }else {
            findUser.setAccessToken(access_token);
            repository.save(findUser);
            return access_token;
        }
    }
}
