package com.itbd.energymanager.repos;

import com.itbd.energymanager.dao.DesignationDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DesignationRepo extends JpaRepository<DesignationDao, String>, JpaSpecificationExecutor<DesignationDao> {
}