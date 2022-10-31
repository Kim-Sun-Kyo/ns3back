package com.demo.Front.repository.jpaRepository;

import com.demo.Front.domain.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

}
