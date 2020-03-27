package model;

public class MedDoctor {
    private String firstName;
    private String lastName;
    private String speciality;
    private String phoneNumber;
    private String email;

    public MedDoctor(String firstName, String lastName, String speciality, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.speciality = speciality;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
