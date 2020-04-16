package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.GuestPatient;

import java.util.List;

public interface GuestPatientDao {
    List<GuestPatient> getPatientsByDoctorId(Long doctorId);

    List<GuestPatient> getPatients();

    Long addPatient(GuestPatient guestPatient);

    boolean removePatientById(Long patientId);

    Long getLatestCouponToDoctorByDay(Long doctorId, int day);
}
