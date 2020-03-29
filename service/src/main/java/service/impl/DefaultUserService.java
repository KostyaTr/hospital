package service.impl;

import dao.impl.DefaultMedDoctorDao;
import dao.impl.DefaultPatientDao;
import model.*;
import dao.*;
import service.UserService;

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
