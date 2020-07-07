package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.GuestPatient;

import java.time.LocalDateTime;
import java.util.List;

public interface GuestPatientDao {
    List<GuestPatient> getPatientsByDoctorId(Long doctorId);

    List<GuestPatient> getPatients();

    Long addPatient(GuestPatient guestPatient);

    void removePatientById(Long patientId);

    int getLatestCouponToDoctorByDay(Long doctorId, int day);

    LocalDateTime getLatestTimeToDoctorByDay(Long doctorId, int day);
}
