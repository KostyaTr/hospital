package web.servlet;

import model.AuthUser;
import service.impl.DefaultAuthorizationService;
import web.WebUtils;
import service.AuthorizationService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private AuthorizationService securityService = DefaultAuthorizationService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Object authUser = req.getSession().getAttribute("authUser");
        if (authUser == null){
            WebUtils.forwardToJsp("login", resp, req);
        }
        try {
            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest rq, HttpServletResponse rs) {
        String login = rq.getParameter("login");
        String password = rq.getParameter("password");
        AuthUser user = securityService.login(login, password);
        if (user == null) {
            rq.setAttribute("error", "login or password invalid");
            WebUtils.forwardToJsp("login", rs, rq);
        }
        rq.getSession().setAttribute("authUser", user);
        try {
            rs.sendRedirect(rq.getContextPath() +"/student");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
