package org.classicstore.controller.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.classicstore.dao.admin.AdminDao;

import java.io.IOException;

@WebServlet(name = "AdminLogoutServlet", value = "/admin/logout")
public class AdminLogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Get existing session if it exists
        if (session != null) {
            AdminDao adminService = new AdminDao();
            adminService.adminLogout(session);
            System.out.println("Session invalidated, logging out.");
        }
        response.sendRedirect(request.getContextPath() + "/admin/login");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
