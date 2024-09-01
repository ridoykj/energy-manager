package com.itbd.energymanager.services;

import com.itbd.energymanager.dao.UserDao;
import com.itbd.energymanager.repos.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(final UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<UserDao> list() {
        return userRepo.findAll();
    }

    @Transactional
    public UserDao save(final UserDao userDao) {
        return userRepo.save(userDao);
    }

    public void delete(Long id) {
        userRepo.deleteById(id);
    }
}
