package com.demo.SMS.service;

import com.demo.SMS.domain.entity.SmsToken;
import com.demo.SMS.repository.SMSTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@SpringBootTest
class JWTTest {
    @Autowired
    SMSTokenService tokenService;

    @Autowired
    SMSTokenRepository tokenRepository;


}

