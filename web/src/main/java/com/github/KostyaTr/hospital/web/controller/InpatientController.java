package com.github.KostyaTr.hospital.web.controller;

import com.github.KostyaTr.hospital.model.*;
import com.github.KostyaTr.hospital.service.MedDoctorService;
import com.github.KostyaTr.hospital.web.rq.CreateTreatmentCourseRq;
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
public class InpatientController {
    private static final Logger log = LoggerFactory.getLogger(InpatientController.class);

    private final MedDoctorService medDoctorService;

    public InpatientController(MedDoctorService medDoctorService) {
        this.medDoctorService = medDoctorService;
    }

    @GetMapping("/inpatients")
    public String inpatients(HttpServletRequest req, UsernamePasswordAuthenticationToken authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        final Long doctorId = medDoctorService.getDoctorByUserId(authUser.getUserId())
                .getDoctorId();
        final List<Inpatient> inpatientsByDoctorId = medDoctorService
                .getInpatientsByDoctorId(doctorId);
        req.setAttribute("inpatients", inpatientsByDoctorId);
        return "inpatient";
    }

    @PostMapping("/inpatients")
    public String selectInpatient(HttpServletRequest req, UsernamePasswordAuthenticationToken authentication) {
        if (selectAnotherInpatient(req)) {
            return "redirect:/personalDoctor/inpatients";
        }

        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        List<Inpatient> inpatients = medDoctorService.getInpatientsByDoctorId(
                medDoctorService.getDoctorByUserId(authUser.getUserId()).getDoctorId());

        Inpatient inpatient = (Inpatient) req.getSession().getAttribute("inpatient");
        if (inpatient == null) {
            String inpatientStr = req.getParameter("inpatientId");
            if (inpatientNullCheck(req, inpatients, inpatientStr)) {
                return "inpatient";
            }
            int inpatientNum = Integer.parseInt(inpatientStr);
            if (inpatientInputCheck(req, inpatients, inpatientNum)) {
                return "inpatient";
            }
            inpatient = medDoctorService.getInpatientById(inpatients.get(inpatientNum - 1).getInpatientId());
            req.getSession().setAttribute("inpatient", inpatient);
        }

        if (req.getParameter("cardInfo") != null) {
            req.getSession().setAttribute("card", medDoctorService.getCardInfo(inpatient.getInpatientId()));
            return "inpatient";
        }

        String option = req.getParameter("option");
        switch (option) {
            case "diagnose":
                return "redirect:/personalDoctor/diagnose";
            case "treatmentCourse":
                return "redirect:/personalDoctor/treatmentCourse";
            case "updateStatus":
                return "redirect:/personalDoctor/inpatientStatus";
            default:
                if (medDoctorService.dischargeInpatient(inpatient)) {
                    log.info("Doctor {} Discharge Patient {} at {}", authUser.getLogin(), inpatient.getInpatientId(), LocalDateTime.now());
                    req.getSession().removeAttribute("inpatient");
                    req.getSession().removeAttribute("card");
                    return "redirect:/personalDoctor/dischargedInpatients";
                } else {
                    req.setAttribute("error", "You Can't Discharge Inpatient If He's Feeling Bad Or Not Dead");
                    req.setAttribute("inpatients", inpatients);
                    return "inpatient";
                }
        }
    }

    @GetMapping("/diagnose")
    public String inpatientDiagnose(HttpServletRequest req, UsernamePasswordAuthenticationToken authentication) {
        if (req.getSession().getAttribute("inpatient") == null) {
            AuthUser authUser = (AuthUser) authentication.getPrincipal();
            final Long doctorId = medDoctorService.getDoctorByUserId(authUser.getUserId())
                    .getDoctorId();
            final List<Inpatient> inpatientsByDoctorId = medDoctorService
                    .getInpatientsByDoctorId(doctorId);
            req.setAttribute("inpatients", inpatientsByDoctorId);
            req.setAttribute("error", "Please Select Inpatient First");
            return "inpatient";
        } else {
            return "diagnose";
        }
    }

    @PostMapping("/diagnose")
    public String setDiagnose(HttpServletRequest req, UsernamePasswordAuthenticationToken authentication) {
        String diagnose = req.getParameter("diagnose");
        if (diagnose.equals("")) {
            return "diagnose";
        }
        boolean prescribeTreatmentCourse = Boolean.parseBoolean(req.getParameter("prescribeTreatmentCourse"));
        Inpatient inpatient = (Inpatient) req.getSession().getAttribute("inpatient");

        Long inpatientId = inpatient.getInpatientId();
        medDoctorService.updateDiagnose(inpatientId, diagnose);
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        log.info("Doctor {} Set new diagnose to {}", authUser.getLogin(), inpatientId);
        req.getSession().setAttribute("inpatient", medDoctorService.getInpatientById(inpatientId));
        // to update diagnose that was set
        if (prescribeTreatmentCourse) {
            return "redirect:/personalDoctor/treatmentCourse";
        } else {
            return "redirect:/personalDoctor/inpatients";
        }
    }

    @GetMapping("/treatmentCourse")
    public String treatmentCourse(HttpServletRequest req, UsernamePasswordAuthenticationToken authentication) {
        Inpatient inpatient = (Inpatient) req.getSession().getAttribute("inpatient");
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        final Long doctorId = medDoctorService.getDoctorByUserId(authUser.getUserId())
                .getDoctorId();
        final List<Inpatient> inpatientsByDoctorId = medDoctorService
                .getInpatientsByDoctorId(doctorId);
        if (inpatient == null) {
            req.setAttribute("inpatients", inpatientsByDoctorId);
            req.setAttribute("error", "Please Select Inpatient First");
            return "inpatient";
        } else if (inpatient.getDiagnose() == null) {
            req.setAttribute("inpatients", inpatientsByDoctorId);
            req.setAttribute("error", "You Can't Prescribe Treatment Course Without Diagnose");
            return "inpatient";
        } else {
            req.setAttribute("treatmentCourse", medDoctorService.getTreatmentCourses());
            return "treatmentCourse";
        }
    }

    @PostMapping("/treatmentCourse")
    public String setTreatmentCourse(HttpServletRequest req, UsernamePasswordAuthenticationToken authentication) {
        String treatmentCourse = req.getParameter("treatmentCourseId");
        List<TreatmentCourse> courses = medDoctorService.getTreatmentCourses();

        if (treatmentCourse.equals("")) {
            req.setAttribute("error", "field is empty");
            req.setAttribute("treatmentCourse", courses);
            return "treatmentCourse";
        } else {
            int treatmentCourseNum = Integer.parseInt(treatmentCourse);
            if (treatmentCourseNum > courses.size() || treatmentCourseNum < 1) {
                req.setAttribute("error", "incorrect input");
                req.setAttribute("treatmentCourse", courses);
                return "treatmentCourse";
            }
            Inpatient inpatient = (Inpatient) req.getSession().getAttribute("inpatient");
            Long treatmentCourseId = courses.get(treatmentCourseNum - 1).getTreatmentCourseId();
            final Long inpatientId = inpatient.getInpatientId();
            medDoctorService.prescribeTreatmentCourse(inpatientId, treatmentCourseId);
            AuthUser authUser = (AuthUser) authentication.getPrincipal();
            log.info("Doctor {} Prescribe New Treatment Course {} to {}", authUser.getLogin(), treatmentCourseId, inpatientId);
            req.getSession().setAttribute("inpatient", medDoctorService.getInpatientById(inpatientId));
            return "redirect:/personalDoctor/inpatients";
        }
    }

    @GetMapping("/createTreatmentCourse")
    public String getCreateTreatmentCourse(HttpServletRequest req) {
        req.setAttribute("medicine", medDoctorService.getMedicine());
        return "createTreatmentCourse";
    }

    @PostMapping("/createTreatmentCourse")
    public String createTreatmentCourse(HttpServletRequest req, CreateTreatmentCourseRq rq, UsernamePasswordAuthenticationToken authentication) {
        List<Medicine> medicines = medDoctorService.getMedicine();

        String medicineName = rq.getMedicineName();
        String drugDose = rq.getDrugDose();
        String timesPerDay = rq.getTimesPerDay();
        String durationInDays = rq.getDurationInDays();
        String receptionDesc = rq.getReceptionDesc();
        boolean overdose = rq.isOverdose();

        if (createTreatmentCourseNullCheck(req, medicines, drugDose, timesPerDay, durationInDays, receptionDesc)) {
            return "createTreatmentCourse";
        }
        final Medicine medicineByName = medDoctorService.getMedicineByName(medicineName);
        if (createTreatmentCourseCriticalDoseCheck(req, medicines, drugDose, overdose, medicineByName)) {
            return "createTreatmentCourse";
        }
        Long treatmentCourseId = medDoctorService.createTreatmentCourse(new TreatmentCourse(
                medicineByName.getMedicineId(),
                medicineByName.getMedicineName(),
                medicineByName.getNormalDose(),
                medicineByName.getCriticalDose(),
                medicineByName.getPackageAmount(),
                medicineByName.getPrice(),
                null,
                Double.parseDouble(drugDose),
                receptionDesc,
                Integer.parseInt(timesPerDay),
                Integer.parseInt(durationInDays)
        ));
        if (treatmentCourseId != null) {
            AuthUser authUser = (AuthUser) authentication.getPrincipal();
            log.info("Doctor {} made new Treatment Course {}", authUser.getLogin(), treatmentCourseId);
            req.setAttribute("treatmentCourseCreation", "Treatment Course Created");
        } else {
            req.setAttribute("treatmentCourseCreation", "Treatment Course Was Not Created Because Database Has Encountered An Unexpected Error");
        }
        req.setAttribute("treatmentCourse", medDoctorService.getTreatmentCourses());
        if (req.getSession().getAttribute("inpatient") == null) {
            return "treatmentCourses";
        } else {
            return "treatmentCourse";
        }
    }

    @GetMapping("/inpatientStatus")
    public String inpatientStatus(HttpServletRequest req, UsernamePasswordAuthenticationToken authentication){
        if (req.getSession().getAttribute("inpatient") == null){
            req.setAttribute("error", "Please Select Inpatient First");
            AuthUser authUser = (AuthUser) authentication.getPrincipal();
            final Long doctorId = medDoctorService.getDoctorByUserId(authUser.getUserId())
                    .getDoctorId();
            final List<Inpatient> inpatientsByDoctorId = medDoctorService
                    .getInpatientsByDoctorId(
                            doctorId);
            req.setAttribute("inpatients", inpatientsByDoctorId);
            return "inpatient";
        } else {
            return "inpatientStatus";
        }
    }

    @PostMapping("/inpatientStatus")
    public String setInpatientStatus(HttpServletRequest req, UsernamePasswordAuthenticationToken authentication){
        Status status = Status.valueOf(req.getParameter("status"));
        Inpatient inpatient = (Inpatient) req.getSession().getAttribute("inpatient");
        if ((status.equals(Status.GOOD) || status.equals(Status.CURED)) && inpatient.getTreatmentCourse() == null){
            req.setAttribute("error", "It Can't Be That Inpatient Got Better Without Any Treatment");
            return "inpatientStatus";
        } else {
            final Long inpatientId = inpatient.getInpatientId();
            medDoctorService.updateStatus(inpatientId, status);
            AuthUser authUser = (AuthUser) authentication.getPrincipal();
            req.getSession().setAttribute("inpatient", medDoctorService.getInpatientById(inpatientId));
            log.info("Doctor {} Update Status {} to {}", authUser.getLogin(), status, inpatientId);
            return "redirect:/personalDoctor/inpatients";
        }
    }

    private boolean createTreatmentCourseCriticalDoseCheck(HttpServletRequest req, List<Medicine> medicines, String drugDose, boolean overdose, Medicine medicineByName) {
        if (medicineByName.getCriticalDose() <= Double.parseDouble(drugDose) && !overdose) {
            req.setAttribute("overdose", "Are you sure you want to overdose your patient?");
            req.setAttribute("medicineDose", medicineByName.getCriticalDose());
            req.setAttribute("medicine", medicines);
            return true;
        }
        return false;
    }


    private boolean createTreatmentCourseNullCheck(HttpServletRequest req, List<Medicine> medicines, String drugDose, String timesPerDay, String durationInDays, String receptionDesc) {
        if (drugDose.equals("") || timesPerDay.equals("") || durationInDays.equals("") || receptionDesc.equals("")) {
            req.setAttribute("error", "Some of the fields are empty");
            req.setAttribute("medicine", medicines);
            return true;
        }
        return false;
    }


    private boolean inpatientInputCheck(HttpServletRequest req, List<Inpatient> inpatients, int inpatientNum) {
        if (inpatientNum > inpatients.size() || inpatientNum < 1) {
            req.setAttribute("error", "incorrect input");
            req.setAttribute("inpatients", inpatients);
            return true;
        }
        return false;
    }

    private boolean inpatientNullCheck(HttpServletRequest req, List<Inpatient> inpatients, String inpatientStr) {
        if (inpatientStr.equals("")) {
            req.setAttribute("error", "field is empty");
            req.setAttribute("inpatients", inpatients);
            return true;
        }
        return false;
    }

    private boolean selectAnotherInpatient(HttpServletRequest req) {
        if (req.getParameter("selectAnotherPatient") != null) {
            req.getSession().removeAttribute("inpatient");
            req.getSession().removeAttribute("card");
            return true;
        }
        return false;
    }
}