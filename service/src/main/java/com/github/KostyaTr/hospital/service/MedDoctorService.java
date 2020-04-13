package com.github.KostyaTr.hospital.service;

import com.github.KostyaTr.hospital.model.*;

import java.util.List;

public interface MedDoctorService {
    List<com.github.KostyaTr.hospital.model.display.Patient> getPatientsByDoctorId(Long doctorId);

    List<com.github.KostyaTr.hospital.model.display.GuestPatient> getGuestPatientsByDoctorId(Long doctorId);

    List<com.github.KostyaTr.hospital.model.display.Inpatient> getInpatientsByDoctorId(Long doctorId);

    boolean takeThePatient(Long patientId, String condition);

    boolean takeTheGuestPatient(Long guestPatientId);

    List<Medicine> getMedicine();

    List<com.github.KostyaTr.hospital.model.display.TreatmentCourse> getTreatmentCourses();
}
