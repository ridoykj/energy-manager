package com.itbd.energymanager.services;

import com.itbd.energymanager.dao.BillDao;
import com.itbd.energymanager.dao.MeterDao;
import com.itbd.energymanager.repos.BillRepo;
import com.itbd.energymanager.repos.MeterRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MeterService {
    private final MeterRepo meterRepo;
    private final BillRepo billRepo;

    public MeterService(final MeterRepo meterRepo, final BillRepo billRepo) {
        this.meterRepo = meterRepo;
        this.billRepo = billRepo;
    }

    public List<MeterDao> list() {
        return meterRepo.findAll();
    }

    @Transactional
    public MeterDao save(final MeterDao meterDao) {
        return meterRepo.save(meterDao);
    }

    public void delete(String id) {
        meterRepo.deleteById(id);
    }


    @Transactional
    public List<BillDao> getBills(String id) {
        return billRepo.findByMeterId(id);
    }
}
