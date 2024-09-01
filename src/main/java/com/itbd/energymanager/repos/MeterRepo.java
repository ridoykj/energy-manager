package com.itbd.energymanager.repos;

import com.itbd.energymanager.dao.MeterDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeterRepo extends JpaRepository<MeterDao, String>, JpaSpecificationExecutor<MeterDao> {
    List<MeterDao> findByCustomerId(String customerId);
}
