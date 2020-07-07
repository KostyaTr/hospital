package com.github.KostyaTr.hospital.web.servlet.authorization;

import com.github.KostyaTr.hospital.model.Card;
import com.github.KostyaTr.hospital.web.WebUtils;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.Role;
import com.github.KostyaTr.hospital.model.User;
import com.github.KostyaTr.hospital.service.RegistrationService;
import com.github.KostyaTr.hospital.service.impl.DefaultRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(SignUpServlet.class);
    private RegistrationService registrationService = DefaultRegistrationService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        Object authUser = req.getSession().getAttribute("authUser");
        if (authUser == null) {
            WebUtils.forwardToJsp("signUp", req, resp);
        }
        else {
            try {
                resp.sendRedirect(req.getContextPath() +"/" + WebUtils.personalAccount(req, resp));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {
        String login = req.getParameter("login");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phoneNumber = req.getParameter("phone");
        String email = req.getParameter("email");
        String sex = req.getParameter("sex");
        String address = req.getParameter("address");
        String birthday = req.getParameter("birthday");
        boolean insurance = Boolean.parseBoolean( req.getParameter("insurance") );
        String password = req.getParameter("password");
        String passwordRepeat = req.getParameter("passwordRepeat");

        if (firstName.equals("") || lastName.equals("") || phoneNumber.equals("") ||
             email.equals("") || sex.equals("") || address.equals("")|| birthday.equals("")){
            req.setAttribute("inputError", "some fields are empty");
            WebUtils.forwardToJsp("signUp", req, resp);
        } else if (!registrationService.loginCheck(login)){
            req.setAttribute("loginError", "login already taken");
            WebUtils.forwardToJsp("signUp", req, resp);
        } else if (!registrationService.passwordCheck(password, passwordRepeat)){
            req.setAttribute("passwordError", "passwords don't match");
            WebUtils.forwardToJsp("signUp", req, resp);
        } else {
            Long userId = registrationService.saveUser(new User(null, firstName, lastName, phoneNumber, email));
            registrationService.saveAuthUser(new AuthUser(null, login, password, Role.AuthorizedUser, userId));
            registrationService.saveCard(new Card(null, userId, sex, address,
                    LocalDate.parse(birthday), insurance));
            log.info("user created:{} at {}", userId, LocalDateTime.now());
            try {
                resp.sendRedirect(req.getContextPath() + "/login");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
