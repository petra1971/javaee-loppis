package se.iths.security;

import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ServletSecurity(@HttpConstraint(rolesAllowed = {"ADMIN"}))
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    public AdminServlet() {
        super();
    }

    @Inject
    SecurityContext securityContext;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.getWriter()
                .append("Welcome to the admin console, ")
                .append(securityContext.getCallerPrincipal().getName());

    }

}
