package com.github.KostyaTr.hospital.service;

import com.github.KostyaTr.hospital.model.*;

import java.util.List;

public interface MedDoctorService {
    List<Patient> getPatientsByDoctorId(Long doctorId);

    List<GuestPatient> getGuestPatientsByDoctorId(Long doctorId);

    List<Inpatient> getInpatientsByDoctorId(Long doctorId);

    boolean takeThePatient(Long patientId, Status status);

    void takeTheGuestPatient(Long guestPatientId);

    void updateDiagnose(Long patientId, String diagnose);

    boolean prescribeTreatmentCourse(Long patientId, Long treatmentCourseId);

    List<Medicine> getMedicine();

    Long createTreatmentCourse(TreatmentCourse treatmentCourse);

    void updateStatus(Long patientId, Status status);

    boolean dischargeInpatient(Inpatient inpatient);

    List<TreatmentCourse> getTreatmentCourses();

    Card getCardInfo(Long inpatientId);
}
