package org.classicstore.controller.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.classicstore.dao.admin.AdminDao;
import org.classicstore.model.Admin;

import java.io.IOException;

@WebServlet(name = "AdminServletLogin", value = "/admin/login")
public class AdminLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        Admin admin = (Admin) session.getAttribute("admin");

        if (admin != null) {
            System.out.println("AdminLoginServlet Checking.....");
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } else {
            request.getRequestDispatcher("/views/admin/admin.login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String adminEmail = request.getParameter("email");
        String adminPassword = request.getParameter("password");

        AdminDao adminDao = new AdminDao();
        String loginResult = adminDao.adminLogin(adminEmail, adminPassword);

        if ("Login Successful".equals(loginResult)) {
            HttpSession session = request.getSession();

            Admin admin = new Admin();
            admin.setAdminEmail(adminEmail);
            session.setAttribute("admin", admin);

            session.setAttribute("adminEmail", adminEmail);
            System.out.println("AdminLoginServlet Checking..... " + adminEmail + " " + adminPassword + " " + session.getId() + " " + session.getAttribute("admin"));
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } else {
            request.setAttribute("errorMessage", loginResult);
            request.getRequestDispatcher("/views/admin/admin.login.jsp").forward(request, response);
        }

    }
}