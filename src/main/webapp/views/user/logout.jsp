<%--
  Created by IntelliJ IDEA.
  User: NOHIM SULAIMAN
  Date: 11/17/2024
  Time: 3:29 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
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

</body>
</html>
