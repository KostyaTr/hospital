package com.github.KostyaTr.hospital.web.controller;

import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.Card;
import com.github.KostyaTr.hospital.model.Role;
import com.github.KostyaTr.hospital.model.User;
import com.github.KostyaTr.hospital.service.AuthorizationService;
import com.github.KostyaTr.hospital.service.RegistrationService;
import com.github.KostyaTr.hospital.web.rq.SignUpUserRq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class AuthorizationController {
    private static final Logger log = LoggerFactory.getLogger(AuthorizationController.class);

    private final AuthorizationService authorizationService;
    private final RegistrationService registrationService;

    public AuthorizationController(AuthorizationService authorizationService,
                                   RegistrationService registrationService) {

        this.authorizationService = authorizationService;
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String login(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || "anonymousUser".equals(authentication.getPrincipal())) {
            return "login";
        }
        return "redirect:/personalAccount";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest rq) {
        String login = rq.getParameter("login");
        String password = rq.getParameter("password");
        AuthUser user = authorizationService.login(login, password);
        if (user == null) {
            rq.setAttribute("error", "login or password invalid");
            return "login";
        }
        log.info("user {} logged", user.getLogin());
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, getAuthorities(user));
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/personalAccount";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest rq) {
        SecurityContextHolder.clearContext();
        try {
            rq.logout();
        } catch (ServletException e) {
            throw new RuntimeException();
        }
        return "login";
    }

    @GetMapping("/signUp")
    public String signUp(HttpServletRequest req){
        Object authUser = req.getSession().getAttribute("authUser");
        if (authUser != null) {
            return "redirect:/personalAccount";
        }
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUp(HttpServletRequest req, SignUpUserRq user){
        String login = user.getLogin();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String phoneNumber = user.getPhoneNumber();
        String email = user.getEmail();
        String sex = user.getSex();
        String address = user.getAddress();
        String birthday = user.getBirthday();
        boolean insurance = user.isInsurance();
        String password = user.getPassword();
        String passwordRepeat = user.getPasswordRepeat();

        if (firstName.equals("") || lastName.equals("") || phoneNumber.equals("") ||
                email.equals("") || sex.equals("") || address.equals("")|| birthday.equals("")){
            req.setAttribute("inputError", "some fields are empty");
            return "signUp";
        } else if (!registrationService.loginCheck(login)){
            req.setAttribute("loginError", "login already taken");
            return "signUp";
        } else if (!registrationService.passwordCheck(password, passwordRepeat)){
            req.setAttribute("passwordError", "passwords don't match");
            return "signUp";
        }
        Long userId = registrationService.saveUser(
                new User(null, firstName, lastName, phoneNumber, email),
                new AuthUser(null, login, password, Role.AuthorizedUser, null),
                new Card(null, null, sex, address, LocalDate.parse(birthday), insurance));
        log.info("user created:{} at {}", userId, LocalDateTime.now());
        return "redirect:/login";
    }

    private List<GrantedAuthority> getAuthorities(AuthUser user) {
        if (user.getRole().equals(Role.Doctor)){
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_DOCTOR"));
            return grantedAuthorities;
        }
        else {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }
}
