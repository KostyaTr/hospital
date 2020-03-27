package service;

import model.AuthUser;
import model.User;

public interface RegistrationService {
    void saveUser(User user);

    void saveAuthUser(AuthUser user);
}
