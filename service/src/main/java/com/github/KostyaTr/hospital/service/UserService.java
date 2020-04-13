package com.github.KostyaTr.hospital.service;

import com.github.KostyaTr.hospital.model.*;
import com.github.KostyaTr.hospital.model.display.Appointment;
import com.github.KostyaTr.hospital.model.display.DoctorSpecialityDept;

import java.util.List;

public interface UserService {
    List<DoctorSpecialityDept> getDoctors();

    List<String> getDepartments();

    Long makeAppointment(Patient patient);

    Long makeGuestAppointment(GuestPatient patient);

    List<Appointment> getAppointmentsByUserId(Long userId);

    List<MedicalService> getMedicalServices();

    List<Medicine> getMedicine();

    List<DoctorSpecialityDept> getDoctorsBySpeciality(Long specialityId);

    DoctorSpecialityDept getDoctorById(Long doctorId);

    List<DoctorSpecialityDept> getDoctorsByMedicalService(Long medicalServiceId);
}
