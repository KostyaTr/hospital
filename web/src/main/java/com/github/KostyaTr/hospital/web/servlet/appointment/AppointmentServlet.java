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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

@WebServlet("/appointment")
public class AppointmentServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(AppointmentServlet.class);
    private UserService userService = DefaultUserService.getInstance();
    private QueueService queueService = DefaultQueueService.getInstance();
    private UserDao userDao = DefaultUserDao.getInstance();
    private MedicalServiceDao medicalServiceDao = DefaultMedicalServiceDao.getInstance();
    private LocalDateTime visitTime;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Long medicalServiceId = (Long) req.getSession().getAttribute("medicalServiceId");
        if (medicalServiceId == null) {
            try {
                resp.sendRedirect(req.getContextPath() +"/chooseMedicalServices");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Long doctorId = (Long) req.getSession().getAttribute("doctorId");
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
                        req.setAttribute("availableDays", queueService.getAvailableDays(doctorId));
                        req.setAttribute("user", user);
                        req.setAttribute("doctor", userService.getDoctorById(doctorId));
                        req.setAttribute("medicalService", medicalServiceDao.getMedicalServiceById(medicalServiceId));
                        WebUtils.forwardToJsp("makeAppointment", req, resp);
                    }
                } else {
                    req.setAttribute("availableDays", queueService.getAvailableDays(doctorId));
                    req.setAttribute("user", userDao.getUserById(authUser.getUserId()));
                    req.setAttribute("doctor", userService.getDoctorById(doctorId));
                    req.setAttribute("medicalService", medicalServiceDao.getMedicalServiceById(medicalServiceId));
                    WebUtils.forwardToJsp("makeAppointment", req, resp);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Long doctorId = (Long) req.getSession().getAttribute("doctorId");
        String selectedDay = req.getParameter("selectDayButton");

        if (selectedDay != null){
            String visitDay = req.getParameter("visitDay");
            LocalDate visit = LocalDate.parse(visitDay);
            visitTime = queueService.getVisitTime(doctorId, visit);
            if (visitTime == null){
                req.setAttribute("visitTime", "Sorry, There was no appointment left on this day");
            } else {
                req.setAttribute("visitTime", visitTime.format(DateTimeFormatter
                        .ofLocalizedDateTime(FormatStyle.FULL)
                        .withZone(ZoneId.systemDefault())
                        .withLocale(Locale.ENGLISH)));
            }
            WebUtils.forwardToJsp("makeAppointment", req, resp);
            return;
        }
        Long medicalServiceId = (Long) req.getSession().getAttribute("medicalServiceId");
        int couponNum = queueService.getCouponNum(doctorId, visitTime.toLocalDate());
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
                    Date.from(visitTime.atZone( ZoneId.systemDefault()).toInstant())));
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
                    Date.from(visitTime.atZone( ZoneId.systemDefault()).toInstant())));
            log.info("User {} made appointment to Doctor {}", authUser.getLogin(), doctorId);
            try {
                resp.sendRedirect(req.getContextPath() + "/" + WebUtils.personalAccount(req, resp));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
