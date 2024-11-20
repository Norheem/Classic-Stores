<%--
  Created by IntelliJ IDEA.
  User: NOHIM SULAIMAN
  Date: 11/16/2024
  Time: 11:58 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/admin.editproduct.css">
</head>
<body>
<c:if test="${not empty product}">
    <div id="addProductModal" class="modal">
        <div class="modal-content">
            <div class="form">
                <form method="post" action="${pageContext.request.contextPath}/admin/editproduct" enctype="multipart/form-data">
                    <div class="form-header">
                        <h1>Edit Product</h1>
                        <h3>Only and admin can edit a product</h3>

                        <input type="hidden" name="productID" value="${product.productID}">

                        <div class="form-input">
                            <label for="productName" class="productName"> Product Name <span class="compulsory"> *</span> </label>
                            <input class="input" type="text" id="productName" value="${product.productName}" name="productName">
                        </div>
                        <div class="form-input">
                            <label for="productPrice" class="productPrice"> Product Price <span class="compulsory"> *</span> </label>
                            <input class="input" type="number" id="productPrice" value="${product.productPrice}" name="productPrice">
                        </div>

                        <div class="form-input">
                            <label for="productDescription" class="productDescription"> Product Description <span class="compulsory"> *</span> </label>
                            <input class="input" type="text" id="productDescription" value="${product.productDescription}" name="productDescription">
                        </div>
                        <div class="form-input">
                            <label for="productCategory" class="productCategory"> Product Category <span class="compulsory"> *</span> </label>
                            <input class="input" type="text" id="productCategory" value="${product.productCategory}" name="productCategory" >
                        </div>
                        <div class="form-input">
                            <label for="productImage" class="productImage"> Product Image <span class="compulsory"> *</span> </label>
                            <input class="input" type="file" id="productImage"  name="productImage">
                        </div>
                        <div>
                            <button class="edit-btn edit-product-button" type="submit">Save Changes</button>
                            <%--                        onclick="closeAddProductModal()"--%>
                            <button class="cancel-btn edit-product-cancel" type="button"
                                    onclick="window.location.href='${pageContext.request.contextPath}/admin/dashboard'">Cancel
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</c:if>
</body>
</html>
