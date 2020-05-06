package com.github.KostyaTr.hospital.web.servlet.appointment;

import com.github.KostyaTr.hospital.service.UserService;
import com.github.KostyaTr.hospital.service.impl.DefaultUserService;
import com.github.KostyaTr.hospital.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/chooseMedicalServices")
public class ChooseMedicalServiceServlet extends HttpServlet {
    private UserService userService = DefaultUserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        if (req.getSession().getAttribute("patientId") != null){
            try {
                resp.sendRedirect(req.getContextPath() +"/appointment");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        req.setAttribute("services", userService.getMedicalServices());
        WebUtils.forwardToJsp("chooseMedicalService", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String service = req.getParameter("serviceId");
        if (service.equals("")){
            req.setAttribute("error", "field is empty");
            req.setAttribute("services", userService.getMedicalServices());
            WebUtils.forwardToJsp("chooseMedicalService", req, resp);
        }
        Long serviceId = Long.valueOf(service);
        if (serviceId > userService.getMedicalServices().size() || serviceId < 1){
            req.setAttribute("error", "incorrect input");
            req.setAttribute("services", userService.getMedicalServices());
            WebUtils.forwardToJsp("chooseMedicalService", req, resp);
        }
        req.getSession().setAttribute("medicalServiceId", serviceId);
        try {
            resp.sendRedirect(req.getContextPath() +"/chooseDoctor");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
