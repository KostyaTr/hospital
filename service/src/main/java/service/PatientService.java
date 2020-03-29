package service;

import model.Patient;

public interface PatientService {
    void getSickMoreWhileStandingInLine(Patient patient);

    void standInLine(Patient patient);
}
