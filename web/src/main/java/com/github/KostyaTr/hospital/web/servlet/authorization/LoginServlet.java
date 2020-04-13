package com.github.KostyaTr.hospital.web.servlet.authorization;

import com.github.KostyaTr.hospital.web.WebUtils;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.service.AuthorizationService;
import com.github.KostyaTr.hospital.service.impl.DefaultAuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);
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
        } else {
            rq.getSession().setAttribute("authUser", user);
            rq.getSession().removeAttribute("user");
            log.info("user {} logged", user.getLogin());
            try {
                rs.sendRedirect(rq.getContextPath() +"/" + WebUtils.personalAccount(rq, rs));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
