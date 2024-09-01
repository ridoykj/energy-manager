package com.itbd.energymanager.services;

import com.itbd.energymanager.dao.GradeDao;
import com.itbd.energymanager.repos.GradeRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GradeService {
    private final GradeRepo gradeRepo;

    public GradeService(final GradeRepo gradeRepo) {
        this.gradeRepo = gradeRepo;
    }

    public List<GradeDao> list() {
        return gradeRepo.findAll();
    }

    @Transactional
    public GradeDao save(final GradeDao gradeDao) {
        return gradeRepo.save(gradeDao);
    }

    public void delete(Long id) {
        gradeRepo.deleteById(id);
    }
}
