package com.github.KostyaTr.hospital.web.servlet.patient;

import com.github.KostyaTr.hospital.dao.UserDao;
import com.github.KostyaTr.hospital.dao.display.PatientDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultUserDao;
import com.github.KostyaTr.hospital.dao.impl.display.DefaultPatientDao;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.User;
import com.github.KostyaTr.hospital.model.display.Patient;
import com.github.KostyaTr.hospital.service.MedDoctorService;
import com.github.KostyaTr.hospital.service.impl.DefaultMedDoctorService;
import com.github.KostyaTr.hospital.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/personalDoctor/condition")
public class ConditionServlet extends HttpServlet {
    private MedDoctorService medDoctorService = DefaultMedDoctorService.getInstance();
    private PatientDao patientDaoDisp = DefaultPatientDao.getInstance();
    private UserDao userDao = DefaultUserDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Long patientId = (Long) req.getSession().getAttribute("patientId");
        if (patientId == null){
            try {
                resp.sendRedirect(req.getContextPath() +"/personalDoctor/patient");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            Patient patient = patientDaoDisp.getPatientById(patientId);
            req.setAttribute("patient", patient);
            WebUtils.forwardToJsp("condition", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
        Long patientId = (Long) req.getSession().getAttribute("patientId");
        Patient patient = patientDaoDisp.getPatientById(patientId);
        String condition = req.getParameter("condition");
        if (condition.equals("")) {
            req.setAttribute("error", "field is empty");
            req.setAttribute("patient", patient);
            WebUtils.forwardToJsp("patient", req, resp);
        } else {
            if (!medDoctorService.takeThePatient(patientId, condition)){
                req.setAttribute("error", "All chambers are full");
                req.setAttribute("patient", patient);
                WebUtils.forwardToJsp("patient", req, resp);
            } else {
                if (condition.equals("Bad")){
                    req.setAttribute("patient", "Patient was put in the hospital");
                } else {
                    req.setAttribute("patient", "Patient Taken");
                }
                final Long userId = authUser.getUserId();
                User user = userDao.getUserById(userId);
                req.setAttribute("name", user.getFirstName() + " "+ user.getLastName());
                WebUtils.forwardToJsp("doctor's", req, resp);
            }
        }
    }

}
