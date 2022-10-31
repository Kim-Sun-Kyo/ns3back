package com.demo.Front.repository.jpaRepository;

import com.demo.Front.domain.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
//    @Modifying
//    @Query("update Manager m set m.kakaoFlag = false where 1 = 1")
//    void updateFlag();

    @Transactional
    @Modifying
    @Query(value = "update manager set kakao_flag = false where 1=1;", nativeQuery = true)
    int updateFlag();

    Optional<Manager> findByKakaoUuid(String kakaoUuid);
    List<Manager> findByKakaoUuidIn(ArrayList<String> uid);
}
