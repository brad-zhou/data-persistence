package com.brad.repository;

import com.brad.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhoubd
 * @Date 2018/11/23 15:15
 * @@Description
 */
public interface AppRepository extends JpaRepository<Department, Long> {
}
