package com.github.KostyaTr.hospital.dao.converter;


import com.github.KostyaTr.hospital.dao.entity.AuthUserEntity;
import com.github.KostyaTr.hospital.model.AuthUser;

public class AuthUserConverter {
    public static AuthUser fromEntity(AuthUserEntity authUserEntity) {
        if (authUserEntity == null) {
            return null;
        }
        return new AuthUser(
                authUserEntity.getAuthUserId(),
                authUserEntity.getLogin(),
                authUserEntity.getPassword(),
                authUserEntity.getRole(),
                authUserEntity.getUserId());
    }

    public static AuthUserEntity toEntity(AuthUser authUser) {
        if (authUser == null) {
            return null;
        }
        final AuthUserEntity authUserEntity = new AuthUserEntity();
        authUserEntity.setAuthUserId(authUser.getAuthUserId());
        authUserEntity.setLogin(authUser.getLogin());
        authUserEntity.setPassword(authUser.getPassword());
        authUserEntity.setRole(authUser.getRole());
        authUserEntity.setUserId(authUser.getUserId());
        return authUserEntity;
    }
}
