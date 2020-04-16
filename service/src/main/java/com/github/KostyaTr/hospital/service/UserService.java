package com.github.KostyaTr.hospital.service;

import com.github.KostyaTr.hospital.model.GuestPatient;
import com.github.KostyaTr.hospital.model.MedicalService;
import com.github.KostyaTr.hospital.model.Medicine;
import com.github.KostyaTr.hospital.model.Patient;
import com.github.KostyaTr.hospital.model.display.Appointment;
import com.github.KostyaTr.hospital.model.display.DoctorSpecialityDept;

import java.util.List;

public interface UserService {
    List<DoctorSpecialityDept> getDoctors();

    Long makeAppointment(Patient patient);

    Long makeGuestAppointment(GuestPatient patient);

    List<Appointment> getAppointmentsByUserId(Long userId);

    List<MedicalService> getMedicalServices();

    List<Medicine> getMedicine();

    List<DoctorSpecialityDept> getDoctorsBySpeciality(Long specialityId);

    DoctorSpecialityDept getDoctorById(Long doctorId);

    List<DoctorSpecialityDept> getDoctorsByMedicalService(Long medicalServiceId);
}
