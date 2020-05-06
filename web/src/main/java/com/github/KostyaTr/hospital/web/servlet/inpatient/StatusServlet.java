package com.github.KostyaTr.hospital.web.servlet.inpatient;

import com.github.KostyaTr.hospital.dao.MedDoctorDao;
import com.github.KostyaTr.hospital.dao.display.InpatientDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultMedDoctorDao;
import com.github.KostyaTr.hospital.dao.impl.display.DefaultInpatientDao;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.Status;
import com.github.KostyaTr.hospital.model.display.Inpatient;
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

@WebServlet("/personalDoctor/inpatientStatus")
public class StatusServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(StatusServlet.class);
    private MedDoctorService medDoctorService = DefaultMedDoctorService.getInstance();
    private MedDoctorDao medDoctorDao = DefaultMedDoctorDao.getInstance();
    private InpatientDao inpatientDao = DefaultInpatientDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getSession().getAttribute("inpatient") == null){
            req.setAttribute("error", "Please Select Inpatient First");
            AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
            final Long doctorId = medDoctorDao.getDoctorByUserId(authUser.getUserId())
                    .getDoctorId();
            final List<Inpatient> inpatientsByDoctorId = medDoctorService
                    .getInpatientsByDoctorId(
                            doctorId);
            req.setAttribute("inpatients", inpatientsByDoctorId);
            WebUtils.forwardToJsp("inpatient", req, resp);
        } else {
            WebUtils.forwardToJsp("inpatientStatus", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Status status = Status.valueOf(req.getParameter("status"));
        Inpatient inpatient = (Inpatient) req.getSession().getAttribute("inpatient");
        if ((status.equals(Status.GOOD) || status.equals(Status.CURED)) && inpatient.getMedicineName() == null){
            req.setAttribute("error", "It Can't Be That Inpatient Got Better Without Any Treatment");
            WebUtils.forwardToJsp("inpatientStatus", req, resp);
        } else {
            final Long inpatientId = inpatient.getInpatientId();
            medDoctorService.updateStatus(inpatientId, status);
            AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
            req.getSession().setAttribute("inpatient", inpatientDao.getInpatientById(inpatientId));
            log.info("Doctor {} Update Status {} to {}", authUser.getLogin(), status, inpatientId);
            try {
                resp.sendRedirect(req.getContextPath() +"/personalDoctor/inpatients");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
