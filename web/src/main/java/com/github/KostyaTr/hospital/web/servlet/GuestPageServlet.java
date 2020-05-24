package com.github.KostyaTr.hospital.web.servlet;

import com.github.KostyaTr.hospital.model.User;
import com.github.KostyaTr.hospital.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/guestPage")
public class GuestPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Object authUser = req.getSession().getAttribute("authUser");
        if (authUser != null){
            try {
                resp.sendRedirect(req.getContextPath() +"/" + WebUtils.personalAccount(req, resp));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(req.getSession().getAttribute("doctorId") == null){
            try {
                resp.sendRedirect("/appointment");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            WebUtils.forwardToJsp("guest's", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phoneNumber = req.getParameter("phone");
        String email = req.getParameter("email");

        if (firstName.equals("") || lastName.equals("") || phoneNumber.equals("") ||
                email.equals("")){
            req.setAttribute("incorrectInput", "some of the fields are empty");
            WebUtils.forwardToJsp("guest's", req, resp);
        } else {
            req.getSession().setAttribute("user", new User(null, firstName, lastName, phoneNumber, email));
            try {
                resp.sendRedirect(req.getContextPath() + "/appointment");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
