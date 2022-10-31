package com.demo.SMS.repository;

import com.demo.SMS.domain.entity.SmsToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SMSTokenRepository extends JpaRepository<SmsToken, Long> {
    SmsToken findByPayload(String payload);
}
