package com.github.KostyaTr.hospital.web.controller;

import com.github.KostyaTr.hospital.model.*;
import com.github.KostyaTr.hospital.service.MedDoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/personalDoctor")
public class PatientController {
    private static final Logger log = LoggerFactory.getLogger(PatientController.class);

    private final MedDoctorService medDoctorService;

    public PatientController(MedDoctorService medDoctorService) {
        this.medDoctorService = medDoctorService;
    }


    @GetMapping("/guestPatient")
    public String getGuestPatients(HttpServletRequest req, UsernamePasswordAuthenticationToken authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        req.setAttribute("guestPatients", medDoctorService.getGuestPatientsByDoctorId(
                medDoctorService.getDoctorByUserId(authUser.getUserId()).getDoctorId()));
        return "guestPatient";
    }

    @GetMapping("/patient")
    public String getPatients(HttpServletRequest req, UsernamePasswordAuthenticationToken authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        req.setAttribute("patients", medDoctorService.getPatientsByDoctorId(
                medDoctorService.getDoctorByUserId(authUser.getUserId()).getDoctorId()));
        return "patient";
    }

    @PostMapping("/guestPatient")
    public String takeGuestPatient(HttpServletRequest req, UsernamePasswordAuthenticationToken authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        List<GuestPatient> patients = medDoctorService.getGuestPatientsByDoctorId(
                medDoctorService.getDoctorByUserId(authUser.getUserId()).getDoctorId());
        String patient = req.getParameter("patientId");
        if (patient.equals("")) {
            req.setAttribute("error", "field is empty");
            req.setAttribute("guestPatients", patients);
            return "guestPatient";
        } else {
            int patientId = Integer.parseInt(patient);
            if (patientId > patients.size() || patientId < 1) {
                req.setAttribute("error", "incorrect input");
                req.setAttribute("guestPatients", patients);
                return "guestPatient";
            } else {
                final Long guestPatientId = patients.get(patientId - 1).getPatientId();
                medDoctorService.takeTheGuestPatient(guestPatientId);
                log.info("Doctor {} Take Guest Patient {} at {}", authUser.getLogin(), guestPatientId, LocalDateTime.now());
                final Long userId = authUser.getUserId();
                MedDoctor user = medDoctorService.getDoctorByUserId(userId);
                req.setAttribute("name", user.getFirstName() + " " + user.getLastName());
                req.setAttribute("patient", "Patient Taken");
                return "personalAccount";
            }
        }
    }

    @PostMapping("/patient")
    public String takePatient(HttpServletRequest req, UsernamePasswordAuthenticationToken authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        List<Patient> patients = medDoctorService.getPatientsByDoctorId(
                medDoctorService.getDoctorByUserId(authUser.getUserId()).getDoctorId());
        String patient = req.getParameter("patientId");
        if (patient.equals("")) {
            req.setAttribute("error", "field is empty");
            req.setAttribute("patients", patients);
            return "patient";
        }
        int patientNum = Integer.parseInt(patient);
        if (patientNum > patients.size() || patientNum < 1) {
            req.setAttribute("error", "incorrect input");
            req.setAttribute("patients", patients);
            return "patient";
        }
        req.getSession().setAttribute("patientId", patients.get(patientNum - 1).getPatientId());
        return "redirect:/personalDoctor/condition";
    }

    @GetMapping("/condition")
    public String condition(HttpServletRequest req) {
        Long patientId = (Long) req.getSession().getAttribute("patientId");
        if (patientId == null) {
            return "redirect:/personalDoctor/patient";
        }
        Patient patient = medDoctorService.getPatientById(patientId);
        req.setAttribute("patient", patient);
        return "condition";
    }

    @PostMapping("/condition")
    public String hospitalizePatient(HttpServletRequest req, UsernamePasswordAuthenticationToken authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        Long patientId = (Long) req.getSession().getAttribute("patientId");
        Patient patient = medDoctorService.getPatientById(patientId);
        Status condition = Status.valueOf(req.getParameter("status"));
        if (!medDoctorService.takeThePatient(patientId, condition)){
            req.setAttribute("error", "All chambers are full");
            req.setAttribute("patient", patient);
            return "patient";
        } else {
            if (condition.equals(Status.BAD)){
                log.info("Doctor {} Put Patient {} In The Hospital at {}", authUser.getLogin(), patient.getPatientId(), LocalDateTime.now());
                req.setAttribute("patient", "Patient was put in the hospital");
            } else {
                log.info("Doctor {} Take Patient {} at {}", authUser.getLogin(), patient.getPatientId(), LocalDateTime.now());
                req.setAttribute("patient", "Patient Taken");
            }
            req.getSession().removeAttribute("patientId");
            final Long userId = authUser.getUserId();
            MedDoctor user = medDoctorService.getDoctorByUserId(userId);
            req.setAttribute("name", user.getFirstName() + " "+ user.getLastName());
            return "personalAccount";
        }
    }
}
