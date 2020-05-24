package com.github.KostyaTr.hospital.web.servlet.personal;

import com.github.KostyaTr.hospital.dao.PatientDao;
import com.github.KostyaTr.hospital.dao.ReceiptDao;
import com.github.KostyaTr.hospital.dao.UserDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultPatientDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultReceiptDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultUserDao;
import com.github.KostyaTr.hospital.model.Patient;
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
    private PatientDao patientDao = DefaultPatientDao.getInstance();
    private ReceiptDao receiptDao = DefaultReceiptDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
        User user = userDao.getUserById(authUser.getUserId());
        req.setAttribute("appointments", userService.getAppointmentsByUserId(user.getUserId()));
        req.setAttribute("appointmentsSize", userService.getAppointmentsByUserId(user.getUserId()).size());
        req.setAttribute("receipt", receiptDao.getReceiptByUserId(user.getUserId()));
        req.setAttribute("name", user.getFirstName() + " " + user.getLastName());
        WebUtils.forwardToJsp("user's", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
        User user = userDao.getUserById(authUser.getUserId());
        int appointmentId = Integer.parseInt(req.getParameter("appointmentId"));
        Long patientId = userService.getAppointmentsByUserId(user.getUserId()).get(appointmentId - 1).getPatientId();
        String cancelAppointment = req.getParameter("cancel");
        if (cancelAppointment != null){
            userService.cancelAppointment(patientId);
            try {
                resp.sendRedirect(req.getContextPath() + "/" + WebUtils.personalAccount(req, resp));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Patient patient = patientDao.getPatientById(patientId);
            req.getSession().setAttribute("doctorId", patient.getDoctor().getDoctorId());
            req.getSession().setAttribute("medicalServiceId", patient.getMedicalService().getMedicalServiceId());
            req.getSession().setAttribute("patientId", patientId);
            try {
                resp.sendRedirect(req.getContextPath() + "/appointment");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
