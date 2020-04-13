package com.github.KostyaTr.hospital.web.servlet.info;

import com.github.KostyaTr.hospital.service.MedDoctorService;
import com.github.KostyaTr.hospital.service.impl.DefaultMedDoctorService;
import com.github.KostyaTr.hospital.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/personalDoctor/treatmentCourse")
public class TreatmentCoursesServlet extends HttpServlet {
    private MedDoctorService medDoctorService = DefaultMedDoctorService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("treatmentCourse", medDoctorService.getTreatmentCourses());
        WebUtils.forwardToJsp("treatmentCourse", req, resp);
    }
}
