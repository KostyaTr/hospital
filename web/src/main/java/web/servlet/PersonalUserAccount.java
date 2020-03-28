package web.servlet;

import dao.PatientDao;
import dao.UserDao;
import dao.impl.DefaultPatientDao;
import dao.impl.DefaultUserDao;
import model.AuthUser;
import model.Patient;
import model.User;
import service.UserService;
import service.impl.DefaultUserService;
import web.WebUtils;

import javax.servlet.ServletException;
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

        userService.makeAppointment(WebUtils.userToPatient(user, appointedDoctor, visitingTime));
    }
}