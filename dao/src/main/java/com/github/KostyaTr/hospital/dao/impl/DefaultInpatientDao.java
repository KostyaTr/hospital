package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.InpatientDao;
import com.github.KostyaTr.hospital.model.Inpatient;

import java.util.List;

public class DefaultInpatientDao implements InpatientDao {

    private static class SingletonHolder {
        static final InpatientDao HOLDER_INSTANCE = new DefaultInpatientDao();
    }

    public static InpatientDao getInstance() {
        return DefaultInpatientDao.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public List<Inpatient> getInpatientsByDoctorId(Long doctorId) {
        return null;
    }

    @Override
    public List<Inpatient> getInpatientsByDepChamberId(Long DepChamberId) {
        return null;
    }

    @Override
    public List<Inpatient> getUndiagnosedInpatients() {
        return null;
    }

    @Override
    public List<Inpatient> getPatients() {
        return null;
    }

    @Override
    public boolean removeInpatientById(Long patientId) {
        return false;
    }

    @Override
    public Inpatient getInpatientById(Long patientId) {
        return null;
    }

    @Override
    public Long addInpatient(Inpatient inpatient) {
        return null;
    }
}
