package com.github.KostyaTr.hospital.web.servlet.personal;

import com.github.KostyaTr.hospital.dao.UserDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultUserDao;
import com.github.KostyaTr.hospital.model.User;
import com.github.KostyaTr.hospital.web.WebUtils;
import com.github.KostyaTr.hospital.model.AuthUser;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/personalDoctor")
public class PersonalDoctorAccount extends HttpServlet {
    private UserDao userDao = DefaultUserDao.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
        final Long userId = authUser.getUserId();
        User user = userDao.getUserById(userId);
        req.setAttribute("name", user.getFirstName() + " "+ user.getLastName());
        WebUtils.forwardToJsp("doctor's", req, resp);
    }
}
