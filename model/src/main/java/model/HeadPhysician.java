package model;

public class HeadPhysician extends MedDoctor{
    private String department;


    public HeadPhysician(String login, String firstName,
                         String lastName, String speciality, String phoneNumber,
                         String email, String department) {

        super(login, firstName, lastName, speciality, phoneNumber, email);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }
}
