package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.AuthUser;

public interface AuthUserDao {
    AuthUser getByLogin(String login);

    void saveAuthUser(AuthUser user);
}
