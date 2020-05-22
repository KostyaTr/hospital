package com.github.KostyaTr.hospital.service;

import com.github.KostyaTr.hospital.model.*;

import java.util.List;

public interface UserService {
    List<MedDoctor> getDoctors();

    Long makeAppointment(Patient patient);

    Long makeGuestAppointment(GuestPatient patient);

    void rescheduleAppointment(Patient patient);

    boolean cancelAppointment(Long patientId);

    List<Patient> getAppointmentsByUserId(Long userId);

    List<MedicalService> getMedicalServices();

    List<Medicine> getMedicine();

    List<MedDoctor> getDoctorsBySpeciality(Long specialityId);

    MedDoctor getDoctorById(Long doctorId);

    List<MedDoctor> getDoctorsByMedicalService(Long medicalServiceId);
}
