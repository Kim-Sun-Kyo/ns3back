package com.demo.SMS.repository;

import com.demo.SMS.domain.entity.EventSender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventSenderRepository extends JpaRepository<EventSender, Long> {
        @Query(value = "select row_number() over() as id, a.client_name, a.admin_name, a.manager_name, a.phone_number, a.kakao_id, a.kakao_uuid, a.kakao_email, a.kakao_nickname, a.kakao_flag, a.camera_name, a.device_name,  a.content,a.msg_type  \n" +
                "                ,case when a.kakao_flag = true and msg_type = 'TALK' then 'TALK' else 'SMS' end as msg_type_new \n" +
                "                from   \n" +
                "                                (  \n" +
                "                                (select manager_id, device_id, msg_id, policy_id from message_template where checkyn = 'Y') a  \n" +
                "                                inner join   \n" +
                "                                (select id, manager_name, phone_number, kakao_uuid, kakao_id, kakao_email, kakao_flag, kakao_nickname from manager) b   \n" +
                "                                on a.manager_id  = b.id  \n" +
                "                                inner join  \n" +
                "                                (select id as deviceId, camera_id as camera_name, client_id as clientNo, device_id as device_name from device_camera) c  \n" +
                "                                on a.device_id = c.deviceId   \n" +
                "                                inner join  \n" +
                "                                (select id as messageId, msg_type from message) d   \n" +
                "                                on a.msg_id = d.messageId  \n" +
                "                                inner join   \n" +
                "                                (select id as policyId, event_type, template_id, status from \"policy\") e  \n" +
                "                                on a.policy_id = e.policyId  \n" +
                "                                inner join   \n" +
                "                                (select id as templateId, content from \"Template\") f   \n" +
                "                                on e.template_id = f.templateId  \n" +
                "                                inner join   \n" +
                "                                (select id as clientId, client_name from client) g   \n" +
                "                                on c.clientNo = g.clientId  \n" +
                "                                inner join   \n" +
                "                                (select id as adminId , admin_name from admin) h  \n" +
                "                                on g.clientId = h.adminId \n" +
                "                                ) a   \n" +
                "                                where a.status = 'Y'  and a.event_type = :eventType\n" +
                "               \t\t\t\t\t and a.camera_name = :cameraName and a.device_name = :deviceName", nativeQuery = true)
    List<EventSender> findSenders(@Param("cameraName") String cameraName, @Param("deviceName") String deviceName, @Param("eventType") String eventType);
}
