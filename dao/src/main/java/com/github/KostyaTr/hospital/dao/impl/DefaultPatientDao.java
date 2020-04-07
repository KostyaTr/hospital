package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.utilises.DaoUtilises;
import com.github.KostyaTr.hospital.model.Patient;
import com.github.KostyaTr.hospital.dao.PatientDao;

import java.util.ArrayList;
import java.util.List;

public class DefaultPatientDao implements PatientDao {
    private List<Patient> patients;

    public DefaultPatientDao() {
        this.patients = new ArrayList<>();
        this.patients.add(new Patient("User", "Paul", "McCarbine",
                "951-28-36", "PaulMc@google.com", "Selma Karney", "Tuesday, 16, 15:00"));
    }

    private static volatile PatientDao instance;

    public static PatientDao getInstance() {
        PatientDao localInstance = instance;
        if (localInstance == null) {
            synchronized (PatientDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DefaultPatientDao();
                }
            }
        }
        return localInstance;
    }

    @Override
    public List<Patient> getPatientsByDoctor(String doctorName) {
        List<Patient> patients = new ArrayList<>();
        for (Patient patient : this.patients) {
            if (doctorName.equals(patient.getAppointedDoctor())) {
                patients.add(patient);
            }
        }
        if (patients.size() != 0){
            return patients;
        } else {
            return null;
        }
    }

    @Override
    public List<Patient> getPatients() {
        return patients;
    }

    @Override
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    @Override
    public boolean removePatient(String patientName) {
        for (int i = 0; i < this.patients.size(); i++) {
            if (patientName.equals(DaoUtilises.patientName(patients.get(i)))) {
                patients.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Patient> getPatientsByLogin(String login) {
        List<Patient> patientsByLogin = new ArrayList<>();
        for (Patient patient : patients) {
            if (patient.getLogin().equals(login)){
                patientsByLogin.add(patient);
            }
        }
        return patientsByLogin;
    }
}