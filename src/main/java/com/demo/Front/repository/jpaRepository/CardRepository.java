package com.demo.Front.repository.jpaRepository;

import com.demo.Front.domain.dto.CardInterface;
import com.demo.NS3.entity.BodyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<BodyEntity,Long> {
    @Query(value = "  select *\n" +
            " from(\n" +
            " select c.client client,c.device device_name,c.camera camera_name,c.atr as trigger, admin_name,string_agg(distinct c.manager_name,',')manager_name ,c.event_type , string_agg(distinct msg,',') msg_type, send_time,snap,person_name\n" +
            " from(\n" +
            "\tselect a.id,a.event_type event_type,a.manager_name manager_name ,a.admin_name admin_name, a.\"trigger\" atr,b.id as body_id,b.\"trigger\"  as body_trigger ,c.id as face_id ,c.\"trigger\" as face_trigger,a.msg_type msg,\n" +
            "\ta.client_name client,a.device_name device,a.camera_name camera,substring(a.send_time,1,16) send_time,b.snap_id bsnap,c.snap_id fsnap,c.person_name,\n" +
            "\tcoalesce(b.snap_id,c.snap_id)snap\n" +
            "\tfrom message_record a\n" +
            "\tleft outer join std_event_record_push b on a.\"trigger\" =b.\"trigger\"\n" +
            "\tleft outer join std_face_record_push c on a.\"trigger\" = c.\"trigger\" \n" +
            "\t)c\n" +
            "\tgroup by  c.atr,c.event_type,c.body_id,body_trigger, face_id ,face_trigger ,client ,device,camera,send_time,admin_name,snap,c.person_name\n" +
            "\torder by c.atr desc \n" +
            ")a\n" +
            "where (:snap is null or snap = :snap) and (:client is null or client =:client) and(:device_name is null or device_name = :device_name) and(:camera_name is null or camera_name =:camera_name) ", nativeQuery = true)
    List<CardInterface> findCard(@Param("snap") String snapId, @Param("client") String client, @Param("device_name") String deviceName, @Param("camera_name") String cameraName);


}
