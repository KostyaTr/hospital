import model.Patient;
import org.junit.Test;
import service.MedDoctorService;
import service.impl.DefaultMedDoctorService;

import static org.junit.Assert.*;
import java.util.List;

public class DefaultMedDoctorServiceTest {

    @Test
    public void getPatientsByDoctorTest(){
        MedDoctorService medDoctorService = DefaultMedDoctorService.getInstance();
        String doctorName = "Selma Karney";
        String doctorName1 = "Michael Kurd";

        List<Patient> patients = medDoctorService.getPatientsByDoctor(doctorName);
        List<Patient> noPatients = medDoctorService.getPatientsByDoctor(doctorName1);

        assertEquals(patients.get(0).getAppointedDoctor(), doctorName);
        assertEquals(patients.size(), 1);

        assertNull(noPatients);
    }
}
