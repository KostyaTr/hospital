package web.servlet;

import model.Patient;
import service.UserService;
import service.impl.DefaultUserService;
import web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/guestPage")
public class GuestPageServlet extends HttpServlet {
    private UserService userService = DefaultUserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("doctors", userService.getDoctors());
        req.setAttribute("role", "Guest");
        WebUtils.forwardToJsp("personalAccount", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phoneNumber = req.getParameter("phone");
        String email = req.getParameter("email");
        String appointedDoctor = req.getParameter("doctorName");
        String visitingTime = req.getParameter("visitingTime");
        String login = firstName + "_" + lastName;

        userService.makeAppointment(new Patient(login, firstName, lastName, phoneNumber,
                email, appointedDoctor, visitingTime));

        try {
            resp.sendRedirect(req.getContextPath() +"/guestPage");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
