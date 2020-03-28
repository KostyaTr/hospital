package dao;

import model.User;

import java.util.List;

public interface UserDao {
    List<User> getUsers();

    void saveUser(User user);

    User getUserByLogin(String login);
}
