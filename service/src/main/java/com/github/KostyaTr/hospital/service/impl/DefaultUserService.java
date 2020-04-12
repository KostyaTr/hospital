package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.*;
import com.github.KostyaTr.hospital.dao.impl.*;
import com.github.KostyaTr.hospital.model.*;
import com.github.KostyaTr.hospital.service.UserService;

import java.util.List;

public class DefaultUserService implements UserService {
    private PatientDao patientDao = DefaultPatientDao.getInstance();
    private DepartmentDao departmentDao = DefaultDepartmentDao.getInstance();
    private MedicalServiceDao medicalServiceDao = DefaultMedicalServiceDao.getInstance();
    private MedicineDao medicineDao = DefaultMedicineDao.getInstance();
    private DoctorSpecialityDeptDao doctorSpecialityDeptDao = DefaultDoctorSpecialityDeptDao.getInstance();
    private GuestPatientDao guestPatientDao = DefaultGuestPatientDao.getInstance();
    private AppointmentDao appointmentDao = DefaultAppointmentDao.getInstance();

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
    public List<DoctorSpecialityDept> getDoctors() {
        return doctorSpecialityDeptDao.getDoctors();
    }


    @Override
    public Long makeAppointment(Patient patient) {
        return patientDao.addPatient(patient);
    }

    @Override
    public Long makeGuestAppointment(GuestPatient guestPatient) {
        return guestPatientDao.addPatient(guestPatient);
    }

    @Override
    public List<Appointment> getAppointmentsByUserId(Long userId) {
        return appointmentDao.getAppointmentsByUserId(userId);
    }

    @Override
    public List<MedicalService> getMedicalServices() {
        return medicalServiceDao.getMedicalServices();
    }

    @Override
    public List<Medicine> getMedicine() {
        return medicineDao.getMedicine();
    }

    @Override
    public List<DoctorSpecialityDept> getDoctorsBySpeciality(Long specialityId) {
        return doctorSpecialityDeptDao.getDoctorsBySpeciality(specialityId);
    }

    @Override
    public DoctorSpecialityDept getDoctorById(Long doctorId) {
        return doctorSpecialityDeptDao.getDoctorById(doctorId);
    }

    public List<DoctorSpecialityDept> getDoctorsByMedicalService(Long medicalServiceId) {
        return getDoctorsBySpeciality(medicalServiceDao.getMedicalServiceById(medicalServiceId).getNeededSpecialityId());
    }
}
