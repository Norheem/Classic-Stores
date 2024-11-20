<%--
  Created by IntelliJ IDEA.
  User: NOHIM SULAIMAN
  Date: 11/12/2024
  Time: 12:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Signup</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/admin.signup.css">
</head>
<body>

<c:if test="${not empty successMessage}">
    <p class="success-message">${successMessage}</p>
</c:if>


<c:if test="${not empty error}">
    <p class="error-message">${error}</p>
</c:if>
<div class="form">
    <form method="post" action="${pageContext.request.contextPath}/admin/signup">
        <div class="form-header">
            <h1>Create Admin Account</h1>
            <h3>It's free and only takes a minute</h3>
            <div class="form-input">
                <label for="firstName" class="firstName"> First Name <span class="compulsory"> *</span> </label>
                <input class="input" type="text" id="firstName" placeholder="Enter your first name" name="firstName">
            </div>
            <div class="form-input">
                <label for="lastName" class="lastName"> Last Name <span class="compulsory"> *</span> </label>
                <input class="input" type="text" id="lastName" placeholder="Enter your last name" name="lastName">
            </div>
            <div class="form-input">
                <label for="email" class="email"> Email <span class="compulsory"> *</span> </label>
                <input class="input" type="email" id="email" placeholder="Enter your email" name="email">
            </div>
            <div class="form-input-password">
                <label for="password" class="password"> Password <span class="compulsory"> *</span> </label>
                <input class="input" type="password" id="password" placeholder="Enter your password" name="password" >
            </div>
            <div class="form-input-password">
                <label for="confirmPassword" class="confirmPassword">Confirm Password <span class="compulsory" > *</span> </label>
                <input class="input" type="password" id="confirmPassword" placeholder="Enter your password" name="confirmPassword" autocomplete="off" >
            </div>
            <button class="signup" type="submit">Signup</button>
            <h3>Already have an account?  <a href="${pageContext.request.contextPath}/admin/login" class="login">Login</a></h3>
        </div>
    </form>
</div>

</body>
</html>