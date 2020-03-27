package model;

public class User {
    private String login;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;


    public User(String login, String firstName,
                String lastName, String phoneNumber, String email) {

        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
