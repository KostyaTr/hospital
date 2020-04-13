package com.github.KostyaTr.hospital.web.servlet.patient;

import com.github.KostyaTr.hospital.dao.MedDoctorDao;
import com.github.KostyaTr.hospital.dao.UserDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultMedDoctorDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultUserDao;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.User;
import com.github.KostyaTr.hospital.model.display.GuestPatient;
import com.github.KostyaTr.hospital.service.MedDoctorService;
import com.github.KostyaTr.hospital.service.impl.DefaultMedDoctorService;
import com.github.KostyaTr.hospital.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/personalDoctor/guestPatient")
public class GuestPatientServlet extends HttpServlet {
    private MedDoctorService medDoctorService = DefaultMedDoctorService.getInstance();
    private MedDoctorDao medDoctorDao = DefaultMedDoctorDao.getInstance();
    private UserDao userDao = DefaultUserDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
        req.setAttribute("guestPatients", medDoctorService.getGuestPatientsByDoctorId(
                medDoctorDao.getDoctorByUserId(authUser.getUserId()).getDoctorId()));
        WebUtils.forwardToJsp("guestPatient", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
        List<GuestPatient> patients = medDoctorService.getGuestPatientsByDoctorId(
                medDoctorDao.getDoctorByUserId(authUser.getUserId()).getDoctorId());
        String patient = req.getParameter("patientId");
        if (patient.equals("")) {
            req.setAttribute("error", "field is empty");
            req.setAttribute("guestPatients", patients);
            WebUtils.forwardToJsp("guestPatient", req, resp);
        } else {
            int patientId = Integer.parseInt(patient);
            if (patientId > patients.size() || patientId < 1) {
                req.setAttribute("error", "incorrect input");
                req.setAttribute("guestPatients", patients);
                WebUtils.forwardToJsp("guestPatient", req, resp);
            } else {
                medDoctorService.takeTheGuestPatient(patients.get(patientId - 1).getPatientId());
                final Long userId = authUser.getUserId();
                User user = userDao.getUserById(userId);
                req.setAttribute("name", user.getFirstName() + " "+ user.getLastName());
                req.setAttribute("patient", "Patient Taken");
                WebUtils.forwardToJsp("doctor's", req, resp);
            }
        }
    }
}
