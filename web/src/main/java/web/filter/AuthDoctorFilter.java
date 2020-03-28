package web.filter;

import model.AuthUser;
import model.Role;
import web.WebUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/personalDoctor")
public class AuthDoctorFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest rq = (HttpServletRequest) servletRequest;
        AuthUser authUser = (AuthUser) rq.getSession().getAttribute("authUser");
        if (authUser == null || authUser.getRole() != Role.Doctor) {
            WebUtils.forwardToJsp("login", rq, ((HttpServletResponse) servletResponse));
        }
        filterChain.doFilter(rq, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
