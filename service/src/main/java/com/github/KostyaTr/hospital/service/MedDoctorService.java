package com.github.KostyaTr.hospital.service;

import com.github.KostyaTr.hospital.model.*;

import java.util.List;

public interface MedDoctorService {
    List<Patient> getPatientsByDoctorId(Long doctorId);

    List<GuestPatient> getGuestPatientsByDoctorId(Long doctorId);

    boolean takeThePatient(Long patientId, String condition);

    boolean takeTheGuestPatient(Long guestPatientId);

    boolean updateDiagnose(Long patientId, String diagnose);

    boolean prescribeTreatmentCourse(Long patientId, Long treatmentCourseId);

    Card getCardByPatientId(Long patientId);

    List<Medicine> getMedicine();

    Long createTreatmentCourse(TreatmentCourse treatmentCourse);

    boolean updateTreatmentCourse(TreatmentCourse treatmentCourse);

    boolean updateStatus(Long patientId, String status);

    boolean dischargeInpatient(Long patientId)
}
