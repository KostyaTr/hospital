package dao;

import model.MedDoctor;

import java.util.List;

public interface MedDoctorDao {
    List<MedDoctor> getDoctors();

    MedDoctor getDoctorByLogin(String login);

    boolean removeDoctor(MedDoctor medDoctor);
}
