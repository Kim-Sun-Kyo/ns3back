package com.demo.Front.repository.jpaRepository;

import com.demo.Front.domain.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    Policy findByEventType(String eventType);
}
