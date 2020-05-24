package com.github.KostyaTr.hospital.web.servlet.appointment;

import com.github.KostyaTr.hospital.model.MedDoctor;
import com.github.KostyaTr.hospital.service.UserService;
import com.github.KostyaTr.hospital.service.impl.DefaultUserService;
import com.github.KostyaTr.hospital.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/chooseDoctor")
public class ChooseDoctorServlet extends HttpServlet {
    private UserService userService = DefaultUserService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getSession().getAttribute("patientId") != null){
            try {
                resp.sendRedirect(req.getContextPath() +"/appointment");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        if (req.getSession().getAttribute("medicalServiceId") == null){
            try {
                resp.sendRedirect(req.getContextPath() +"/chooseMedicalServices");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            req.setAttribute("doctors",
                    userService.getDoctorsByMedicalService((Long) req.getSession().getAttribute("medicalServiceId")));
            WebUtils.forwardToJsp("chooseDoctor", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String doctor = req.getParameter("doctorId");
        if (doctor.equals("")){
            req.setAttribute("error", "field is empty");
            req.setAttribute("doctors",
                    userService.getDoctorsByMedicalService((Long) req.getSession().getAttribute("medicalServiceId")));
            WebUtils.forwardToJsp("chooseDoctor", req, resp);
        } else {
            int doctorId = Integer.parseInt(doctor);
            final List<MedDoctor> doctors = userService.getDoctorsByMedicalService((Long) req.getSession().getAttribute("medicalServiceId"));
            if (doctorId > doctors.size() || doctorId < 1){
                req.setAttribute("error", "incorrect input");
                req.setAttribute("doctors",
                        userService.getDoctorsByMedicalService((Long) req.getSession().getAttribute("medicalServiceId")));
                WebUtils.forwardToJsp("chooseDoctor", req, resp);
            } else {
                req.getSession().setAttribute("doctorId", doctors.get(doctorId - 1).getDoctorId());
                try {
                    resp.sendRedirect(req.getContextPath() +"/appointment");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
