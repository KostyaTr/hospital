package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.ChamberDao;
import com.github.KostyaTr.hospital.dao.InpatientDao;
import com.github.KostyaTr.hospital.model.Inpatient;

import java.util.List;

public class DefaultChamberDao implements ChamberDao {

    private static class SingletonHolder {
        static final ChamberDao HOLDER_INSTANCE = new DefaultChamberDao();
    }

    public static ChamberDao getInstance() {
        return DefaultChamberDao.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public Integer getChamberCapacity(Long id) {
        return null;
    }

    @Override
    public Integer getChamberLoad(Long id) {
        return null;
    }

    @Override
    public Long putPatientInChamber(Inpatient inpatientId) {
        return null;
    }

    @Override
    public boolean dischargeFromChamber(Long patientId) {
        return false;
    }

    @Override
    public List<Long> getFullChambersByDeptId(Long deptId) {
        return null;
    }

    @Override
    public List<Long> getEmptyChambersByDeptId(Long deptId) {
        return null;
    }
}
