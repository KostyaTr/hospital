package serviceTest;

import com.github.KostyaTr.hospital.model.MedDoctor;
import com.github.KostyaTr.hospital.model.Patient;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.github.KostyaTr.hospital.service.UserService;
import com.github.KostyaTr.hospital.service.impl.DefaultUserService;

import java.util.List;


public class DefaultUserServiceTest {

    @DisplayName("get Doctors")
    @Test
    public void  getDoctors(){
        UserService userService = DefaultUserService.getInstance();

        List<MedDoctor> doctor = userService.getDoctors();

        assertNotNull(doctor);
    }

    @DisplayName("make Appointment")
    @Test
    public void makeAppointment(){
        UserService userService = DefaultUserService.getInstance();
        Patient patient = new Patient("User02", "Paul", "McCarbine",
                "951-28-36", "PaulMc@google.com", "Selma Karney", "Tuesday, 16, 15:00");

        userService.makeAppointment(patient);

        assertNotNull(userService.getAppointments("User02"));
        assertEquals(userService.getAppointments("User02")
                .get(userService.getAppointments("User02").size() - 1).getVisitingTime(), "Tuesday, 16, 15:00");
    }

    @DisplayName("get Appointments")
    @Test
   public void getAppointments(){
        UserService userService = DefaultUserService.getInstance();
        Patient patient = new Patient("User02", "Paul", "McCarbine",
                "951-28-36", "PaulMc@google.com", "Selma Karney", "Tuesday, 16, 15:00");

        userService.makeAppointment(patient);

        assertNotNull(userService.getAppointments("User02"));
    }
}
