package com.github.KostyaTr.hospital.web.controller;

import com.github.KostyaTr.hospital.model.*;
import com.github.KostyaTr.hospital.service.QueueService;
import com.github.KostyaTr.hospital.service.UserService;
import com.github.KostyaTr.hospital.web.rq.GuestInfoRq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
    private static final Logger log = LoggerFactory.getLogger(AppointmentController.class);

    private final UserService userService;
    private final QueueService queueService;

    private LocalDateTime visitTime;

    public AppointmentController(UserService userService, QueueService queueService) {
        this.userService = userService;
        this.queueService = queueService;
    }

    @GetMapping("/chooseMedicalServices")
    public String chooseMedicalService(HttpServletRequest req) {
        if (req.getSession().getAttribute("patientId") != null) {
            return "redirect:/appointment";
        }
        req.setAttribute("services", userService.getMedicalServices());
        return "chooseMedicalService";
    }

    @PostMapping("/chooseMedicalServices")
    public String chosenMedicalService(HttpServletRequest req) {
        String service = req.getParameter("serviceId");
        if (checkForMedicalServiceNull(req, service)) {
            return "chooseMedicalService";
        }
        Long serviceId = Long.valueOf(service);
        if (checkForMedicalServiceInput(req, serviceId)) {
            return "chooseMedicalService";
        }
        req.getSession().setAttribute("medicalServiceId", serviceId);
        return "redirect:/appointment/chooseDoctor";
    }

    @GetMapping("/chooseDoctor")
    public String chooseDoctor(HttpServletRequest req) {
        if (req.getSession().getAttribute("patientId") != null) {
            return "redirect:/appointment";
        }
        Long medicalServiceId = (Long) req.getSession().getAttribute("medicalServiceId");
        if (medicalServiceId == null) {
            return "redirect:/appointment/chooseMedicalServices";
        }
        req.setAttribute("doctors", userService.getDoctorsByMedicalService(medicalServiceId));
        return "chooseDoctor";
    }

    @PostMapping("/chooseDoctor")
    public String chosenDoctor(HttpServletRequest req) {
        String doctor = req.getParameter("doctorId");
        if (checkDoctorForNull(req, doctor)) {
            return "chooseDoctor";
        }
        int doctorId = Integer.parseInt(doctor);
        final List<MedDoctor> doctors = userService.getDoctorsByMedicalService((Long) req.getSession().getAttribute("medicalServiceId"));
        if (checkForDoctorInput(req, doctorId, doctors)) {
            return "chooseDoctor";
        }
        req.getSession().setAttribute("doctorId", doctors.get(doctorId - 1).getDoctorId());
        return "redirect:/appointment";
    }

    @GetMapping
    public String makeAppointment(HttpServletRequest req, UsernamePasswordAuthenticationToken authentication) {
        Long medicalServiceId = (Long) req.getSession().getAttribute("medicalServiceId");
        if (medicalServiceId == null) {
            return "redirect:/appointment/chooseMedicalServices";
        }
        Long doctorId = (Long) req.getSession().getAttribute("doctorId");
        if (doctorId == null) {
            return "redirect:/appointment/chooseDoctor";
        }
        if (authentication == null) {
            User user = (User) req.getSession().getAttribute("user");
            if (user == null) {
                return "redirect:/appointment/guestInfo";
            } else {
                req.setAttribute("availableDays", queueService.getAvailableDays(doctorId));
                req.setAttribute("user", user);
                req.setAttribute("doctor", userService.getDoctorById(doctorId));
                req.setAttribute("medicalService", userService.getMedicalServiceById(medicalServiceId));
                return "makeAppointment";
            }
        } else {
            AuthUser authUser = (AuthUser) authentication.getPrincipal();
            req.setAttribute("availableDays", queueService.getAvailableDays(doctorId));
            req.setAttribute("user", userService.getUserById(authUser.getUserId()));
            req.setAttribute("doctor", userService.getDoctorById(doctorId));
            req.setAttribute("medicalService", userService.getMedicalServiceById(medicalServiceId));
            return "makeAppointment";
        }
    }

    @GetMapping("/guestInfo")
    public String guestInfo(HttpServletRequest req, UsernamePasswordAuthenticationToken authentication) {
        if (authentication != null) {
            return "redirect:/personalAccount";
        }
        if (req.getSession().getAttribute("doctorId") == null) {
            return "redirect:/appointment";
        }
        return "guest's";
    }

    @PostMapping("/guestInfo")
    public String getGuestInfo(HttpServletRequest req, GuestInfoRq rq) {
        String firstName = rq.getFirstName();
        String lastName = rq.getLastName();
        String phoneNumber = rq.getPhoneNumber();
        String email = rq.getEmail();

        if (firstName.equals("") || lastName.equals("") || phoneNumber.equals("") ||
                email.equals("")) {
            req.setAttribute("incorrectInput", "some of the fields are empty");
            return "guest's";
        }
        req.getSession().setAttribute("user", new User(null, firstName, lastName, phoneNumber, email));
        return "redirect:/appointment";

    }

    @PostMapping()
    public String appointment(HttpServletRequest req, UsernamePasswordAuthenticationToken authentication) {
        if (cancelRescheduling(req)) {
            return "redirect:/personalAccount";
        }

        Long doctorId = (Long) req.getSession().getAttribute("doctorId");
        String selectedDay = req.getParameter("selectDayButton");

        if (selectVisitTime(req, doctorId, selectedDay)) {
            return "makeAppointment";
        }

        Long medicalServiceId = (Long) req.getSession().getAttribute("medicalServiceId");
        int couponNum = queueService.getCouponNum(doctorId, visitTime.toLocalDate());
        req.getSession().removeAttribute("medicalServiceId");
        req.getSession().removeAttribute("doctorId");
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        if (authUser == null) {
            return guestAppointment(req, doctorId, medicalServiceId, couponNum);
        } else {
            User user = userService.getUserById(authUser.getUserId());
            final Patient patient = new Patient(
                    user.getUserId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getPhoneNumber(),
                    user.getEmail(),
                    null,
                    userService.getDoctorById(doctorId),
                    couponNum,
                    userService.getMedicalServiceById(medicalServiceId),
                    visitTime);
            if (req.getSession().getAttribute("patientId") != null) {
                rescheduleAppointment(req, doctorId, authUser, patient);
            } else {
                userService.makeAppointment(patient);
                log.info("User {} made appointment to Doctor {}", authUser.getLogin(), doctorId);
            }
            return "redirect:/personalAccount";
        }
    }

    private void rescheduleAppointment(HttpServletRequest req, Long doctorId, AuthUser authUser, Patient patient) {
        patient.setPatientId((Long) req.getSession().getAttribute("patientId"));
        req.getSession().removeAttribute("patientId");
        userService.rescheduleAppointment(patient);
        log.info("User {} updated appointment to Doctor {}", authUser.getLogin(), doctorId);
    }

    private String guestAppointment(HttpServletRequest req, Long doctorId, Long medicalServiceId, int couponNum) {
        User user = (User) req.getSession().getAttribute("user");
        Long id = userService.makeGuestAppointment(new GuestPatient(
                null,
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getEmail(),
                userService.getDoctorById(doctorId),
                couponNum,
                userService.getMedicalServiceById(medicalServiceId),
                visitTime));
        req.setAttribute("coupon", couponNum);
        log.info("Guest User {} made appointment to Doctor {}", id, doctorId);
        return "redirect:/appointment";
    }

    private boolean selectVisitTime(HttpServletRequest req, Long doctorId, String selectedDay) {
        if (selectedDay != null) {
            String visitDay = req.getParameter("visitDay");
            LocalDate visit = LocalDate.parse(visitDay);
            visitTime = queueService.getVisitTime(doctorId, visit);
            if (visitTime == null) {
                req.setAttribute("visitTime", "Sorry, There was no appointment left on this day");
            } else {
                req.setAttribute("visitTime", visitTime.format(DateTimeFormatter
                        .ofLocalizedDateTime(FormatStyle.FULL)
                        .withZone(ZoneId.systemDefault())
                        .withLocale(Locale.ENGLISH)));
            }
            return true;
        }
        return false;
    }

    private boolean cancelRescheduling(HttpServletRequest req) {
        if (req.getParameter("cancelRescheduling") != null) {
            req.getSession().removeAttribute("medicalServiceId");
            req.getSession().removeAttribute("doctorId");
            req.getSession().removeAttribute("patientId");
            return true;
        }
        return false;
    }


    private boolean checkForDoctorInput(HttpServletRequest req, int doctorId, List<MedDoctor> doctors) {
        if (doctorId > doctors.size() || doctorId < 1) {
            req.setAttribute("error", "incorrect input");
            req.setAttribute("doctors",
                    userService.getDoctorsByMedicalService((Long) req.getSession().getAttribute("medicalServiceId")));
            return true;
        }
        return false;
    }

    private boolean checkDoctorForNull(HttpServletRequest req, String doctor) {
        if (doctor.equals("")) {
            req.setAttribute("error", "field is empty");
            req.setAttribute("doctors",
                    userService.getDoctorsByMedicalService((Long) req.getSession().getAttribute("medicalServiceId")));
            return true;
        }
        return false;
    }


    private boolean checkForMedicalServiceInput(HttpServletRequest req, Long serviceId) {
        if (serviceId > userService.getMedicalServices().size() || serviceId < 1) {
            req.setAttribute("error", "incorrect input");
            req.setAttribute("services", userService.getMedicalServices());
            return true;
        }
        return false;
    }

    private boolean checkForMedicalServiceNull(HttpServletRequest req, String service) {
        if (service.equals("")) {
            req.setAttribute("error", "field is empty");
            req.setAttribute("services", userService.getMedicalServices());
            return true;
        }
        return false;
    }
}