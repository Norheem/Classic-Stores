package org.classicstore.controller.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.classicstore.model.Admin;
import org.classicstore.dao.admin.AdminDao;

import java.io.IOException;

@WebServlet(name = "AdminSignupServlet", value = "/admin/signup")
public class AdminSignupServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("An admin signup request");
        getServletContext().getRequestDispatcher("/views/admin/admin.signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            request.setAttribute("error", "Please fill all the required fields");
            getServletContext().getRequestDispatcher("/views/admin/admin.signup.jsp").forward(request, response);
            return;
        } else {

            if (!password.equals(confirmPassword)) {
                request.setAttribute("error", "Passwords do not match");
                getServletContext().getRequestDispatcher("/views/admin/admin.signup.jsp").forward(request, response);
                return;
            } else if (password.length() < 6) {
                request.setAttribute("error", "Password must be at least 6 characters");
                getServletContext().getRequestDispatcher("/views/admin/admin.signup.jsp").forward(request, response);
                return;
            }
        }

        Admin admin = new Admin();
        admin.setAdminFirstName(firstName);
        admin.setAdminLastName(lastName);
        admin.setAdminEmail(email);
        admin.setAdminPassword(password);

        AdminDao adminService = new AdminDao();
        String register = adminService.adminRegister(admin);

        if ("Admin registered successfully.".equals(register)) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
        } else {
            request.setAttribute("error", register);
            getServletContext().getRequestDispatcher("/views/admin/admin.signup.jsp").forward(request, response);
        }
    }
}
