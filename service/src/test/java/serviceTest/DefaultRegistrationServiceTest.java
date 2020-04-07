package serviceTest;

import com.github.KostyaTr.hospital.dao.AuthUserDao;
import com.github.KostyaTr.hospital.dao.UserDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultAuthUserDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultUserDao;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.Role;
import com.github.KostyaTr.hospital.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.github.KostyaTr.hospital.service.RegistrationService;
import com.github.KostyaTr.hospital.service.impl.DefaultRegistrationService;

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
