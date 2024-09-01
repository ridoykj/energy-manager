package com.itbd.energymanager.services;

import com.itbd.energymanager.dao.BillDao;
import com.itbd.energymanager.repos.BillRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BillService {
    private final BillRepo billRepo;

    public BillService(final BillRepo billRepo) {
        this.billRepo = billRepo;
    }

    public List<BillDao> list() {
        return billRepo.findAll();
    }

    @Transactional
    public BillDao save(final BillDao billDao) {
        return billRepo.save(billDao);
    }

    public void delete(Long id) {
        billRepo.deleteById(id);
    }

}
