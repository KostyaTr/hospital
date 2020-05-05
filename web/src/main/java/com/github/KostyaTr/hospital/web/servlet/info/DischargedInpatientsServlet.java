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
   private int page = 1;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("pageCount", Math.ceil(dischargedPatientDao.getDischargedPatientsCount()/10));
        req.setAttribute("page", page);
        req.setAttribute("dischargeInpatients", dischargedPatientDao.getDischargedPatients(page));
        WebUtils.forwardToJsp("dischargedInpatients", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("pageCount", Math.ceil(dischargedPatientDao.getDischargedPatientsCount()/10));
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
        req.setAttribute("page", page);
        req.setAttribute("dischargeInpatients", dischargedPatientDao.getDischargedPatients(page));
        WebUtils.forwardToJsp("dischargedInpatients", req, resp);
    }
}