package com.itbd.energymanager.services;

import com.itbd.energymanager.dao.CategoryDao;
import com.itbd.energymanager.dao.GradeDao;
import com.itbd.energymanager.repos.CategoryRepo;
import com.itbd.energymanager.repos.GradeRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepo categoryRepo;
    private final GradeRepo gradeRepo;

    public CategoryService(final CategoryRepo categoryRepo, final GradeRepo gradeRepo) {
        this.categoryRepo = categoryRepo;
        this.gradeRepo = gradeRepo;
    }

    public List<CategoryDao> list() {
        return categoryRepo.findAll();
    }

    @Transactional
    public CategoryDao save(final CategoryDao categoryDao) {
        return categoryRepo.save(categoryDao);
    }

    public void delete(String id) {
        categoryRepo.deleteById(id);
    }


    @Transactional
    public List<GradeDao> getGrades(String id) {
        return gradeRepo.findByCategoryId(id);
    }
}
