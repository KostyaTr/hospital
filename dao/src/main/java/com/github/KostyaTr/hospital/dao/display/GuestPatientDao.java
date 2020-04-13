package com.github.KostyaTr.hospital.dao.display;

import com.github.KostyaTr.hospital.model.display.GuestPatient;

import java.util.List;

public interface GuestPatientDao {
    List<GuestPatient> getPatientsByDoctorId(Long doctorId);
}
