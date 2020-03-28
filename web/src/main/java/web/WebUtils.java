package web;

import model.AuthUser;
import model.Patient;
import model.Role;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class WebUtils {
    private WebUtils() {
    }
    private static final Logger log = LoggerFactory.getLogger(WebUtils.class);

    public static void forwardToJsp(String jspName, HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/" + jspName + ".jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    public static String personalAccount(HttpServletRequest rq, HttpServletResponse rs) {
        AuthUser authUser = (AuthUser) rq.getSession().getAttribute("authUser");
        if(authUser.getRole().equals(Role.AuthorizedUser)){
            return "personalUser";
        } if (authUser.getRole().equals(Role.Doctor)){
            return "personalDoctor";
        } else {
            return "guestPage";
        }
    }

    public static Patient userToPatient(User user, String appointedDoctor, String visitingTime){
        return new Patient(user.getLogin(), user.getFirstName(), user.getLastName(),
                user.getEmail(), user.getEmail(), appointedDoctor, visitingTime);
    }
}