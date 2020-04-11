package com.github.KostyaTr.hospital.web.servlet.authorization;

import com.github.KostyaTr.hospital.model.Card;
import com.github.KostyaTr.hospital.web.WebUtils;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.Role;
import com.github.KostyaTr.hospital.model.User;
import com.github.KostyaTr.hospital.service.RegistrationService;
import com.github.KostyaTr.hospital.service.impl.DefaultRegistrationService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {
    private RegistrationService registrationService = DefaultRegistrationService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        WebUtils.forwardToJsp("signUp", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {
        String login = req.getParameter("login");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phoneNumber = req.getParameter("phone");
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        String birthday = req.getParameter("birthday");
        boolean insurance = Boolean.parseBoolean( req.getParameter("insurance") );
        String password = req.getParameter("password");
        String passwordRepeat = req.getParameter("passwordRepeat");

        if (firstName.equals("") || lastName.equals("") || phoneNumber.equals("") ||
             email.equals("") || address.equals("")|| birthday.equals("")){
            req.setAttribute("inputError", "some fields are empty");
            WebUtils.forwardToJsp("signUp", req, resp);
        }

        if (registrationService.loginCheck(login)){
            req.setAttribute("loginError", "login already taken");
            WebUtils.forwardToJsp("signUp", req, resp);
        }

        if (!registrationService.passwordCheck(password, passwordRepeat)){
            req.setAttribute("passwordError", "passwords don't match");
            WebUtils.forwardToJsp("signUp", req, resp);
        }

        Long userId = registrationService.saveUser(new User(null, firstName, lastName, phoneNumber, email));
        registrationService.saveAuthUser(new AuthUser(null, login, password, Role.AuthorizedUser, userId));
        try {
            registrationService.saveCard(new Card(null, userId, "", address,
                    new SimpleDateFormat("yyyy-MM-dd").parse(birthday), insurance));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
