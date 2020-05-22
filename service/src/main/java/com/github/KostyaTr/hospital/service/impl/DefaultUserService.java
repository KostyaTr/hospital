package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.*;
import com.github.KostyaTr.hospital.dao.impl.*;
import com.github.KostyaTr.hospital.model.*;
import com.github.KostyaTr.hospital.service.UserService;

import java.util.List;

public class DefaultUserService implements UserService {
    private PatientDao patientDao = DefaultPatientDao.getInstance();
    private MedicalServiceDao medicalServiceDao = DefaultMedicalServiceDao.getInstance();
    private MedicineDao medicineDao = DefaultMedicineDao.getInstance();
    private GuestPatientDao guestPatientDao = DefaultGuestPatientDao.getInstance();
    private MedDoctorDao medDoctorDao = DefaultMedDoctorDao.getInstance();

    private static class SingletonHolder {
        static final UserService HOLDER_INSTANCE = new DefaultUserService();
    }

    public static UserService getInstance() {
        return DefaultUserService.SingletonHolder.HOLDER_INSTANCE;
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
    public Long makeGuestAppointment(GuestPatient guestPatient) {
        return guestPatientDao.addPatient(guestPatient);
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

    @Override
    public List<MedDoctor> getDoctorsBySpeciality(Long specialityId) {
        return medDoctorDao.getDoctorBySpeciality(specialityId);
    }

    @Override
    public MedDoctor getDoctorById(Long doctorId) {
        return medDoctorDao.getDoctorById(doctorId);
    }

    public List<MedDoctor> getDoctorsByMedicalService(Long medicalServiceId) {
        return getDoctorsBySpeciality(medicalServiceDao.getMedicalServiceById(medicalServiceId).getNeededSpeciality().getSpecialityId());
    }

    @Override
    public void rescheduleAppointment(Patient patient) {
        patientDao.updateVisitDate(patient);
    }

    @Override
    public boolean cancelAppointment(Long patientId) {
        return patientDao.removePatientById(patientId);
    }
}
