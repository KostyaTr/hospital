package com.github.KostyaTr.hospital.web.servlet;

import com.github.KostyaTr.hospital.dao.display.DoctorSpecialityDeptDao;
import com.github.KostyaTr.hospital.dao.impl.display.DefaultDoctorSpecialityDeptDao;
import com.github.KostyaTr.hospital.model.display.DoctorSpecialityDept;
import com.github.KostyaTr.hospital.web.WebUtils;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.MedDoctor;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/personalDoctor")
public class PersonalDoctorAccount extends HttpServlet {
    private DoctorSpecialityDeptDao doctorSpecialityDeptDao = DefaultDoctorSpecialityDeptDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
        final Long userId = authUser.getUserId();
        final DoctorSpecialityDept doctorById = doctorSpecialityDeptDao.getDoctorByUserId(userId);
        DoctorSpecialityDept doctor = doctorById;

        req.setAttribute("name", doctor.getFirstName() + " "+ doctor.getLastName());
        WebUtils.forwardToJsp("personalAccount", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String patientName = req.getParameter("patientName");

        medDoctorService.curePatient(patientName);

        try {
            resp.sendRedirect(req.getContextPath() +"/personalDoctor");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
