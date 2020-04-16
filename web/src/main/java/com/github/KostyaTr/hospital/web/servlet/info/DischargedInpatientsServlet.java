package com.github.KostyaTr.hospital.web.servlet.info;

import com.github.KostyaTr.hospital.dao.DischargedPatientDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultDischargedPatientDao;
import com.github.KostyaTr.hospital.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/personalDoctor/dischargedInpatients")
public class DischargedInpatientsServlet extends HttpServlet {
   private DischargedPatientDao dischargedPatientDao = DefaultDischargedPatientDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("dischargeInpatients", dischargedPatientDao.getDischargedPatients());
        WebUtils.forwardToJsp("dischargedInpatients", req, resp);
    }
}