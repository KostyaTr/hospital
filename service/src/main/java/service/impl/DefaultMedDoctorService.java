package service.impl;

import dao.impl.DefaultPatientDao;
import model.Patient;
import service.MedDoctorService;
import dao.PatientDao;

import java.util.List;

public class DefaultMedDoctorService implements MedDoctorService {
    private PatientDao patientDao = DefaultPatientDao.getInstance();

    private static volatile MedDoctorService instance;

    public static MedDoctorService getInstance(){
        MedDoctorService localInstance = instance;
        if (localInstance == null){
            synchronized (MedDoctorService.class){
                localInstance = instance;
                if (localInstance == null){
                    instance = localInstance = new DefaultMedDoctorService();
                }
            }
        }
        return localInstance;
    }

    @Override
    public List<Patient> getPatientsByDoctor(String doctorName) {
        return patientDao.getPatientsByDoctor(doctorName);
    }

    @Override
    public List<Patient> getPatients() {
        return patientDao.getPatients();
    }

    @Override
    public void healed(String patientName) {
        patientDao.healed(patientName);
    }
}
