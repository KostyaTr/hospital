package com.github.KostyaTr.hospital.service;

import com.github.KostyaTr.hospital.model.GuestPatient;
import com.github.KostyaTr.hospital.model.Patient;

import java.util.List;

public interface MedDoctorService {
    List<Patient> getPatientsByDoctorId(Long doctorId);

    List<GuestPatient> getGuestPatientsByDoctorId(Long doctorId);

    boolean takeThePatient(Long patientId, String condition);

    boolean takeTheGuestPatient(Long guestPatientId, String condition);

    //поставить диагноз стационарным, назначить курс лечения, операцию
    //читать мед карту стационарного
}
