package dao.impl;

import dao.DaoUtilises;
import model.Patient;
import dao.PatientDao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class DefaultPatientDao implements PatientDao {
    private List<Patient> patients;

    public DefaultPatientDao() {
        Calendar calendar = new GregorianCalendar(2020, Calendar.MAY , 10);
        calendar.set(Calendar.HOUR, 10);
        calendar.set(Calendar.MINUTE, 15);
        this.patients = new ArrayList<>();
        this.patients.add(new Patient("User", "Paul", "McCarbine",
                "951-28-36", "PaulMc@google.com", "Selma Karney", calendar));
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
        return patients;
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
    public void healed(String patientName) {
        for (int i = 0; i < this.patients.size(); i++) {
            if (patientName.equals(DaoUtilises.patientName(patients.get(i)))) {
                patients.remove(i);
            }
        }
    }
}