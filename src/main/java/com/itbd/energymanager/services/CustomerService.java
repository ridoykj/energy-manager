package com.itbd.energymanager.services;

import com.itbd.energymanager.dao.CustomerDao;
import com.itbd.energymanager.dao.MeterDao;
import com.itbd.energymanager.repos.CustomerRepo;
import com.itbd.energymanager.repos.MeterRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepo customerRepo;
    private final MeterRepo meterRepo;

    public CustomerService(final CustomerRepo customerRepo, final MeterRepo meterRepo) {
        this.customerRepo = customerRepo;
        this.meterRepo = meterRepo;
    }

    public List<CustomerDao> list() {
        return customerRepo.findAll();
    }

    @Transactional
    public CustomerDao save(final CustomerDao customerDao) {
        return customerRepo.save(customerDao);
    }

    public void delete(String id) {
        customerRepo.deleteById(id);
    }

    public CustomerDao store(final CustomerDao customerDao, byte[] bytes) {
        customerDao.setImage(bytes);
        return customerRepo.save(customerDao);
    }

    @Transactional
    public List<MeterDao> getMeters(String id) {
        return meterRepo.findByCustomerId(id);
    }
}
