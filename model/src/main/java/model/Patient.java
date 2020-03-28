package model;

import java.util.Calendar;

public class Patient extends User {
    private String appointedDoctor;
    private String visitingTime;

    public Patient(String login, String firstName, String lastName,
                   String phoneNumber, String email, String appointedDoctor,
                   String visitingTime) {

        super(login, firstName, lastName, phoneNumber, email);
        this.appointedDoctor = appointedDoctor;
        this.visitingTime = visitingTime;
    }

    public String getAppointedDoctor() {
        return appointedDoctor;
    }

    public String getVisitingTime() {
        return visitingTime;
    }
}
