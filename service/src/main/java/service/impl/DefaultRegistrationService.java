package service.impl;

import dao.impl.DefaultAuthUserDao;
import dao.impl.DefaultUserDao;
import model.AuthUser;
import model.User;
import dao.AuthUserDao;
import service.RegistrationService;
import dao.UserDao;

public class DefaultRegistrationService implements RegistrationService {
    private UserDao userDao = DefaultUserDao.getInstance();
    private AuthUserDao authUserDao = DefaultAuthUserDao.getInstance();

    private static volatile RegistrationService instance;

    public static RegistrationService getInstance(){
        RegistrationService localInstance = instance;
        if (localInstance == null){
            synchronized (RegistrationService.class){
                localInstance = instance;
                if (localInstance == null){
                    localInstance = instance = new DefaultRegistrationService();
                }
            }
        }
        return localInstance;
    }

    @Override
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public void saveAuthUser(AuthUser user) {
        authUserDao.saveAuthUser(user);
    }
}
