package com.demo.KaKao.repository;

import com.demo.KaKao.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {
    Optional<Token> findByLoginYn(String loginYn);

    Token findByUserId(String userId);

    Token findByAccessToken(String accessToken);
}
