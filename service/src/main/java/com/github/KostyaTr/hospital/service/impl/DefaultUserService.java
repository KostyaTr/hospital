package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.*;
import com.github.KostyaTr.hospital.model.*;
import com.github.KostyaTr.hospital.service.UserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DefaultUserService implements UserService {
    private final PatientDao patientDao;
    private final MedicalServiceDao medicalServiceDao;
    private final MedicineDao medicineDao;
    private final GuestPatientDao guestPatientDao;
    private final MedDoctorDao medDoctorDao;
    private final UserDao userDao;
    private final ReceiptDao receiptDao;

    public DefaultUserService(PatientDao patientDao, MedicalServiceDao medicalServiceDao,
                              MedicineDao medicineDao, GuestPatientDao guestPatientDao,
                              MedDoctorDao medDoctorDao, UserDao userDao, ReceiptDao receiptDao) {

        this.patientDao = patientDao;
        this.medicalServiceDao = medicalServiceDao;
        this.medicineDao = medicineDao;
        this.guestPatientDao = guestPatientDao;
        this.medDoctorDao = medDoctorDao;
        this.userDao = userDao;
        this.receiptDao = receiptDao;
    }

    @Override
    @Transactional
    public List<MedDoctor> getDoctors() {
        return medDoctorDao.getDoctors();
    }


    @Override
    @Transactional
    public Long makeAppointment(Patient patient) {
        return patientDao.addPatient(patient);
    }

    @Override
    @Transactional
    public Long makeGuestAppointment(GuestPatient guestPatient) {
        return guestPatientDao.addPatient(guestPatient);
    }

    @Override
    @Transactional
    public List<Patient> getAppointmentsByUserId(Long userId) {
        return patientDao.getPatientsByUserId(userId);
    }

    @Override
    @Transactional
    public List<MedicalService> getMedicalServices() {
        return medicalServiceDao.getMedicalServices();
    }

    @Override
    @Transactional
    public List<Medicine> getMedicine() {
        return medicineDao.getMedicine();
    }

    @Override
    @Transactional
    public List<MedDoctor> getDoctorsBySpeciality(Long specialityId) {
        return medDoctorDao.getDoctorBySpeciality(specialityId);
    }

    @Override
    @Transactional
    public MedDoctor getDoctorById(Long doctorId) {
        return medDoctorDao.getDoctorById(doctorId);
    }

    @Override
    @Transactional
    public List<MedDoctor> getDoctorsByMedicalService(Long medicalServiceId) {
        return getDoctorsBySpeciality(medicalServiceDao.getMedicalServiceById(medicalServiceId).getNeededSpeciality().getSpecialityId());
    }

    @Override
    @Transactional
    public void rescheduleAppointment(Patient patient) {
        patientDao.updateVisitDate(patient);
    }

    @Override
    @Transactional
    public boolean cancelAppointment(Long patientId) {
        return patientDao.removePatientById(patientId);
    }

    @Override
    @Transactional
    public User getUserById(Long userId) {
        return userDao.getUserById(userId);
    }

    @Override
    @Transactional
    public MedicalService getMedicalServiceById(Long medicalServiceId) {
        return medicalServiceDao.getMedicalServiceById(medicalServiceId);
    }

    @Override
    @Transactional
    public Patient getAppointment(Long patientId) {
        return patientDao.getPatientById(patientId);
    }

    @Override
    @Transactional
    public Receipt getReceipt(Long userId) {
        return receiptDao.getReceiptByUserId(userId);
    }
}
