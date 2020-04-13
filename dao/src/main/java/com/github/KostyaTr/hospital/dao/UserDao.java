package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.User;

public interface UserDao {
    Long saveUser(User user);

    User getUserById(Long userId);
}
