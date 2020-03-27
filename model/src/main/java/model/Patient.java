package model;

import java.util.Calendar;

public class Patient extends User {
    private String appointedDoctor;
    private Calendar visitingTime;

    public Patient(String login, String firstName, String lastName,
                   String phoneNumber, String email, String appointedDoctor,
                   Calendar visitingTime) {

        super(login, firstName, lastName, phoneNumber, email);
        this.appointedDoctor = appointedDoctor;
        this.visitingTime = visitingTime;
    }

    public String getAppointedDoctor() {
        return appointedDoctor;
    }

    public Calendar getVisitingTime() {
        return visitingTime;
    }
}
