package org.classicstore.controller.users;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.classicstore.dao.user.UserDao;

import java.io.IOException;

@WebServlet(name = "UserLogoutServlet", value = "/logout")
public class UserLogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            UserDao userDao = new UserDao();
            userDao.userLogout(session);
            System.out.println("Session invalidated, user logging out");
        }
        response.sendRedirect( request.getContextPath() + "/login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
