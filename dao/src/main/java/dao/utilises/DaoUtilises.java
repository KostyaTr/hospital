package dao.utilises;

import model.Patient;

public class DaoUtilises {
    public static String patientName(Patient patient){
        return patient.getFirstName() + " " + patient.getLastName();
    }
}
