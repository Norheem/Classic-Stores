<%--
  Created by IntelliJ IDEA.
  User: NOHIM SULAIMAN
  Date: 11/17/2024
  Time: 4:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.classicstore.model.Cart" %>
<%@ page import="org.classicstore.model.Product" %>
<%@ page import="java.util.Base64" %>
<html>
<head>
    <title>Cart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/user/cart.css">
</head>
<body>

<nav>
    <div class="logo-home-wrapper">
        <img src="${pageContext.request.contextPath}/assets/classic_store_logo.png" class="logo">
        <a href="${pageContext.request.contextPath}/home" class="homepage">Home</a>
    </div>

    <h1 class="header">Welcome to Classic Store</h1>
    <div class="nav-container">
        <div class="dropdown">
            <a class="nav-link">Categories </a>
            <div class="dropdown-content">
                <a href="${pageContext.request.contextPath}/categories/furniture">Furniture</a>
                <a href="${pageContext.request.contextPath}/categories/men-wear">Men's Wear</a>
                <a href="${pageContext.request.contextPath}/categories/women-bag">Women's Bag</a>
                <a href="${pageContext.request.contextPath}/categories/smartphone">Smartphone</a>
                <a href="${pageContext.request.contextPath}/categories/women-jewelry">Women's Jewelry</a>
                <a href="${pageContext.request.contextPath}/categories/electronics">Electronics</a>
                <a href="${pageContext.request.contextPath}/categories/men-shoe">Men's Shoe</a>
                <a href="${pageContext.request.contextPath}/categories/sport-accessories">Sport Accessories</a>
            </div>
        </div>
        <a href="${pageContext.request.contextPath}/cart" class="nav-link add-to-product">Cart
            <span id="cart-count">
                    <c:out value="${sessionScope.cartCount != null ? sessionScope.cartCount : 0}" />
                </span>
        </a>
        <a href="${pageContext.request.contextPath}/favourite" class="nav-link favourite-link">Favourite</a>
        <a href="${pageContext.request.contextPath}/logout" class="logout-link">Logout</a>
    </div>
</nav>

<div class="center-container">
    <h1 class="cart-header">Your Shopping Cart</h1>

    <c:if test="${not empty cartItems}">
        <table border="1" >
            <thead>
            <tr>
                <th>S/N</th>
                <th>Product Name</th>
                <th>Product Image</th>
                <th>Quantity</th>
                <th>Price</th>
                <th>Remove</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Cart> cartItems = (List<Cart>) request.getAttribute("cartItems");
                double totalPrice = 0;
                int serialNumber = 1;

                for (Cart cart : cartItems) {
                    Product product = cart.getProduct();
                    int quantity = cart.getQuantity();
                    double price = product.getProductPrice() * quantity;
                    totalPrice += price;

                    String base64Image = Base64.getEncoder().encodeToString(product.getProductImage());
            %>
            <tr>
                <td><%= serialNumber++ %></td>
                <td><%= product.getProductName() %></td>
                <td>
                    <img src="data:image/jpeg;base64,<%= base64Image %>" alt="<%= product.getProductName() %>" width="50" height="50">
                </td>
                <td>
                    <form action="<%= request.getContextPath() %>/cart" method="post" style="display: flex; align-items: center; justify-content: center;">
                        <input type="hidden" name="action" value="updateQuantity">
                        <input type="hidden" name="productId" value="<%= product.getProductID() %>">
                        <button type="submit" name="quantity" value="-1" style="width: 30px;">-</button>
                        <span style="margin: 0 10px;"><%= quantity %></span>
                        <button type="submit" name="quantity" value="1" style="width: 30px;">+</button>
                    </form>
                </td>
                <td>₦<%= price %><span>0</span></td>
                <td>
                    <form action="<%= request.getContextPath() %>/cart" method="post">
                        <input type="hidden" name="action" value="removeFromCart">
                        <input type="hidden" name="productId" value="<%= product.getProductID() %>">
                        <button type="submit" class="delete-action-btn">Remove</button>
                    </form>
                </td>
            </tr>
            <% } %>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="4" style="text-align: right; font-weight: bold;">Total Price:</td>
                <td colspan="2">₦<%= totalPrice %><span>0</span></td>
            </tr>
            </tfoot>
        </table>

        <div style="display: flex; justify-content: space-between; margin-top: 20px;">
            <form action="<%= request.getContextPath() %>/cart" method="post">
                <input type="hidden" name="action" value="clear">
                <button type="submit" style="background-color: red; color: white; padding: 10px 20px; border: none; cursor: pointer;">Clear Cart</button>
            </form>

            <form action="<%= request.getContextPath() %>/cart" method="post">
                <input type="hidden" name="action" value="clear">
                <button type="submit" style="background-color: #4CAF50; color: white; padding: 10px 20px; border: none; cursor: pointer;">Checkout</button>
            </form>
        </div>

    </c:if>
</div>
</body>
</html>