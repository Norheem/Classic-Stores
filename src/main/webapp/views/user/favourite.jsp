<%--
  Created by IntelliJ IDEA.
  User: NOHIM SULAIMAN
  Date: 11/17/2024
  Time: 1:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.classicstore.model.Favourite" %>
<%@ page import="org.classicstore.model.Product" %>
<%@ page import="java.util.Base64" %>
<html>
<head>
    <title>Favourite</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/user/favourite.css">
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

    <h2 class="favorite-header">Your Favourite Items</h2>
<c:if test="${not empty favouriteItems}">
    <table border="1"  class="main-table">
        <thead>
        <tr>
            <th>S/N</th>
            <th>Product Name</th>
            <th>Product Image</th>
            <th>Remove</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Favourite> favouriteItems = (List<Favourite>) request.getAttribute("favouriteItems");
            int serialNumber = 1;

            for (Favourite favourite : favouriteItems) {
                Product product = favourite.getProduct();
                String base64Image = Base64.getEncoder().encodeToString(product.getProductImage());
        %>
        <tr>
            <td><%= serialNumber++ %></td>
            <td><%= product.getProductName() %></td>
            <td>
                <img src="data:image/jpeg;base64,<%= base64Image %>" alt="<%= product.getProductName() %>" width="50" height="50">
            </td>
            <td>
                <form action="<%= request.getContextPath() %>/favourite" method="post" style="display: flex; align-items: center; justify-content: center;">
                    <input type="hidden" name="action" value="removeFromFavourite">
                    <input type="hidden" name="productId" value="<%= product.getProductID() %>">
                    <button type="submit" class="delete-action-btn">Remove</button>
                </form>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</c:if>

</body>
</html>
