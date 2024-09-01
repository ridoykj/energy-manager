package com.itbd.energymanager.services;

import com.itbd.energymanager.dao.DesignationDao;
import com.itbd.energymanager.dao.EmployeeDao;
import com.itbd.energymanager.repos.EmployeeRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    public EmployeeService(final EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Transactional
    public List<EmployeeDao> list() {
        List<EmployeeDao> employees = employeeRepo.findAll().stream().map(em -> {
            DesignationDao designationDao = em.getDesignation();
            em.setDesignation(designationDao);
            return em;
        }).toList();
        return employees;
    }

    @Transactional
    public EmployeeDao save(final EmployeeDao employeeDao) {
        return employeeRepo.save(employeeDao);
    }

    public void delete(String id) {
        employeeRepo.deleteById(id);
    }

    public EmployeeDao store(final EmployeeDao employeeDao, byte[] bytes) {
        employeeDao.setImage(bytes);
        return employeeRepo.save(employeeDao);
    }
}
