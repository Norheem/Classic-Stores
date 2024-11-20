package org.classicstore.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.classicstore.dao.cart.CartDao;
import org.classicstore.dao.product.ProductDao;
import org.classicstore.model.Cart;
import org.classicstore.model.Product;
import org.classicstore.model.User;
import java.io.IOException;
import java.util.List;


@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private final CartDao cartDao = new CartDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/login");
            return;
        }
        List<Cart> cartList = cartDao.getCartItems();

        request.setAttribute("cartItems", cartList);
        request.getRequestDispatcher("views/user/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/views/user/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        String action = request.getParameter("action");

        if ("addToCart".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            ProductDao productDao = new ProductDao();
            Product product = productDao.getProductById(productId);


            if (product != null) {
                String message = cartDao.addToCart(user, product, 1);
                if (message.contains("added")) {
                    session.setAttribute("successMessage", message);
                    session.setAttribute("cartCount", cartDao.getCartItems().size());
                    System.out.println("This is the product added to the cart: " + cartDao.getCartItems());
                } else {
                    session.setAttribute("errorMessage", message);
                }
            }
            response.sendRedirect("/home");
        } else if ("removeFromCart".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            ProductDao productDao = new ProductDao();
            Product product = productDao.getProductById(productId);

            if (product != null) {
                cartDao.removeFromCart(user, product);
            }
            response.sendRedirect("/cart");
        } else if ("clearCart".equals(action)) {
            cartDao.clearCart(user);
            session.setAttribute("cartCount", 0);
            session.setAttribute("successMessage", "Your cart has been successfully cleared!");
            response.sendRedirect("/cart");
        } else if ("updateQuantity".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            ProductDao productDao = new ProductDao();
            Product product = productDao.getProductById(productId);
            if (product != null) {
                cartDao.updateQuantity(user, product, quantity);
            }
            response.sendRedirect("/cart");
        }
    }
}


