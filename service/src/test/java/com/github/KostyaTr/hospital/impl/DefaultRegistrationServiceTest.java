package com.github.KostyaTr.hospital.impl;

import com.github.KostyaTr.hospital.dao.AuthUserDao;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.Role;
import com.github.KostyaTr.hospital.service.impl.DefaultRegistrationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultRegistrationServiceTest {
    @Mock
    AuthUserDao authUserDao;

    @InjectMocks
    DefaultRegistrationService registrationService;

    @Test
    void notRegisteredLoginCheckTest(){
        when(authUserDao.getByLogin("notRegisteredLogin")).thenReturn(null);
        boolean check = registrationService.loginCheck("notRegisteredLogin");
        assertTrue(check);
    }

    @Test
    void registeredLoginCheck(){
        when(authUserDao.getByLogin("registeredLogin")).thenReturn(new AuthUser(null, "existingLogin",
                "existingPassword", Role.AuthorizedUser, null));
        boolean check = registrationService.loginCheck("registeredLogin");
        assertFalse(check);
    }

    @Test
    void passwordCheckTest(){
        assertFalse(registrationService.passwordCheck("pass", "pas"));
        assertTrue(registrationService.passwordCheck("pass", "pass"));
    }
}
