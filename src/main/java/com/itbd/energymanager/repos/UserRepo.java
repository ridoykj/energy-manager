package com.itbd.energymanager.repos;

import com.itbd.energymanager.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserDao, Long>, JpaSpecificationExecutor<UserDao> {
}
