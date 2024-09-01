package com.itbd.energymanager.repos;

import com.itbd.energymanager.dao.CustomerDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerDao, String>, JpaSpecificationExecutor<CustomerDao> {
    List<CustomerDao> findByAreaId(String areaId);
}
