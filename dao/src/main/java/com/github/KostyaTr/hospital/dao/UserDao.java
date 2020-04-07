package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.User;

import java.util.List;

public interface UserDao {
    List<User> getUsers();

    void saveUser(User user);

    User getUserByLogin(String login);
}
