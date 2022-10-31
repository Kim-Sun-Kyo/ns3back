package com.demo.Front.domain.entity;

import com.demo.Front.repository.jpaRepository.PolicyRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;


import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PolicyTest {
    @Autowired
    PolicyRepository repository;




}