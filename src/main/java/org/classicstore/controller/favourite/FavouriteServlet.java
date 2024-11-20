package org.classicstore.controller.favourite;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.classicstore.dao.favourite.FavouriteDao;
import org.classicstore.dao.product.ProductDao;
import org.classicstore.model.Favourite;
import org.classicstore.model.Product;
import org.classicstore.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/favourite")
public class FavouriteServlet extends HttpServlet {
    private final FavouriteDao favouriteDao = new FavouriteDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/login");
            return;
        }

        List<Favourite> favouriteList = favouriteDao.getFavouriteItems();

        request.setAttribute("favouriteItems", favouriteList);
        request.getRequestDispatcher("views/user/favourite.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        String action = request.getParameter("action");

        if ("addToFavourite".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            ProductDao productDao = new ProductDao();
            Product product = productDao.getProductById(productId);

            if (product != null) {
                String message = favouriteDao.addToFavourite(user, product);
                if (message.contains("added")) {
                    session.setAttribute("successMessage", message);
                } else {
                    session.setAttribute("errorMessage", message);
                }
            }
            response.sendRedirect("/home");
        } else if ("removeFromFavourite".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            ProductDao productDao = new ProductDao();
            Product product = productDao.getProductById(productId);

            if (product != null) {
                favouriteDao.removeFromFavourite(user, product);
            }
            response.sendRedirect("/favourite");
        }
    }
}
