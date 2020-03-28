package web.servlet.authorization;

import model.AuthUser;
import model.Role;
import model.User;
import service.RegistrationService;
import service.impl.DefaultRegistrationService;
import web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {
    private RegistrationService registrationService = DefaultRegistrationService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        WebUtils.forwardToJsp("signUp", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {
        String login = req.getParameter("login");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phoneNumber = req.getParameter("phone");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String passwordRepeat = req.getParameter("passwordRepeat");

        if (registrationService.loginCheck(login)){
            req.setAttribute("loginError", "login already taken");
            WebUtils.forwardToJsp("signUp", req, resp);
        }
        if (registrationService.passwordCheck(password, passwordRepeat)){
            req.setAttribute("passwordError", "passwords don't match");
            WebUtils.forwardToJsp("signUp", req, resp);
        }

        User user = new User(login, firstName, lastName, phoneNumber, email);
        AuthUser authUser = new AuthUser(login, password, Role.AuthorizedUser);

        registrationService.saveAuthUser(authUser);
        registrationService.saveUser(user);

        try {
            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
