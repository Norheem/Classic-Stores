package org.classicstore.controller.users;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.classicstore.dao.product.ProductDao;
import org.classicstore.model.Product;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserDashboardServlet", value = "/home")
public class UserHomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
          String email = (String) session.getAttribute("email");
            System.out.println("User email: " + email);

            ProductDao productDao = new ProductDao();
            List<Product> productList = productDao.displayAllProducts();
            request.setAttribute("productList", productList);
            System.out.println("Number of products fetched: " + productList.size());

            System.out.println("Forwarding to /views/user/home.jsp");
            request.getRequestDispatcher("/views/user/home.jsp").forward(request, response);

            System.out.println("Session ID: " + session.getId());
            System.out.println("User in session: " + session.getAttribute("user"));
            System.out.println("Email in session: " + session.getAttribute("email"));

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error loading home " +e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
