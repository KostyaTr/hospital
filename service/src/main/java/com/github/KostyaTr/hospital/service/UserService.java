package com.github.KostyaTr.hospital.service;

import com.github.KostyaTr.hospital.model.MedDoctor;
import com.github.KostyaTr.hospital.model.Patient;

import java.util.List;

public interface UserService {
    List<MedDoctor> getDoctors();

    void makeAppointment(Patient patient);

    List<Patient> getAppointments(String login);
}
