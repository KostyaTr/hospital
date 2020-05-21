package com.github.KostyaTr.hospital.dao.converter;

import com.github.KostyaTr.hospital.dao.entity.UserEntity;
import com.github.KostyaTr.hospital.model.User;

public class UserConverter {
    public static User fromEntity(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return new User(
                userEntity.getUserId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getPhoneNumber(),
                userEntity.getEmail()
                );
    }

    public static UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }
        final UserEntity userEntity = new UserEntity();
        userEntity.setUserId(user.getUserId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        userEntity.setEmail(user.getEmail());
        return userEntity;
    }
}
