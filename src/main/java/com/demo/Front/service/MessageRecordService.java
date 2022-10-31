package com.demo.Front.service;

import com.demo.SMS.domain.entity.MessageRecord;
import com.demo.SMS.repository.MessageRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageRecordService {
    private final MessageRecordRepository recordRepository;
    public ResponseEntity<?> getLog(){
        List<MessageRecord> entity = recordRepository.findAllByOrderByIdDesc();
        return new ResponseEntity<>(entity, HttpStatus.OK);

    }
}
