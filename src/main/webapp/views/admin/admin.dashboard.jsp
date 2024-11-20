<%@ page import="org.classicstore.model.Product" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: NOHIM SULAIMAN
  Date: 11/12/2024
  Time: 12:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Dashboard</title>

<link rel="stylesheet"  href="${pageContext.request.contextPath}/views/admin/admin.dashboard.css">

</head>
<body>
<c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
</c:if>
<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>



<c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
    <%
        session.removeAttribute("message");
    %>
</c:if>
<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
    <%
        session.removeAttribute("error");
    %>
</c:if>


<nav>
    <img src="${pageContext.request.contextPath}/assets/classic_store_logo.png" class="logo">
<%--    onclick="openAddProductModal(); return false"
javascript:void(0);
--%>
    <h1 class="header">ADMIN PORTAL</h1>
    <div class="nav-container">
<%--        <a href="${pageContext.request.contextPath}/admin/addproduct" class="add-product" >Add New Product</a>--%>
    <% if (session.getAttribute("admin") != null) { %>
    <a href="${pageContext.request.contextPath}/admin/addproduct" class="add-product">Add New Product</a>
    <% } %>

    <a href="${pageContext.request.contextPath}/login" class="user-link">User</a>
        <a href="${pageContext.request.contextPath}/admin/logout" class="logout-link">Logout</a>
    </div>
</nav>


<p class="welcome">You have successfully logged in as an admin.</p>
<h2 class="available">Available Products</h2>
<table >
    <thead>
    <tr>
        <th>S/N</th>
        <th>Product Name</th>
        <th>Product Image</th>
        <th>Product Price</th>
        <th>Product Category</th>
        <th>Product Description</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tbody>

    <%
        List<Product> productList = (List<Product>) request.getAttribute("productList");
        if (productList != null && !productList.isEmpty()) {
            for (Product product : productList) {
    %>
    <%
        byte[] imageBytes = product.getProductImage();
        String base64Image = (imageBytes != null) ? java.util.Base64.getEncoder().encodeToString(imageBytes) : "";
    %>
    <tr>
        <td><%= product.getProductID() %></td>
        <td><%= product.getProductName() %></td>
        <td>
            <img src="data:image/jpeg;base64,<%= base64Image %>"
             alt="<%= product.getProductName() %>" width="50" height="50" />
        </td>
        <td><%= product.getProductPrice() %></td>
        <td><%= product.getProductCategory() %></td>

        <td><%= product.getProductDescription() %></td>
<%--        <th class="action-btn edit-btn">Edit</th>--%>
<%--        <th class="action-btn delete-btn">Delete</th>--%>

        <td class="edit-action-btn edit-link">
            <a href="<%= request.getContextPath() %>/admin/editproduct?productId=<%= product.getProductID() %>" class="edit-link">Edit</a>
        </td>
        <td class="delete-action-btn delete-link">
            <a href="<%= request.getContextPath() %>/admin/deleteproduct?productId=<%= product.getProductID() %>"
               onclick="return confirm('Are you sure you want to delete this product?');" class="delete-link">Delete</a>
        </td>

    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="6" style="text-align: center">No products available.</td>
    </tr>
    <%
        }
    %>

    </tbody>
</table>

<%--<jsp:include  page="/views/admin/add.product.jsp" />--%>
</body>
</html>
