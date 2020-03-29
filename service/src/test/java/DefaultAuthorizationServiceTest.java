import model.AuthUser;
import org.junit.Test;
import service.AuthorizationService;
import service.impl.DefaultAuthorizationService;

import static org.junit.Assert.*;


public class DefaultAuthorizationServiceTest {

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
