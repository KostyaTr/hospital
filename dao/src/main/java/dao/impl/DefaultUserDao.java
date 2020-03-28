package dao.impl;

import model.User;
import dao.UserDao;


import java.util.ArrayList;
import java.util.List;

public class DefaultUserDao implements UserDao{

    private List<User> users;

    public DefaultUserDao() {
        this.users = new ArrayList<>();
        this.users.add(new User("User", "Paul", "McCarbine",
                "951-28-36", "PaulMc@google.com"));
        this.users.add(new User("User01", "Gaul", "Daune",
                "711-12-48", "GaulD@google.com"));
        this.users.add(new User("Therapist", "Michael", "Kurd",
                "293-79-45", "KurMich@google.com"));
        this.users.add(new User("Virologist", "Selma", "Karney",
                "198-10-77", "SelmKarney@google.com"));
        this.users.add(new User("Surgeon", "Octavius", "Celestas",
                "728-25-47", "OctaCelesta@google.com"));
    }

    private static volatile DefaultUserDao instance;

    public static DefaultUserDao getInstance() {
        DefaultUserDao localInstance = instance;
        if (localInstance == null) {
            synchronized (DefaultUserDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DefaultUserDao();
                }
            }
        }
        return localInstance;
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public void saveUser(User user) {
        users.add(user);
    }

    @Override
    public User getUserByLogin(String login) {
        for (User user : users) {
            if (login.equals(user.getLogin())){
                return user;
            }
        }
        return null;
    }
}
