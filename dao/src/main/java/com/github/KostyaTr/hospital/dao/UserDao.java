package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.User;

import java.util.List;

public interface UserDao {
    List<User> getUsers();

    Long saveUser(User user);

    User getUserById(Long userId);

    boolean removeUserById(Long userId);
}
