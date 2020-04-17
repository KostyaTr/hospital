package com.github.KostyaTr.hospital.web.servlet.appointment;

import com.github.KostyaTr.hospital.dao.MedicalServiceDao;
import com.github.KostyaTr.hospital.dao.UserDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultMedicalServiceDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultUserDao;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.GuestPatient;
import com.github.KostyaTr.hospital.model.Patient;
import com.github.KostyaTr.hospital.model.User;
import com.github.KostyaTr.hospital.service.QueueService;
import com.github.KostyaTr.hospital.service.UserService;
import com.github.KostyaTr.hospital.service.impl.DefaultQueueService;
import com.github.KostyaTr.hospital.service.impl.DefaultUserService;
import com.github.KostyaTr.hospital.web.WebUtils;
import com.github.KostyaTr.hospital.web.servlet.authorization.SignUpServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/appointment")
public class AppointmentServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(AppointmentServlet.class);
    private UserService userService = DefaultUserService.getInstance();
    private QueueService queueService = DefaultQueueService.getInstance();
    private UserDao userDao = DefaultUserDao.getInstance();
    private MedicalServiceDao medicalServiceDao = DefaultMedicalServiceDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Object medicalServiceId = req.getSession().getAttribute("medicalServiceId");
        if (medicalServiceId == null) {
            try {
                resp.sendRedirect(req.getContextPath() +"/chooseMedicalServices");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Object doctorId = req.getSession().getAttribute("doctorId");
            if (doctorId == null) {
                try {
                    resp.sendRedirect(req.getContextPath() +"/chooseDoctor");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
                if (authUser == null){
                    User user = (User) req.getSession().getAttribute("user");
                    if (user == null){
                        try {
                            resp.sendRedirect(req.getContextPath() +"/guestPage");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        req.setAttribute("user", user);
                        req.setAttribute("doctor", userService.getDoctorById((Long) doctorId));
                        req.setAttribute("medicalService", medicalServiceDao.getMedicalServiceById((Long) medicalServiceId));
                        WebUtils.forwardToJsp("makeAppointment", req, resp);
                    }
                } else {
                    req.setAttribute("user", userDao.getUserById(authUser.getUserId()));
                    req.setAttribute("doctor", userService.getDoctorById((Long) doctorId));
                    req.setAttribute("medicalService", medicalServiceDao.getMedicalServiceById((Long) medicalServiceId));
                    WebUtils.forwardToJsp("makeAppointment", req, resp);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String visitDate = req.getParameter("visitDate");

        Date visit = null;
        try {
            visit = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(visitDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long medicalServiceId = (Long) req.getSession().getAttribute("medicalServiceId");
        Long doctorId = (Long) req.getSession().getAttribute("doctorId");
        Long couponNum = queueService.getCouponNum(doctorId, visit);
        req.getSession().removeAttribute("medicalServiceId");
        req.getSession().removeAttribute("doctorId");
        if (req.getSession().getAttribute("authUser") == null) {
            User user = (User) req.getSession().getAttribute("user");
            Long id = userService.makeGuestAppointment(new GuestPatient(
                    null,
                    user.getFirstName(),
                    user.getLastName(),
                    user.getPhoneNumber(),
                    user.getEmail(),
                    doctorId,
                    couponNum,
                    medicalServiceId,
                    visit));
            req.setAttribute("coupon", couponNum);
            log.info("Guest User {} made appointment to Doctor {}", id, doctorId);
            WebUtils.forwardToJsp("guest's", req, resp);
        } else {
            AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
            userService.makeAppointment(new Patient(
                    null,
                    authUser.getUserId(),
                    doctorId,
                    couponNum,
                    medicalServiceId,
                    visit));
            log.info("User {} made appointment to Doctor {}", authUser.getLogin(), doctorId);
            try {
                resp.sendRedirect(req.getContextPath() + "/" + WebUtils.personalAccount(req, resp));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
