package com.github.KostyaTr.hospital.web.servlet.authorization;

import com.github.KostyaTr.hospital.web.WebUtils;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.service.AuthorizationService;
import com.github.KostyaTr.hospital.service.impl.DefaultAuthorizationService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private AuthorizationService authorizationService = DefaultAuthorizationService.getInstance();

    @Override
    protected void doGet(HttpServletRequest rq, HttpServletResponse rs) {
        Object authUser = rq.getSession().getAttribute("authUser");
        if (authUser == null) {
            WebUtils.forwardToJsp("login", rq, rs);
        }
        else {
            try {
                rs.sendRedirect(rq.getContextPath() +"/" + WebUtils.personalAccount(rq, rs));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest rq, HttpServletResponse rs) {
        String login = rq.getParameter("login");
        String password = rq.getParameter("password");
        AuthUser user = authorizationService.login(login, password);
        if (user == null) {
            rq.setAttribute("error", "login or password invalid");
            WebUtils.forwardToJsp("login", rq, rs);
        }
        rq.getSession().setAttribute("authUser", user);
        rq.getSession().removeAttribute("user");
        try {
            rs.sendRedirect(rq.getContextPath() +"/" + WebUtils.personalAccount(rq, rs));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
