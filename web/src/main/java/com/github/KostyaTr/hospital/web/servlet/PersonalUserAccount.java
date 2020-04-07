package com.github.KostyaTr.hospital.web.servlet;

import com.github.KostyaTr.hospital.dao.UserDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultUserDao;
import com.github.KostyaTr.hospital.web.WebUtils;
import model.AuthUser;
import model.User;
import service.UserService;
import service.impl.DefaultUserService;

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
        User user = userDao.getUserByLogin(authUser.getLogin());

        req.setAttribute("appointments", userService.getAppointments(authUser.getLogin()));
        req.setAttribute("name", user.getFirstName() + " " + user.getLastName());
        req.setAttribute("doctors", userService.getDoctors());
        req.setAttribute("role", "User");
        WebUtils.forwardToJsp("personalAccount", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String appointedDoctor = req.getParameter("doctorName");
        String visitingTime = req.getParameter("visitingTime");

        AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
        User user = userDao.getUserByLogin(authUser.getLogin());

        if (WebUtils.makeAppointmentCheck(appointedDoctor, userService.getDoctors())){
            userService.makeAppointment(WebUtils.userToPatient(user, appointedDoctor, visitingTime));
        } else {
            req.setAttribute("appointments", userService.getAppointments(authUser.getLogin()));
            req.setAttribute("name", user.getFirstName() + " " + user.getLastName());
            req.setAttribute("doctors", userService.getDoctors());
            req.setAttribute("incorrectInput", "incorrect doctor name");
            req.setAttribute("role", "User");
            WebUtils.forwardToJsp("personalAccount", req, resp);
        }

        try {
            resp.sendRedirect(req.getContextPath() +"/personalUser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
