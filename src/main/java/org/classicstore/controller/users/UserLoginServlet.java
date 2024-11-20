package org.classicstore.controller.users;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.classicstore.dao.user.UserDao;
import org.classicstore.model.User;

import java.io.IOException;

@WebServlet(name = "UserLoginServlet", value = "/login")
public class UserLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       HttpSession session = request.getSession();
       User user = (User) session.getAttribute("user");

       if (user != null) {
           System.out.println("UserLoginServlet checking......");
           response.sendRedirect(request.getContextPath() + "/home");
       } else {
          request.getRequestDispatcher("/views/user/login.jsp").forward(request, response);
       }

        System.out.println("Session ID: " + session.getId());
        System.out.println("User in session: " + session.getAttribute("user"));
        System.out.println("Email in session: " + session.getAttribute("email"));

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userEmail = request.getParameter("email");
        String userPassword = request.getParameter("password");

        UserDao userDao = new UserDao();
        String userLoginResult = userDao.userLogin(userEmail, userPassword);

        if ("User logged in successfully".equals(userLoginResult)) {
            HttpSession session = request.getSession();
            User user = new User();
            user.setEmail(userEmail);
            session.setAttribute("user", user);

            session.setAttribute("email", userEmail);
            System.out.println("UserLoginServlet Checking...... " +userEmail + " " + userPassword + " " + session.getId() + " " + session.getAttribute("user"));
            response.sendRedirect(request.getContextPath() + "/home");

            System.out.println("Session ID: " + session.getId());
            System.out.println("User in session: " + session.getAttribute("user"));
            System.out.println("Email in session: " + session.getAttribute("email"));

        } else {
            request.setAttribute("errorMessage", userLoginResult);
            request.getRequestDispatcher("/views/user/login.jsp").forward(request, response);
        }

    }

}
