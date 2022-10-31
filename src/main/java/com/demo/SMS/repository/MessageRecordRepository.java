package com.demo.SMS.repository;

import com.demo.SMS.domain.entity.MessageRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRecordRepository extends JpaRepository<MessageRecord, Long> {
    List<MessageRecord> findAllByOrderByIdDesc();
}
