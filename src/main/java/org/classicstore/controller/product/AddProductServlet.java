package org.classicstore.controller.product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.classicstore.dao.product.ProductDao;
import org.classicstore.model.Admin;
import org.classicstore.model.Product;

import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "ProductServlet", value = "/admin/addproduct")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)

public class AddProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/add.product.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error forwarding to add.product.jsp", e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Object sessionAdmin = session != null ? session.getAttribute("admin") : null;

        if (sessionAdmin == null  || !(sessionAdmin instanceof Admin)) {
            System.out.println("Session admin attribute is either null or not of type Admin.");
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }

        Admin admin = (Admin) sessionAdmin;

        request.setCharacterEncoding("UTF-8");


        String productName = request.getParameter("productName");
        String productPriceStr = request.getParameter("productPrice");
        String productDescription = request.getParameter("productDescription");
        String productCategory = request.getParameter("productCategory");

        Part productImagePart = request.getPart("productImage");

        if (productName == null || productName.isEmpty() ||
                productPriceStr == null || productPriceStr.isEmpty() ||
                productDescription == null || productDescription.isEmpty() ||
                productCategory == null || productCategory.isEmpty() ||
                productImagePart == null || productImagePart.getSize() == 0) {

            request.setAttribute("errorMessage", "All fields are required.");
            request.getRequestDispatcher("/views/admin/addproduct.jsp").forward(request, response);
            return;
        }

        try {

            double productPrice = Double.parseDouble(productPriceStr);


            InputStream inputStream = productImagePart.getInputStream();
            byte[] productImage = inputStream.readAllBytes();

            Product product = new Product();
            product.setProductName(productName);
            product.setProductPrice(productPrice);
            product.setProductDescription(productDescription);
            product.setProductCategory(productCategory);
            product.setProductImage(productImage);

            ProductDao productDao = new ProductDao();
            boolean isProductAdded = productDao.addProduct(product, admin);

            if (isProductAdded) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                request.setAttribute("errorMessage", "Failed to add product. Try again.");
                request.getRequestDispatcher("/views/admin/addproduct.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error adding product: " + e.getMessage());
        }

    }
}




