package com.github.KostyaTr.hospital.service;

import com.github.KostyaTr.hospital.model.MedDoctor;
import com.github.KostyaTr.hospital.model.MedicalService;
import com.github.KostyaTr.hospital.model.Medicine;
import com.github.KostyaTr.hospital.model.Patient;

import java.util.List;

public interface UserService {
    List<MedDoctor> getDoctors();

    List<String> getDepartments();

    Long makeAppointment(Patient patient);

    List<Patient> getAppointmentsByUserId(Long userId);

    List<MedicalService> getMedicalServices();

    List<Medicine> getMedicine();
}
