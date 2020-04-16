package com.github.KostyaTr.hospital.web.servlet.inpatient;

import com.github.KostyaTr.hospital.dao.MedDoctorDao;
import com.github.KostyaTr.hospital.dao.display.InpatientDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultMedDoctorDao;
import com.github.KostyaTr.hospital.dao.impl.display.DefaultInpatientDao;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.display.Inpatient;
import com.github.KostyaTr.hospital.service.MedDoctorService;
import com.github.KostyaTr.hospital.service.impl.DefaultMedDoctorService;
import com.github.KostyaTr.hospital.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/personalDoctor/inpatients")
public class InpatientsServlet extends HttpServlet {
    private MedDoctorService medDoctorService = DefaultMedDoctorService.getInstance();
    private MedDoctorDao medDoctorDao = DefaultMedDoctorDao.getInstance();
    private InpatientDao inpatientDao = DefaultInpatientDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
        final Long doctorId = medDoctorDao.getDoctorByUserId(authUser.getUserId())
                .getDoctorId();
        final List<Inpatient> inpatientsByDoctorId = medDoctorService
                .getInpatientsByDoctorId(
                        doctorId);
        req.setAttribute("inpatients", inpatientsByDoctorId);
        WebUtils.forwardToJsp("inpatient", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
        List<Inpatient> inpatients = medDoctorService.getInpatientsByDoctorId(
                medDoctorDao.getDoctorByUserId(authUser.getUserId()).getDoctorId());

        String inpatientStr = req.getParameter("inpatientId");
        String option = req.getParameter("option");

        if (inpatientStr.equals("")) {
            req.setAttribute("error", "field is empty");
            req.setAttribute("inpatients", inpatients);
            WebUtils.forwardToJsp("inpatient", req, resp);
        } else {
            int inpatientNum = Integer.parseInt(inpatientStr);
            if (inpatientNum > inpatients.size() || inpatientNum < 1) {
                req.setAttribute("error", "incorrect input");
                req.setAttribute("inpatients", inpatients);
                WebUtils.forwardToJsp("inpatient", req, resp);
            } else {
                Inpatient inpatient = inpatientDao.getInpatientById(inpatients.get(inpatientNum - 1).getInpatientId());
                req.getSession().setAttribute("inpatient", inpatient);
                try {
                    if (option.equals("diagnose")){
                        resp.sendRedirect(req.getContextPath() +"/personalDoctor/diagnose");
                    } else if (option.equals("treatmentCourse")){
                        resp.sendRedirect(req.getContextPath() +"/personalDoctor/treatmentCourse");
                    } else if (option.equals("updateStatus")){
                        resp.sendRedirect(req.getContextPath() + "/personalDoctor/inpatientStatus");
                    } else {
                        if (medDoctorService.dischargeInpatient(inpatient)){
                            resp.sendRedirect(req.getContextPath() +"/personalDoctor/dischargedInpatients");
                        } else {
                            req.setAttribute("error", "You Can't Discharge Inpatient If He's Feeling Bad Or Not Dead");
                            req.setAttribute("inpatients", inpatients);
                            WebUtils.forwardToJsp("inpatient", req, resp);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}