<%--
  Created by IntelliJ IDEA.
  User: NOHIM SULAIMAN
  Date: 11/12/2024
  Time: 12:30 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="org.classicstore.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="org.classicstore.model.Favourite" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/user/home.css">
    <script>
        setTimeout(function() {
            const successMessage = document.getElementById("success-message");
            const errorMessage = document.getElementById("error-message");

            if (successMessage) successMessage.style.display = "none";
            if (errorMessage) errorMessage.style.display = "none";
        }, 5000);
    </script>
</head>
<body>


<c:if test="${not empty successMessage}">
    <p id="success-message" class="success-message" style="color: green; font-weight: bold;">
            ${successMessage}
    </p>
</c:if>


<c:if test="${not empty errorMessage}">
    <p  id="error-message" class="error-message" style="color: red; font-weight: bold; margin-top: 40px">
            ${errorMessage}
    </p>
</c:if>

<c:if test="${not empty successMessage}">
    <p class="success-message" style="color: green; font-weight: bold; margin-top: 25px">
            ${successMessage}
    </p>
    <c:remove var="successMessage" scope="session"/>
</c:if>

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

<p class="moto">Your home of quality store that you can always trust...</p>
<h2 class="our-products">Our Products</h2>

<div class="product-grid">

    <%
        List<Product> productList = (List<Product>) request.getAttribute("productList");
        if (productList != null && !productList.isEmpty()) {
            for (Product product : productList) {
    %>
    <div class="product-details">
        <%
            byte[] imageBytes = product.getProductImage();
            String base64Image = (imageBytes != null) ? java.util.Base64.getEncoder().encodeToString(imageBytes) : "";
            boolean isFavorite = false;

            List<Favourite> favouriteList = (List<Favourite>) request.getAttribute("favouriteItems");
            if (favouriteList != null) {
                for (Favourite favourite : favouriteList) {
                    if (favourite.getProduct().getProductID() == product.getProductID()) {
                        isFavorite = true;
                        break;
                    }
                }
            }
        %>
        <div style="position: relative; width: 100%; height: 100px;">
            <form action="<%= request.getContextPath() %>/favourite" method="post" style="position: absolute; top: 10px; right: 10px;">
                <input type="hidden" name="action" value="<%= isFavorite ? "removeFromFavourite" : "addToFavourite" %>">
                <input type="hidden" name="productId" value="<%= product.getProductID() %>">
                <button type="submit" class="favorite-icon-btn" style="background: none; border: none; cursor: pointer;">
                    <span class="favorite-icon" style="color: <%= isFavorite ? "red" : "gray" %>;">&#x2764;&#xfe0f;</span>
                </button>
            </form>
        </div>

        <div class="product-image">
            <img src="data:image/jpeg;base64,<%= base64Image %>" alt="<%= product.getProductName() %>" />
        </div>
        <h4 class="product-name"><%= product.getProductName() %></h4>
        <h3 class="product-price"><span>₦</span><%= product.getProductPrice() %><span>0</span></h3>
        <p class="product-description"><%= product.getProductDescription() %></p>

        <% if (session != null && session.getAttribute("user") != null) { %>
        <form action="<%= request.getContextPath() %>/cart" method="post">
            <input type="hidden" name="action" value="addToCart">
            <input type="hidden" name="productId" value="<%= product.getProductID() %>">
            <button type="submit" class="add-to-cart">Add to Cart</button>
        </form>
        <% } else { %>
        <a href="${pageContext.request.contextPath}/login">Login to Add to Cart</a>
        <% } %>
    </div>
    <%
        }
    } else {
    %>
    <p>No products available.</p>
    <% } %>
</div>

</body>

</html>
