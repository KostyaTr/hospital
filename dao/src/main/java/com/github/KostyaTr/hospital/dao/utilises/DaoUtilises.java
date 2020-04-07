package com.github.KostyaTr.hospital.dao.utilises;

import com.github.KostyaTr.hospital.model.Patient;

public class DaoUtilises {
    public static String patientName(Patient patient){
        return patient.getFirstName() + " " + patient.getLastName();
    }
}
