package com.github.KostyaTr.hospital.web.controller;

import com.github.KostyaTr.hospital.dao.ReceiptDao;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.Patient;
import com.github.KostyaTr.hospital.model.User;
import com.github.KostyaTr.hospital.service.PriceCalculationService;
import com.github.KostyaTr.hospital.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/personalAccount")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getPersonalPage(HttpServletRequest req, UsernamePasswordAuthenticationToken authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        User user = userService.getUserById(authUser.getUserId());
        req.setAttribute("appointments", userService.getAppointmentsByUserId(user.getUserId()));
        req.setAttribute("appointmentsSize", userService.getAppointmentsByUserId(user.getUserId()).size());
        req.setAttribute("receipt", userService.getReceipt(user.getUserId()));
        req.setAttribute("name", user.getFirstName() + " " + user.getLastName());
        return "personalAccount";
    }

    @PostMapping
    public String rescheduleAppointment(HttpServletRequest req, UsernamePasswordAuthenticationToken authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        User user = userService.getUserById(authUser.getUserId());
        int appointmentId = Integer.parseInt(req.getParameter("appointmentId"));
        Long patientId = userService.getAppointmentsByUserId(user.getUserId()).get(appointmentId - 1).getPatientId();
        String cancelAppointment = req.getParameter("cancel");
        if (cancelAppointment != null) {
            userService.cancelAppointment(patientId);
            return "redirect:/personalAccount";
        } else {
            Patient patient = userService.getAppointment(patientId);
            req.getSession().setAttribute("doctorId", patient.getDoctor().getDoctorId());
            req.getSession().setAttribute("medicalServiceId", patient.getMedicalService().getMedicalServiceId());
            req.getSession().setAttribute("patientId", patientId);
            return "redirect:/appointment";
        }
    }
}
