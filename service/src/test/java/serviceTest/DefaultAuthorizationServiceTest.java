package serviceTest;

import model.AuthUser;
import service.AuthorizationService;
import service.impl.DefaultAuthorizationService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultAuthorizationServiceTest {

    @DisplayName("authorization Test")
    @Test
    public void authorizationTest() {
       AuthorizationService authorizationService = DefaultAuthorizationService.getInstance();
       String incorrectLogin = "no such user";
       String incorrectPassword = "incorrect password";
       String correctPassword = "User";
       String correctLogin = "User";

       AuthUser nullAuthUser1 = authorizationService.login(incorrectLogin, correctPassword);
       AuthUser nullAuthUser2 = authorizationService.login(correctLogin, incorrectPassword);
       AuthUser nullAuthUser3 = authorizationService.login(incorrectLogin, incorrectPassword);

       AuthUser authUser = authorizationService.login(correctLogin, correctPassword);


       assertNull(nullAuthUser1);
       assertNull(nullAuthUser2);
       assertNull(nullAuthUser3);

       assertEquals(authUser.getLogin(), "User");
    }

}
