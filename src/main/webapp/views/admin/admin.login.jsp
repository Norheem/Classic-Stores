<%--
  Created by IntelliJ IDEA.
  User: NOHIM SULAIMAN
  Date: 11/12/2024
  Time: 12:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/admin.login.css">
</head>
<body>
<!-- Display success message if available -->
<c:if test="${not empty successMessage}">
    <p class="success-message">${successMessage}</p>
</c:if>

<!-- Display error message if login fails -->
<c:if test="${not empty error}">
    <p class="error-message">${error}</p>
</c:if>
<div class="form">
    <form action=${pageContext.request.contextPath}/admin/login method="post">
        <div class="form-header">
            <h1>Welcome To Admin Portal</h1>
            <h3>Login to your admin account</h3>
            <div class="form-input">
                <label for="email" class="email"> Email </label>
                <input class="input" type="email" id="email" placeholder="admin@email" name="email">
            </div>
            <div class="form-input-password">
                <label for="password" class="password"> Password </label>
                <input class="input" type="password" id="password" placeholder="Enter your password" name="password">
            </div>
            <h3 class="forget-password">Forgot Password?</h3>
            <button class="submit">Login</button>
            <h3>Don't have an account?  <a href="${pageContext.request.contextPath}/admin/signup" class="signup">Signup</a></h3>
        </div>
    </form>
</div>
</body>
</html>