package com.itbd.energymanager.repos;

import com.itbd.energymanager.dao.CategoryDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepo extends JpaRepository<CategoryDao, String>, JpaSpecificationExecutor<CategoryDao> {
}