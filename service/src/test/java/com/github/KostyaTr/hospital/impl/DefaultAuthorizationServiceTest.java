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
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultAuthorizationServiceTest {
    @Mock
    AuthUserDao authUserDao;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    DefaultAuthorizationService authorizationService;

    @Test
    void loginNotExistTest(){
        when(authUserDao.getByLogin("nonExistingLogin")).thenReturn(null);
        AuthUser authUser = authorizationService.login("nonExistingLogin", "pass");
        assertNull(authUser);
    }

    @Test
    void passwordHashAndLoginCheck(){
        when(authUserDao.getByLogin("login")).thenReturn(
                new AuthUser(null, "login",
                        "encodedPass", Role.Doctor,null));
        when(passwordEncoder.encode("pass")).thenReturn("encodedPass");
        AuthUser authUser = authorizationService.login("login", "pass");
        assertNotNull(authUser);
        assertEquals("login", authUser.getLogin());
    }

    @Test
    void wrongPasswordCheck(){
        when(authUserDao.getByLogin("existingLogin")).thenReturn(
                new AuthUser(null, "existingLogin",
                        "passEncoded", Role.AuthorizedUser, null));
        when(passwordEncoder.encode("pass")).thenReturn("encodedPass");

        AuthUser authUser = authorizationService.login("existingLogin", "pass");
        assertNull(authUser);
    }
}
