package org.classicstore.controller.product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.classicstore.dao.product.ProductDao;
import org.classicstore.model.Admin;
import org.classicstore.model.Product;

import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "EditProductServlet", value = "/admin/editproduct")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class EditProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("EditProductServlet invoked");
        String productIdStr = request.getParameter("productId");
        if (productIdStr == null || productIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        try {
            int productId = Integer.parseInt(productIdStr);

            ProductDao productDao = new ProductDao();
            Product product = productDao.getProductById(productId);
            if (product != null) {
                request.setAttribute("product", product);

                request.getRequestDispatcher("/views/admin/editproduct.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String productIdStr = request.getParameter("productID");
        String productName = request.getParameter("productName");
        String productPriceStr = request.getParameter("productPrice");
        String productDescription = request.getParameter("productDescription");
        String productCategory = request.getParameter("productCategory");
        Part productImagePart = request.getPart("productImage");

        try {
            int productId = Integer.parseInt(productIdStr);
            double productPrice = Double.parseDouble(productPriceStr);

            byte[] productImage = null;
            if (productImagePart != null && productImagePart.getSize() > 0) {
                InputStream inputStream = productImagePart.getInputStream();
                productImage = inputStream.readAllBytes();
            }
            Product product = new Product();
            product.setProductID(productId);
            product.setProductName(productName);
            product.setProductPrice(productPrice);
            product.setProductDescription(productDescription);
            product.setProductCategory(productCategory);
            product.setProductImage(productImage);

            ProductDao productDao = new ProductDao();
            HttpSession session = request.getSession(false);
            Admin admin = (Admin) session.getAttribute("admin");

            productDao.updateProduct(product, admin);

            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to update product. Please try again.");
            request.getRequestDispatcher("/views/admin/editproduct.jsp").forward(request, response);
        }
        }
}
