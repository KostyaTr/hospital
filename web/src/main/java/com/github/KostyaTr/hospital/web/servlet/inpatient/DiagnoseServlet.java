package com.github.KostyaTr.hospital.web.servlet.inpatient;

import com.github.KostyaTr.hospital.dao.InpatientDao;
import com.github.KostyaTr.hospital.dao.MedDoctorDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultInpatientDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultMedDoctorDao;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.Inpatient;
import com.github.KostyaTr.hospital.service.MedDoctorService;
import com.github.KostyaTr.hospital.service.impl.DefaultMedDoctorService;
import com.github.KostyaTr.hospital.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/personalDoctor/diagnose")
public class DiagnoseServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(DiagnoseServlet.class);
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
                    .getInpatientsByDoctorId(doctorId);
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
            AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
            log.info("Doctor {} Set new diagnose to {}", authUser.getLogin(), inpatientId);
            req.getSession().setAttribute("inpatient", inpatientDao.getInpatientById(inpatientId));
            if (prescribeTreatmentCourse){
                try {
                    resp.sendRedirect(req.getContextPath() +"/personalDoctor/treatmentCourse");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                 try {
                    resp.sendRedirect(req.getContextPath() +"/personalDoctor/inpatients");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
