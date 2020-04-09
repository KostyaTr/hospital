package com.github.KostyaTr.hospital.dao.utils;

import com.github.KostyaTr.hospital.model.Patient;

public class DaoUtilises {
    public static String patientName(Patient patient){
        return patient.getFirstName() + " " + patient.getLastName();
    }
}
