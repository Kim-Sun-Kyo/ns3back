package com.demo.SMS.repository;

import com.demo.SMS.domain.entity.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TemplateRepository extends JpaRepository<MessageTemplate, Long> {
    List<MessageTemplate> findByPolicyIdAndDeviceIdAndCheckyn(Long policyId, Long deviceId, String checkyn);
    Optional<MessageTemplate> findByManagerIdAndDeviceIdAndMsgIdAndPolicyId(Long managerId, Long deviceId, Long msgId, Long policyId);
}
