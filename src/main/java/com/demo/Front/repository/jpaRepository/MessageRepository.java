package com.demo.Front.repository.jpaRepository;

import com.demo.Front.domain.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
