package com.github.KostyaTr.hospital.web.servlet.inpatient;

import com.github.KostyaTr.hospital.dao.MedDoctorDao;
import com.github.KostyaTr.hospital.dao.InpatientDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultMedDoctorDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultInpatientDao;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.Inpatient;
import com.github.KostyaTr.hospital.model.TreatmentCourse;
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

@WebServlet("/personalDoctor/treatmentCourse")
public class TreatmentCourseServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(TreatmentCourseServlet.class);
    private MedDoctorService medDoctorService = DefaultMedDoctorService.getInstance();
    private MedDoctorDao medDoctorDao = DefaultMedDoctorDao.getInstance();
    private InpatientDao inpatientDao = DefaultInpatientDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Inpatient inpatient = (Inpatient) req.getSession().getAttribute("inpatient");
        AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
        final Long doctorId = medDoctorDao.getDoctorByUserId(authUser.getUserId())
                .getDoctorId();
        final List<Inpatient> inpatientsByDoctorId = medDoctorService
                .getInpatientsByDoctorId(doctorId);
        if (inpatient == null){
            req.setAttribute("inpatients", inpatientsByDoctorId);
            req.setAttribute("error", "Please Select Inpatient First");
            WebUtils.forwardToJsp("inpatient", req, resp);
        } else if(inpatient.getDiagnose() == null){
            req.setAttribute("inpatients", inpatientsByDoctorId);
            req.setAttribute("error", "You Can't Prescribe Treatment Course Without Diagnose");
            WebUtils.forwardToJsp("inpatient", req, resp);
        } else {
            req.setAttribute("treatmentCourse", medDoctorService.getTreatmentCourses());
            WebUtils.forwardToJsp("treatmentCourse", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String treatmentCourse = req.getParameter("treatmentCourseId");
        List<TreatmentCourse> courses =  medDoctorService.getTreatmentCourses();

        if (treatmentCourse.equals("")){
            req.setAttribute("error", "field is empty");
            req.setAttribute("treatmentCourse", courses);
            WebUtils.forwardToJsp("treatmentCourse", req, resp);
        } else {
            int treatmentCourseNum = Integer.parseInt(treatmentCourse);
            if (treatmentCourseNum > courses.size() || treatmentCourseNum < 1){
                req.setAttribute("error", "incorrect input");
                req.setAttribute("treatmentCourse", courses);
                WebUtils.forwardToJsp("treatmentCourse", req, resp);
            }
            Inpatient inpatient = (Inpatient) req.getSession().getAttribute("inpatient");
            Long treatmentCourseId = courses.get(treatmentCourseNum - 1).getTreatmentCourseId();
            final Long inpatientId = inpatient.getInpatientId();
            medDoctorService.prescribeTreatmentCourse(inpatientId, treatmentCourseId);
            AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
            log.info("Doctor {} Prescribe New Treatment Course {} to {}", authUser.getLogin(), treatmentCourseId, inpatientId);
            req.getSession().setAttribute("inpatient", inpatientDao.getInpatientById(inpatientId));
            try {
                resp.sendRedirect(req.getContextPath() +"/personalDoctor/inpatients");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
