package web.servlet;

import dao.MedDoctorDao;
import dao.impl.DefaultMedDoctorDao;
import model.AuthUser;
import model.MedDoctor;
import service.MedDoctorService;
import service.impl.DefaultMedDoctorService;
import web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/personalDoctor")
public class PersonalDoctorAccount extends HttpServlet {
    private MedDoctorService medDoctorService = DefaultMedDoctorService.getInstance();
    private MedDoctorDao medDoctorDao = DefaultMedDoctorDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
        MedDoctor medDoctor = medDoctorDao.getDoctorByLogin(authUser.getLogin());

        req.setAttribute("patients", medDoctorService.getPatientsByDoctor(medDoctor.getFirstName()
                + " "+ medDoctor.getLastName()));

        req.setAttribute("name", medDoctor.getFirstName() + " "+ medDoctor.getLastName());
        req.setAttribute("allPatients",medDoctorService.getPatients());
        req.setAttribute("role", "Doctor");
        WebUtils.forwardToJsp("personalAccount", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String patientName = req.getParameter("patientName");

        medDoctorService.curePatient(patientName);

        try {
            resp.sendRedirect(req.getContextPath() +"/personalDoctor");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
