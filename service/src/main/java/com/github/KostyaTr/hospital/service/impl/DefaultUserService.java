package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.MedDoctorDao;
import com.github.KostyaTr.hospital.dao.PatientDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultMedDoctorDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultPatientDao;
import com.github.KostyaTr.hospital.model.*;
import com.github.KostyaTr.hospital.service.UserService;

import java.util.List;

public class DefaultUserService implements UserService {
    private MedDoctorDao medDoctorDao = DefaultMedDoctorDao.getInstance();
    private PatientDao patientDao = DefaultPatientDao.getInstance();

    private static volatile UserService instance;

    public static UserService getInstance(){
        UserService localInstance = instance;
        if (localInstance == null){
            synchronized (UserService.class){
                localInstance = instance;
                if (localInstance == null){
                    instance = localInstance = new DefaultUserService();
                }
            }
        }
        return localInstance;
    }

    @Override
    public List<MedDoctor> getDoctors() {
        return medDoctorDao.getDoctors();
    }


    @Override
    public void makeAppointment(Patient patient) {
        patientDao.addPatient(patient);
    }

    @Override
    public List<Patient> getAppointments(String login) {
        return patientDao.getPatientsByLogin(login);
    }
}
