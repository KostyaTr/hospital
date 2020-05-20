package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.GuestPatient;

import java.util.Date;
import java.util.List;

public interface GuestPatientDao {
    List<GuestPatient> getPatientsByDoctorId(Long doctorId);

    List<GuestPatient> getPatients();

    Long addPatient(GuestPatient guestPatient);

    void removePatientById(Long patientId);

    int getLatestCouponToDoctorByDay(Long doctorId, int day);

    Date getLatestTimeToDoctorByDay(Long doctorId, int day);
}
