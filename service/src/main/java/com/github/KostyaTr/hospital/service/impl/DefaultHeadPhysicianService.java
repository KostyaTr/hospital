package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.*;
import com.github.KostyaTr.hospital.dao.impl.*;
import com.github.KostyaTr.hospital.model.*;
import com.github.KostyaTr.hospital.service.HeadPhysicianService;

import java.util.List;


public class DefaultHeadPhysicianService extends DefaultMedDoctorService implements HeadPhysicianService {
    private MedDoctorDao medDoctorDao = DefaultMedDoctorDao.getInstance();
    private MedicalServiceDao medicalServiceDao = DefaultMedicalServiceDao.getInstance();
    private PatientDao patientDao = DefaultPatientDao.getInstance();
    private GuestPatientDao guestPatientDao = DefaultGuestPatientDao.getInstance();
    private InpatientDao inpatientDao = DefaultInpatientDao.getInstance();

    private static class SingletonHolder {
        static final HeadPhysicianService HOLDER_INSTANCE = new DefaultHeadPhysicianService();
    }

    public static HeadPhysicianService getInstance() {
        return DefaultHeadPhysicianService.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public boolean fireDoctor(Long doctorId) {
        return medDoctorDao.removeDoctorById(doctorId);
    }

    @Override
    public Long addMedicalService(MedicalService medicalService) {
        return medicalServiceDao.addMedicalServiceId(medicalService);
    }

    @Override
    public List<Patient> getPatientsByDeptId(Long deptId) {
        return patientDao.getPatientsByDepartmentId(deptId);
    }

    @Override
    public List<GuestPatient> getGuestPatientByDeptId(Long deptId) {
        return guestPatientDao.getPatientsByDepartmentId(deptId);
    }

    @Override
    public List<Inpatient> getInpatientsByDepId(Long deptId) {
        return inpatientDao.getInpatientsByDepId(deptId);
    }

    @Override
    public List<Inpatient> getUndiagnosedInpatientsByDep(Long deptId) {
        return inpatientDao.getUndiagnosedInpatientsByDep(deptId);
    }

    @Override
    public Long addDoctor(MedDoctor medDoctor) {
        return medDoctorDao.addDoctor(medDoctor);
    }
}
