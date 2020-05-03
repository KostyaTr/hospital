package com.github.KostyaTr.hospital.impl;

import com.github.KostyaTr.hospital.dao.AuthUserDao;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.Role;
import com.github.KostyaTr.hospital.service.impl.DefaultAuthorizationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultAuthorizationServiceTest {
    @Mock
    AuthUserDao authUserDao;

    @InjectMocks
    DefaultAuthorizationService authorizationService;

    @Test
    void loginNotExistTest(){
        when(authUserDao.getByLogin("nonExistingLogin")).thenReturn(null);
        AuthUser authUser = authorizationService.login("nonExistingLogin", "pass");
        assertNull(authUser);
    }

    @Test
    void loginExistTest(){
        when(authUserDao.getByLogin("existingLogin")).thenReturn(
                new AuthUser(null, "existingLogin",
                        "existingPassword", Role.AuthorizedUser, null));

        AuthUser authUser = authorizationService.login("existingLogin", "existingPassword");
        assertNotNull(authUser);
        assertEquals("existingLogin", authUser.getLogin());
    }

    @Test
    void passwordHashCheck(){
        when(authUserDao.getByLogin("login")).thenReturn(
                new AuthUser(null, "login",
                        "a94d4950bba6053ab0fa6feb2ebe04ec", Role.Doctor,null));
        AuthUser authUser = authorizationService.login("login", "Virus");
        assertNotNull(authUser);
    }

    @Test
    void wrongPasswordCheck(){
        when(authUserDao.getByLogin("existingLogin")).thenReturn(
                new AuthUser(null, "existingLogin",
                        "existingPassword", Role.AuthorizedUser, null));

        AuthUser authUser = authorizationService.login("existingLogin", "pass");
        assertNull(authUser);
    }
}
