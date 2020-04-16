package com.github.KostyaTr.hospital.web.servlet.inpatient;

import com.github.KostyaTr.hospital.dao.display.InpatientDao;
import com.github.KostyaTr.hospital.dao.MedDoctorDao;
import com.github.KostyaTr.hospital.dao.impl.display.DefaultInpatientDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultMedDoctorDao;
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

@WebServlet("/personalDoctor/diagnose")
public class DiagnoseServlet extends HttpServlet {
    private MedDoctorService medDoctorService = DefaultMedDoctorService.getInstance();
    private MedDoctorDao medDoctorDao = DefaultMedDoctorDao.getInstance();
    private InpatientDao inpatientDao = DefaultInpatientDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getSession().getAttribute("inpatient") == null){
            AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
            final Long doctorId = medDoctorDao.getDoctorByUserId(authUser.getUserId())
                    .getDoctorId();
            final List<Inpatient> inpatientsByDoctorId = medDoctorService
                    .getInpatientsByDoctorId(
                            doctorId);
            req.setAttribute("inpatients", inpatientsByDoctorId);
            req.setAttribute("error", "Please Select Inpatient First");
            WebUtils.forwardToJsp("inpatient", req, resp);
        } else {
            WebUtils.forwardToJsp("diagnose", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String diagnose = req.getParameter("diagnose");
        boolean prescribeTreatmentCourse = Boolean.parseBoolean(req.getParameter("prescribeTreatmentCourse"));
        Inpatient inpatient = (Inpatient) req.getSession().getAttribute("inpatient");
        if(diagnose.equals("")){
            WebUtils.forwardToJsp("diagnose", req, resp);
        } else {
            Long inpatientId = inpatient.getInpatientId();
            medDoctorService.updateDiagnose(inpatientId, diagnose);
            if (prescribeTreatmentCourse){
                try {
                    req.getSession().setAttribute("inpatient", inpatientDao.getInpatientById(inpatientId));
                    resp.sendRedirect(req.getContextPath() +"/personalDoctor/treatmentCourse");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                req.getSession().removeAttribute("inpatient");
                try {
                    resp.sendRedirect(req.getContextPath() +"/personalDoctor/inpatients");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
