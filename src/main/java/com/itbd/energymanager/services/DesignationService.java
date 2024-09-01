package com.itbd.energymanager.services;

import com.itbd.energymanager.dao.DesignationDao;
import com.itbd.energymanager.repos.DesignationRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DesignationService {
    private final DesignationRepo designationRepo;

    public DesignationService(final DesignationRepo designationRepo) {
        this.designationRepo = designationRepo;
    }

    public List<DesignationDao> list() {
        return designationRepo.findAll();
    }

    @Transactional
    public DesignationDao save(final DesignationDao designationDao) {
        return designationRepo.save(designationDao);
    }

    public void delete(String id) {
        designationRepo.deleteById(id);
    }
}
