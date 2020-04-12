package com.github.KostyaTr.hospital.web.servlet;

import com.github.KostyaTr.hospital.dao.MedicalServiceDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultMedicalServiceDao;
import com.github.KostyaTr.hospital.model.User;
import com.github.KostyaTr.hospital.service.UserService;
import com.github.KostyaTr.hospital.service.impl.DefaultUserService;
import com.github.KostyaTr.hospital.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/guestPage")
public class GuestPageServlet extends HttpServlet {
    private MedicalServiceDao medicalServiceDao = DefaultMedicalServiceDao.getInstance();
    private UserService userService = DefaultUserService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Object authUser = req.getSession().getAttribute("authUser");
        if (authUser != null){
            try {
                resp.sendRedirect(req.getContextPath() +"/" + WebUtils.personalAccount(req, resp));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(req.getSession().getAttribute("doctorId") == null){
            try {
                resp.sendRedirect("/appointment");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            WebUtils.forwardToJsp("guest's", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phoneNumber = req.getParameter("phone");
        String email = req.getParameter("email");

        if (firstName.equals("") || lastName.equals("") || phoneNumber.equals("") ||
                email.equals("")){
            req.setAttribute("incorrectInput", "some of the fields are empty");
            req.setAttribute("role", "Guest");
            WebUtils.forwardToJsp("personalAccount", req, resp);
        } else {
            Long medicalServiceId = (Long) req.getSession().getAttribute("medicalServiceId");
            Long doctorId = (Long) req.getSession().getAttribute("doctorId");
            req.setAttribute("medicalService", medicalServiceDao.getMedicalServiceById(medicalServiceId));
            req.setAttribute("doctor", userService.getDoctorById(doctorId));
            req.getSession().setAttribute("user", new User(null, firstName, lastName, phoneNumber, email));
            WebUtils.forwardToJsp("makeAppointment", req, resp);
        }
    }
}
