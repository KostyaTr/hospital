package com.github.KostyaTr.hospital.web.controller;

import com.github.KostyaTr.hospital.service.MedDoctorService;
import com.github.KostyaTr.hospital.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class InfoController {
    private final UserService userService;
    private final MedDoctorService medDoctorService;

    public InfoController(UserService userService, MedDoctorService medDoctorService) {
        this.userService = userService;
        this.medDoctorService = medDoctorService;
    }

    private int page = 1;

    @GetMapping("/doctors")
    public String doctors(HttpServletRequest req){
        req.setAttribute("doctors", userService.getDoctors());
        return "doctors";
    }

    @GetMapping("/medicalServices")
    public String medicalServices(HttpServletRequest req){
        req.setAttribute("services", userService.getMedicalServices());
        return "medicalServices";
    }

    @GetMapping("/personalDoctor/medicine")
    public String medicine(HttpServletRequest req){
        req.setAttribute("medicine", medDoctorService.getMedicine());
        return "medicine";
    }

    @GetMapping("/personalDoctor/treatmentCourses")
    public String treatmentCourses(HttpServletRequest req){
        req.setAttribute("treatmentCourse", medDoctorService.getTreatmentCourses());
        return "treatmentCourses";
    }

    @GetMapping("/personalDoctor/dischargedInpatients")
    public String dischargedPatients(HttpServletRequest req){
        req.setAttribute("pageCount", Math.ceil(medDoctorService.getDischargedPatientsCount()/10));
        req.setAttribute("page", page);
        req.setAttribute("dischargeInpatients", medDoctorService.getDischargedPatients(page));
        return "dischargedInpatients";
    }

    @PostMapping("/personalDoctor/dischargedInpatients")
    public String dischargedPatientsPager(HttpServletRequest req){
        String previousPage = req.getParameter("previousPage");
        String aGoto = req.getParameter("goto");
        String nextPage = req.getParameter("nextPage");
        if (previousPage != null){
            page--;
        }
        else if (aGoto != null){
            page = Integer.parseInt(req.getParameter("goToPage"));
        }
        else if (nextPage != null){
            page++;
        }
        return "redirect:/personalDoctor/dischargedInpatients";
    }
}
