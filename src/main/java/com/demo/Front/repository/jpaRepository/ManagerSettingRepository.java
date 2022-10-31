package com.demo.Front.repository.jpaRepository;

import com.demo.Front.domain.dto.ManagerSettingInterface;
import com.demo.Front.domain.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ManagerSettingRepository extends JpaRepository<Manager,Long> {
    @Query(value = "select a.id,b.client_name ,a.manager_name ,a.phone_number,a.kakao_nickname ,a.kakao_uuid,a.kakao_id  \n" +
            "from manager a\n" +
            "inner join client b\n" +
            "on b.id=a.client_id \n" +
            "order by id",nativeQuery = true)
    List<ManagerSettingInterface> findManager();
}
