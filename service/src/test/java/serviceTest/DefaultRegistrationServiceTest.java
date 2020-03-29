package serviceTest;

import dao.AuthUserDao;
import dao.UserDao;
import dao.impl.DefaultAuthUserDao;
import dao.impl.DefaultUserDao;
import model.AuthUser;
import model.Role;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import service.RegistrationService;
import service.impl.DefaultRegistrationService;

public class DefaultRegistrationServiceTest {

    @DisplayName("login Check")
    @Test
    public void loginCheck(){
        RegistrationService registrationService = DefaultRegistrationService.getInstance();
        UserDao userDao = DefaultUserDao.getInstance();

        User user = new User("User02", "Gaul", "Daune",
                "711-12-48", "GaulD@google.com");

        registrationService.saveUser(user);
        String unRepeatedLogin = "new login";
        String repeatedLogin = "User02";

        assertFalse(registrationService.loginCheck(unRepeatedLogin));
        assertTrue(registrationService.loginCheck(repeatedLogin));
    }

    @DisplayName("password Check")
    @Test
    public void passwordCheck(){
        RegistrationService registrationService = DefaultRegistrationService.getInstance();
        String password = "password";
        String incorrectRepeatedPassword = "pasword";
        String correctRepeatedPassword = "password";

        assertTrue(registrationService.passwordCheck(password, correctRepeatedPassword));
        assertFalse(registrationService.passwordCheck(password, incorrectRepeatedPassword));
    }

    @DisplayName("save User")
    @Test
    public void saveUser(){
        RegistrationService registrationService = DefaultRegistrationService.getInstance();
        UserDao userDao = DefaultUserDao.getInstance();

        User user = new User("User02", "Gaul", "Daune",
                "711-12-48", "GaulD@google.com");
        int initSize = userDao.getUsers().size();

        registrationService.saveUser(user);

        assertEquals(initSize + 1, userDao.getUsers().size());
        assertEquals(userDao.getUsers().get(initSize).getLogin(), "User02");
    }

    @DisplayName("save Auth User")
    @Test
    public void saveAuthUser(){
        RegistrationService registrationService = DefaultRegistrationService.getInstance();

        AuthUserDao authUserDao = DefaultAuthUserDao.getInstance();

        AuthUser authUser = new AuthUser("User02", "User02", Role.AuthorizedUser);

        registrationService.saveAuthUser(authUser);

        assertEquals(authUserDao.getByLogin("User02"), authUser);
    }

}
