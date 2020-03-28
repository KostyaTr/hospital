package dao.impl;

import model.MedDoctor;
import dao.MedDoctorDao;

import java.util.ArrayList;
import java.util.List;

public class DefaultMedDoctorDao implements MedDoctorDao {
    private List<MedDoctor> doctors;

    public DefaultMedDoctorDao() {
        this.doctors = new ArrayList<>();
        this.doctors.add(new MedDoctor("Selma", "Karney",
                "Virologist", "198-10-77", "SelmKarney@google.com"));
        this.doctors.add(new MedDoctor( "Octavius", "Celestas","Surgeon",
                "728-25-47", "OctaCelesta@google.com"));
        this.doctors.add(new MedDoctor("Michael", "Kurd", "Therapist",
                "293-79-45", "KurMich@google.com"));
    }

    private static volatile MedDoctorDao instance;

    public static MedDoctorDao getInstance() {
        MedDoctorDao localInstance = instance;
        if (localInstance == null) {
            synchronized (MedDoctorDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DefaultMedDoctorDao();
                }
            }
        }
        return localInstance;
    }

    @Override
    public List<MedDoctor> getDoctors() {
        return doctors;
    }

}