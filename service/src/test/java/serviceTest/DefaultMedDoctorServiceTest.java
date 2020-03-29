package serviceTest;

import model.Patient;
import service.MedDoctorService;
import service.UserService;
import service.impl.DefaultMedDoctorService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.impl.DefaultUserService;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class DefaultMedDoctorServiceTest {

    @DisplayName("get Patients By Doctor")
    @Test
    public void getPatientsByDoctor(){
        MedDoctorService medDoctorService = DefaultMedDoctorService.getInstance();
        UserService userService = DefaultUserService.getInstance();
        String doctorName = "Selma Karney";
        String doctorName1 = "No such doctor";
        Patient patient = new Patient("User02", "Paul", "McCarbine",
                "951-28-36", "PaulMc@google.com", "Selma Karney", "Tuesday, 16, 15:00");

        userService.makeAppointment(patient);

        List<Patient> patients = medDoctorService.getPatientsByDoctor(doctorName);
        List<Patient> noPatients = medDoctorService.getPatientsByDoctor(doctorName1);

        assertNotNull(patients);
        assertEquals(patients.get(patients.size() - 1).getAppointedDoctor(), doctorName);
        assertEquals(patients.get(patients.size() - 1).getLogin(), "User02");

        assertNull(noPatients);
    }

    @DisplayName("get Patients")
    @Test
    public void getPatients(){
        MedDoctorService medDoctorService = DefaultMedDoctorService.getInstance();

        List<Patient> patients = medDoctorService.getPatients();

        assertNotNull(patients);
    }

    @DisplayName("cure Patient")
    @Test
    public void curePatient(){
        MedDoctorService medDoctorService = DefaultMedDoctorService.getInstance();
        UserService userService = DefaultUserService.getInstance();

        Patient patient = new Patient("User02", "Col", "McCarbine",
                "951-28-36", "PaulMc@google.com", "Selma Karney", "Tuesday, 16, 15:00");

        userService.makeAppointment(patient);

        int initSize = medDoctorService.getPatients().size();
        String patientName = "no such patient";
        String patientName2 = "Col McCarbine";

        medDoctorService.curePatient(patientName);
        assertEquals(initSize, medDoctorService.getPatients().size());

        medDoctorService.curePatient(patientName2);
        assertEquals(initSize - 1, medDoctorService.getPatients().size());
    }
}
