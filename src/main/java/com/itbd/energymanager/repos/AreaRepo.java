package com.itbd.energymanager.repos;

import com.itbd.energymanager.dao.AreaDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AreaRepo extends JpaRepository<AreaDao, String>, JpaSpecificationExecutor<AreaDao> {
}