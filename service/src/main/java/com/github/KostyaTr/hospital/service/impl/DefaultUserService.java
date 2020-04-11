package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.*;
import com.github.KostyaTr.hospital.dao.impl.*;
import com.github.KostyaTr.hospital.model.*;
import com.github.KostyaTr.hospital.service.UserService;

import java.util.List;

public class DefaultUserService implements UserService {
    private MedDoctorDao medDoctorDao = DefaultMedDoctorDao.getInstance();
    private PatientDao patientDao = DefaultPatientDao.getInstance();
    private DepartmentDao departmentDao = DefaultDepartmentDao.getInstance();
    private MedicalServiceDao medicalServiceDao = DefaultMedicalServiceDao.getInstance();
    private MedicineDao medicineDao = DefaultMedicineDao.getInstance();

    private static class SingletonHolder {
        static final UserService HOLDER_INSTANCE = new DefaultUserService();
    }

    public static UserService getInstance() {
        return DefaultUserService.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public List<String> getDepartments() {
        return departmentDao.getDepartments();
    }

    @Override
    public List<MedDoctor> getDoctors() {
        return medDoctorDao.getDoctors();
    }


    @Override
    public Long makeAppointment(Patient patient) {
        return patientDao.addPatient(patient);
    }

    @Override
    public List<Patient> getAppointmentsByUserId(Long userId) {
        return patientDao.getPatientsByUserId(userId);
    }

    @Override
    public List<MedicalService> getMedicalServices() {
        return medicalServiceDao.getMedicalServices();
    }

    @Override
    public List<Medicine> getMedicine() {
        return medicineDao.getMedicine();
    }
}
