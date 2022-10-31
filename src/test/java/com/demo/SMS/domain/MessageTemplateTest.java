package com.demo.SMS.domain;

import com.demo.Front.domain.entity.Manager;
import com.demo.Front.repository.jpaRepository.ManagerRepository;
import com.demo.SMS.domain.entity.MessageTemplate;
import com.demo.SMS.repository.TemplateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class MessageTemplateTest {
    // policy_id = 6, device_id = 1, checkYn = Y,
    @Autowired
    TemplateRepository repository;

    @Autowired
    ManagerRepository managerRepository;

    @Test
    void EntityTest() {
        // policyId = 6, deviceId = 1, checkyn = Y,
        Long policyId = 6L;
        Long deviceId = 1L;
        String checkyn = "Y";
        List<MessageTemplate> findSenders = repository.findByPolicyIdAndDeviceIdAndCheckyn(policyId, deviceId, checkyn);
        for (MessageTemplate findSender : findSenders) {
            Long sendDerId = findSender.getManagerId();
            Optional<Manager> Manager = managerRepository.findById(sendDerId);
            System.out.println("Manager.get().getPhoneNumber() = " + Manager.get().getPhoneNumber());
        }
    }
}