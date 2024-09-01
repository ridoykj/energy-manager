package com.itbd.energymanager.services;

import com.itbd.energymanager.dao.AreaDao;
import com.itbd.energymanager.dao.CustomerDao;
import com.itbd.energymanager.dao.EmployeeDao;
import com.itbd.energymanager.repos.AreaRepo;
import com.itbd.energymanager.repos.CustomerRepo;
import com.itbd.energymanager.repos.EmployeeRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class AreaService {
    private final AreaRepo areaRepo;
    private final EmployeeRepo employeeRepo;
    private final CustomerRepo customerRepo;

    public AreaService(AreaRepo areaRepo, EmployeeRepo employeeRepo, CustomerRepo customerRepo) {
        this.areaRepo = areaRepo;
        this.employeeRepo = employeeRepo;
        this.customerRepo = customerRepo;
    }

    public List<AreaDao> list() {
        return areaRepo.findAll();
    }

    @Transactional
    public AreaDao save(final AreaDao areaDao) {
        return areaRepo.save(areaDao);
    }

    public void delete(String id) {
        areaRepo.deleteById(id);
    }

    @Transactional
    public List<EmployeeDao> getEmployees(String id) {
        return employeeRepo.findByAreaId(id);
    }

    @Transactional
    public List<CustomerDao> getCustomers(String id) {
        return customerRepo.findByAreaId(id);
    }
}
