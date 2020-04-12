package com.github.KostyaTr.hospital.web.servlet.personal;

import com.github.KostyaTr.hospital.dao.UserDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultUserDao;
import com.github.KostyaTr.hospital.web.WebUtils;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.User;
import com.github.KostyaTr.hospital.service.UserService;
import com.github.KostyaTr.hospital.service.impl.DefaultUserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/personalUser")
public class PersonalUserAccount extends HttpServlet {
    private UserDao userDao = DefaultUserDao.getInstance();
    private UserService userService = DefaultUserService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
        User user = userDao.getUserById(authUser.getUserId());
        req.setAttribute("appointments", userService.getAppointmentsByUserId(user.getUserId()));
        req.setAttribute("name", user.getFirstName() + " " + user.getLastName());
        WebUtils.forwardToJsp("personalAccount", req, resp);
    }
}
