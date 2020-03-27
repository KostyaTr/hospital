package service;

import model.AuthUser;

public interface AuthorizationService {
    AuthUser login(String login, String password);
}
