package org.classicstore.controller.product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.classicstore.dao.product.ProductDao;
import org.classicstore.model.Admin;
import org.classicstore.model.Product;

import java.io.IOException;

@WebServlet(name = "DeleteProductServlet", value = "/admin/deleteproduct")
public class DeleteProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String productIdStr = request.getParameter("productId");

        if (productIdStr == null || productIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }
        try {
            int productId = Integer.parseInt(productIdStr);

            Admin admin = (Admin) request.getSession().getAttribute("admin");
            if (admin == null) {
                throw new ServletException("Unauthorized access. Admin not found in session.");
            }

            ProductDao productDao = new ProductDao();
            Product product = productDao.getProductById(productId);

            if (product != null) {
                productDao.deleteProduct(product, admin);
                request.setAttribute("message", "Product deleted successfully!");
            } else {
                request.setAttribute("error", "Product not found.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid product ID.");
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred while deleting the product.");
        }
        response.sendRedirect(request.getContextPath() + "/admin/dashboard");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
