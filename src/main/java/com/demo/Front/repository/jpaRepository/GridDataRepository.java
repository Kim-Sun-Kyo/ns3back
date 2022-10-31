package com.demo.Front.repository.jpaRepository;

import com.demo.Front.domain.entity.GridData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GridDataRepository extends JpaRepository<GridData, Long> {
    @Query(value = "            select row_number() over() as id, c.client_key as client_key, c.device_key as device_key, c.admin_key as admin_key, c.manager_key as manager_key, c.message_key as message_key, c.client_name as client_name, c.device_id as device_id, c.camera_id as camera_id, c.admin_name as admin_name, c.manager_name as manager_name, c.msg_type as msg_type,  \n" +
            "            max(case when c.event_type = '385' then coalesce(c.flag, '')else '' end)as event385, \n" +
            "            max(case when c.event_type = '386' then coalesce(c.flag, '')else '' end)as event386, \n" +
            "            max(case when c.event_type = '387' then coalesce(c.flag, '')else '' end)as event387, \n" +
            "            max(case when c.event_type = '388' then coalesce(c.flag, '')else '' end)as event388, \n" +
            "            max(case when c.event_type = '389' then coalesce(c.flag, '')else '' end)as event389, \n" +
            "            max(case when c.event_type = '390' then coalesce(c.flag, '')else '' end)as event390, \n" +
            "            max(case when c.event_type = '391' then coalesce(c.flag, '')else '' end)as event391, \n" +
            "            max(case when c.event_type = '394' then coalesce(c.flag, '')else '' end)as event394, \n" +
            "            max(case when c.event_type = '395' then coalesce(c.flag, '')else '' end)as event395, \n" +
            "            max(case when c.event_type = '396' then coalesce(c.flag, '')else '' end)as event396, \n" +
            "            max(case when c.event_type = '398' then coalesce(c.flag, '')else '' end)as event398, \n" +
            "            max(case when c.event_type = '641' then coalesce(c.flag, '')else '' end)as event641, \n" +
            "            max(case when c.event_type = '705' then coalesce(c.flag, '')else '' end)as event705,\n" +
            "            \n" +
            "            max(case when c.event_type = '101' then coalesce(c.flag, '')else '' end)as event101,\n" +
            "            max(case when c.event_type = '102' then coalesce(c.flag, '')else '' end)as event102,\n" +
            "            max(case when c.event_type = '103' then coalesce(c.flag, '')else '' end)as event103,\n" +
            "            \n" +
            "            max(case when c.event_type = '385' then coalesce(c.policy_key, 0)else 0 end)as event385_key, \n" +
            "            max(case when c.event_type = '386' then coalesce(c.policy_key, 0)else 0 end)as event386_key, \n" +
            "            max(case when c.event_type = '387' then coalesce(c.policy_key, 0)else 0 end)as event387_key, \n" +
            "            max(case when c.event_type = '388' then coalesce(c.policy_key, 0)else 0 end)as event388_key, \n" +
            "            max(case when c.event_type = '389' then coalesce(c.policy_key, 0)else 0 end)as event389_key, \n" +
            "            max(case when c.event_type = '390' then coalesce(c.policy_key, 0)else 0 end)as event390_key, \n" +
            "            max(case when c.event_type = '391' then coalesce(c.policy_key, 0)else 0 end)as event391_key, \n" +
            "            max(case when c.event_type = '394' then coalesce(c.policy_key, 0)else 0 end)as event394_key, \n" +
            "            max(case when c.event_type = '395' then coalesce(c.policy_key, 0)else 0 end)as event395_key, \n" +
            "            max(case when c.event_type = '396' then coalesce(c.policy_key, 0)else 0 end)as event396_key, \n" +
            "            max(case when c.event_type = '398' then coalesce(c.policy_key, 0)else 0 end)as event398_key, \n" +
            "            max(case when c.event_type = '641' then coalesce(c.policy_key, 0)else 0 end)as event641_key, \n" +
            "            max(case when c.event_type = '705' then coalesce(c.policy_key, 0)else 0 end)as event705_key, \n" +
            "            \n" +
            "            max(case when c.event_type = '101' then coalesce(c.policy_key, 0)else 0 end)as event101_key,\n" +
            "            max(case when c.event_type = '102' then coalesce(c.policy_key, 0)else 0 end)as event102_key,\n" +
            "            max(case when c.event_type = '103' then coalesce(c.policy_key, 0)else 0 end)as event103_key\n" +
            "             \n" +
            "             \n" +
            "            from ( \n" +
            "             \n" +
            "            select a.client_key, a.device_key, a.admin_key, a.manager_key,a.message_key,a.policy_key, a.client_name, a.camera_id, a.device_id, a.admin_name, a.manager_name,  a.msg_type , a.event_type, b.flag  from  \n" +
            "            ( \n" +
            "            ( \n" +
            "            (select id as client_key, client_name from client) a  \n" +
            "            inner join  \n" +
            "            (select id as device_key, client_id, camera_id, device_id from device_camera) b  \n" +
            "            on a.client_key = b.client_id \n" +
            "            )a \n" +
            "             \n" +
            "            inner join  \n" +
            "             \n" +
            "            (select id as admin_key, admin_name, client_id from admin) c \n" +
            "            on a.client_key = c.client_id \n" +
            "            inner join  \n" +
            "            (select id as manager_key, manager_name, client_id from manager) d  \n" +
            "            on a.client_key = d.client_id  \n" +
            "             \n" +
            "            inner join  \n" +
            "            ( \n" +
            "            (select id as message_key, msg_type from message) e  \n" +
            "            inner join \n" +
            "            (select id as policy_key, event_type from policy where status = 'Y'  ) f \n" +
            "            on 1=1 \n" +
            "            )g  \n" +
            "            on 1=1 \n" +
            "            ) a  \n" +
            "             \n" +
            "            left outer join  \n" +
            "             \n" +
            "            (select manager_id as manager_key, device_id as device_key, msg_id as message_key, policy_id as policy_key, checkyn as flag from message_template) b  \n" +
            "            on a.device_key = b.device_key and a.manager_key = b.manager_key and a.message_key = b.message_key and a.policy_key = b.policy_key \n" +
            "             \n" +
            "            ) c  \n" +
            "             \n" +
            "            group by c.client_key, c.device_key, c.admin_key, c.manager_key, c.message_key, c.client_name, c.device_id, c.camera_id, c.admin_name, c.manager_name, c.msg_type \n" +
            "            order by c.client_key, c.device_key, c.admin_key, c.manager_key, c.message_key, c.client_name, c.device_id, c.camera_id, c.admin_name, c.manager_name, c.msg_type ", nativeQuery = true)
    List<GridData> getGridData();
}
