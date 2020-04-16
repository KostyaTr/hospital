package com.github.KostyaTr.hospital.service;

import com.github.KostyaTr.hospital.model.Card;
import com.github.KostyaTr.hospital.model.Medicine;
import com.github.KostyaTr.hospital.model.TreatmentCourse;

import java.util.List;

public interface MedDoctorService {
    List<com.github.KostyaTr.hospital.model.display.Patient> getPatientsByDoctorId(Long doctorId);

    List<com.github.KostyaTr.hospital.model.display.GuestPatient> getGuestPatientsByDoctorId(Long doctorId);

    List<com.github.KostyaTr.hospital.model.display.Inpatient> getInpatientsByDoctorId(Long doctorId);

    List<com.github.KostyaTr.hospital.model.display.Inpatient> getUndiagnosedInpatientsByDoctorId(Long doctorId);

    boolean takeThePatient(Long patientId, String condition);

    boolean takeTheGuestPatient(Long guestPatientId);

    boolean updateDiagnose(Long patientId, String diagnose);

    boolean prescribeTreatmentCourse(Long patientId, Long treatmentCourseId);

    Card getCardByPatientId(Long patientId);

    List<Medicine> getMedicine();

    Long createTreatmentCourse(TreatmentCourse treatmentCourse);

    boolean updateTreatmentCourse(TreatmentCourse treatmentCourse);

    boolean updateStatus(Long patientId, String status);

    boolean dischargeInpatient(com.github.KostyaTr.hospital.model.display.Inpatient inpatient);

    List<com.github.KostyaTr.hospital.model.display.TreatmentCourse> getTreatmentCourses();
}
