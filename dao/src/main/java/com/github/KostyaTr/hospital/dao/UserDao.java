package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.User;

import java.util.List;

public interface UserDao {

    Long saveUser(User user);

    User getUserById(Long userId);

    boolean removeUser(Long userId);
}
