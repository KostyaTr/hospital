package com.github.KostyaTr.hospital.web.servlet.inpatient;

import com.github.KostyaTr.hospital.dao.MedicineDao;
import com.github.KostyaTr.hospital.dao.TreatmentCourseDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultMedicineDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultTreatmentCourseDao;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.Medicine;
import com.github.KostyaTr.hospital.model.TreatmentCourse;
import com.github.KostyaTr.hospital.service.MedDoctorService;
import com.github.KostyaTr.hospital.service.impl.DefaultMedDoctorService;
import com.github.KostyaTr.hospital.web.WebUtils;
import com.github.KostyaTr.hospital.web.servlet.appointment.AppointmentServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/personalDoctor/createTreatmentCourse")
public class CreateTreatmentCourseServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(CreateTreatmentCourseServlet.class);
    private MedDoctorService medDoctorService = DefaultMedDoctorService.getInstance();
    private MedicineDao medicineDao = DefaultMedicineDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("medicine", medDoctorService.getMedicine());
        WebUtils.forwardToJsp("createTreatmentCourse", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        List<Medicine> medicines = medDoctorService.getMedicine();

        String medicineName = req.getParameter("medicineName");
        String drugDose = req.getParameter("drugDose");
        String timesPerDay = req.getParameter("timesPerDay");
        String durationInDays = req.getParameter("durationInDays");
        String receptionDesc = req.getParameter("receptionDescription");
        boolean overdose = Boolean.parseBoolean(req.getParameter("overdose"));

        if (drugDose.equals("") || timesPerDay.equals("") || durationInDays.equals("") || receptionDesc.equals("")){
            req.setAttribute("error", "Some of the fields are empty");
            req.setAttribute("medicine", medicines);
            WebUtils.forwardToJsp("createTreatmentCourse", req, resp);
        } else {
            Medicine medicine = medicineDao.getMedicineByName(medicineName);
            if (medicine.getCriticalDose() <= Double.parseDouble(drugDose) && !overdose){
                req.setAttribute("overdose", "Are you sure you want to overdose your patient?");
                req.setAttribute("medicineDose", medicine.getCriticalDose());
                req.setAttribute("medicine", medicines);
                WebUtils.forwardToJsp("createTreatmentCourse", req, resp);
            } else {
                Long treatmentCourseId = medDoctorService.createTreatmentCourse(new TreatmentCourse(
                        null,
                        medicine.getMedicineId(),
                        Double.parseDouble(drugDose),
                        receptionDesc,
                        Integer.parseInt(timesPerDay),
                        Integer.parseInt(durationInDays)
                ));
                if (treatmentCourseId != null) {
                    AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
                    log.info("Doctor {} made new Treatment Course {}", authUser.getLogin(), treatmentCourseId);
                    req.setAttribute("treatmentCourseCreation", "Treatment Course Created");
                } else {
                    req.setAttribute("treatmentCourseCreation", "Treatment Course Was Not Created Because Database Has Encountered An Unexpected Error");
                }
                req.setAttribute("treatmentCourse", medDoctorService.getTreatmentCourses());
                if (req.getSession().getAttribute("inpatient") == null){
                    WebUtils.forwardToJsp("treatmentCourses", req, resp);
                } else {
                    WebUtils.forwardToJsp("treatmentCourse", req, resp);
                }
            }
        }
    }
}
