package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.model.MedDoctor;
import com.github.KostyaTr.hospital.dao.MedDoctorDao;

import java.util.ArrayList;
import java.util.List;

public class DefaultMedDoctorDao implements MedDoctorDao {
    private List<MedDoctor> doctors;

    public DefaultMedDoctorDao() {
        this.doctors = new ArrayList<>();
        this.doctors.add(new MedDoctor("Virologist","Selma", "Karney",
                "Virologist", "198-10-77", "SelmKarney@google.com"));
        this.doctors.add(new MedDoctor( "Surgeon","Octavius", "Celestas","Surgeon",
                "728-25-47", "OctaCelesta@google.com"));
        this.doctors.add(new MedDoctor("Therapist","Michael", "Kurd", "Therapist",
                "293-79-45", "KurMich@google.com"));
    }

    @Override
    public MedDoctor getDoctorByLogin(String login) {
        for (MedDoctor doctor : doctors) {
            if (login.equals(doctor.getLogin())){
                return doctor;
            }
        }
        return null;
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

    @Override
    public boolean removeDoctor(MedDoctor medDoctor) {
        for (int i = 0; i < this.doctors.size(); i++) {
            if (medDoctor.getLogin().equals(this.doctors.get(i).getLogin())) {
                doctors.remove(i);
                return true;
            }
        }
        return false;
    }
}
