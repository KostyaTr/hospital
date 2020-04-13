package com.github.KostyaTr.hospital.web.servlet.patient;

import com.github.KostyaTr.hospital.dao.MedDoctorDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultMedDoctorDao;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.display.Patient;
import com.github.KostyaTr.hospital.service.MedDoctorService;
import com.github.KostyaTr.hospital.service.impl.DefaultMedDoctorService;
import com.github.KostyaTr.hospital.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/personalDoctor/patient")
public class PatientServlet extends HttpServlet {
    private MedDoctorService medDoctorService = DefaultMedDoctorService.getInstance();
    private MedDoctorDao medDoctorDao = DefaultMedDoctorDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
        req.setAttribute("patients", medDoctorService.getPatientsByDoctorId(
                medDoctorDao.getDoctorByUserId(authUser.getUserId()).getDoctorId()));
        WebUtils.forwardToJsp("patient", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
        List<Patient> patients = medDoctorService.getPatientsByDoctorId(
                medDoctorDao.getDoctorByUserId(authUser.getUserId()).getDoctorId());
        String patient = req.getParameter("patientId");
        if (patient.equals("")) {
            req.setAttribute("error", "field is empty");
            req.setAttribute("patients", patients);
            WebUtils.forwardToJsp("patient", req, resp);
        } else {
            int patientNum = Integer.parseInt(patient);
            if (patientNum > patients.size() || patientNum < 1) {
                req.setAttribute("error", "incorrect input");
                req.setAttribute("patients", patients);
                WebUtils.forwardToJsp("patient", req, resp);
            } else {
                req.getSession().setAttribute("patientId", patients.get(patientNum - 1).getPatientId());
                try {
                    resp.sendRedirect(req.getContextPath() +"/personalDoctor/condition");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}