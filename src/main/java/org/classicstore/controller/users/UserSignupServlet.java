package org.classicstore.controller.users;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.classicstore.dao.user.UserDao;
import org.classicstore.model.User;

import java.io.IOException;

@WebServlet(name = "UserSignupServlet", value = "/signup")
public class UserSignupServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("A GET request from SignupServlet");
        getServletContext().getRequestDispatcher("/views/user/signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Request received");

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            request.setAttribute("error", "Please fill all the required fields");
            getServletContext().getRequestDispatcher("/views/user/signup.jsp").forward(request, response);
            return;
        } else {

            if (!password.equals(confirmPassword)) {
                request.setAttribute("error", "Passwords do not match");
                getServletContext().getRequestDispatcher("/views/user/signup.jsp").forward(request, response);
                return;
            } else if (password.length() < 6) {
                request.setAttribute("error", "Password must be at least 6 characters");
                getServletContext().getRequestDispatcher("/views/user/signup.jsp").forward(request, response);
                return;
            }
        }
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);

        UserDao userDao = new UserDao();
        String register = userDao.userRegister(user);

        if ("User registered successfully".equals(register)) {
            response.sendRedirect(request.getContextPath() + "/views/user/login.jsp");
        } else {
            request.setAttribute("error", register);
            getServletContext().getRequestDispatcher("/views/user/signup.jsp").forward(request, response);
        }
    }
}
