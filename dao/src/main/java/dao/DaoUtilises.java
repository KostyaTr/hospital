package dao;

import model.MedDoctor;
import model.Patient;

public class DaoUtilises {
    public static String patientName(Patient patient){
        return patient.getFirstName() + " " + patient.getLastName();
    }

    public static String appointedDoctor(MedDoctor medDoctor){
        return medDoctor.getFirstName() + " " + medDoctor.getLastName();
    }
}
