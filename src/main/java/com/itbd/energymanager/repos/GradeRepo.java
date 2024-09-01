package com.itbd.energymanager.repos;

import com.itbd.energymanager.dao.GradeDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface GradeRepo extends JpaRepository<GradeDao, Long>, JpaSpecificationExecutor<GradeDao> {
    List<GradeDao> findByCategoryId(String categoryId);
}