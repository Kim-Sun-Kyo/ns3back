package com.demo.SMS.service;

import com.demo.Front.domain.entity.Client;
import com.demo.Front.repository.jpaRepository.ClientRepository;
import com.demo.Front.repository.jpaRepository.DeviceCameraRepository;
import com.demo.SMS.domain.entity.SmsToken;
import com.demo.SMS.repository.SMSTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SMSTokenService {

    private final SMSTokenRepository tokenRepository;
    private final DeviceCameraRepository deviceCameraRepository;

    private final ClientRepository clientRepository;
    private final String key = "JLab";

    // create Token(use snap_id)
    public String createToken(String snapId) {
        // v214_1665641406_576903_0
        // Set Header
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");
        // Set payload
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("snapKey", snapId);

        long expiredTime = 1000 * 60L * 60L; // Valid 1hour


        Date ext = new Date(); // Token expired time
        ext.setTime(ext.getTime() + expiredTime);

        //Token builder

        return Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setExpiration(ext)
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .compact();
    }

    public String splitPayload(String token) {
        String[] split = token.split("\\.");
        return split[1];
    }

    public void createTokenEntity(String snapId, String cameraName, String deviceId, String clientName, String token) {
        String[] split = token.split("\\.");
        String payload = split[1];

        SmsToken smsToken = SmsToken.builder()
                .payload(payload)
                .accessToken(token)
                .snapId(snapId)
                .cameraName(cameraName)
                .deviceId(deviceId)
                .clientName(clientName)
                .build();
        tokenRepository.save(smsToken);
    }

    // validate token
    public Map<String, Object> verifyToken(String token) {
        Map<String, Object> claimMap = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key.getBytes("UTF-8"))
                    .parseClaimsJws(token)
                    .getBody();

            claimMap = claims;
        } catch (UnsupportedEncodingException e) {
            log.info("인코딩 에러...");
        } catch (ExpiredJwtException e) {
            log.info("만료");
        }
        return claimMap;
    }

    public ResponseEntity<?> findInfo(String payload) {
        try {
            SmsToken findData = tokenRepository.findByPayload(payload);
            String accessToken = findData.getAccessToken();
            Map<String, Object> claimMap = verifyToken(accessToken);
            return new ResponseEntity<>(findData, HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
