package service;

import model.MedDoctor;
import model.Patient;

import java.util.List;

public interface UserService {
    List<MedDoctor> getDoctors();

    void makeAppointment(Patient patient);

    List<Patient> getAppointments(String login);
}
