package com.itbd.energymanager.repos;

import com.itbd.energymanager.dao.EmployeeDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<EmployeeDao, String>, JpaSpecificationExecutor<EmployeeDao> {
    List<EmployeeDao> findByAreaId(String areaId);
}