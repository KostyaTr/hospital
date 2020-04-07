package com.github.KostyaTr.hospital.service;

import com.github.KostyaTr.hospital.model.AuthUser;

public interface AuthorizationService {
    AuthUser login(String login, String password);
}
