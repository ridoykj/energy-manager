package com.itbd.energymanager.repos;

import com.itbd.energymanager.dao.BillDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BillRepo extends JpaRepository<BillDao, Long>, JpaSpecificationExecutor<BillDao> {
    List<BillDao> findByMeterId(String meterId);
}