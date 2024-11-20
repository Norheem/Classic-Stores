package org.classicstore.controller.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.classicstore.dao.product.ProductDao;
import org.classicstore.model.Product;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminDashboardServlet", value = "/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminEmail") == null) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }

        try {
            String adminEmail = (String) session.getAttribute("adminEmail");
            System.out.println("Admin Email: " + adminEmail);

            ProductDao productDao = new ProductDao();
            List<Product> productList = productDao.displayAllProducts();

            request.setAttribute("productList", productList);
            System.out.println("Number of products fetched: " + productList.size());

            System.out.println("Forwarding to /views/admin/admin.dashboard.jsp");
            request.getRequestDispatcher("/views/admin/admin.dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error loading dashboard: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String adminEmail = (String) session.getAttribute("adminEmail");

    }

}
