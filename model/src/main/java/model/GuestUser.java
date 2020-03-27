package model;

public class GuestUser {
    private final String login = "Guest";
    private final String password = "null";
    private final Role role = Role.Guest;


    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public Role getRole() {
        return role;
    }
}
