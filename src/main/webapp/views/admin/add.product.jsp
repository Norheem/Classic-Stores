<%--
  Created by IntelliJ IDEA.
  User: NOHIM SULAIMAN
  Date: 11/14/2024
  Time: 4:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Product</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/admin.addproduct.css">
</head>
<body>
<div id="addProductModal" class="modal">
    <div class="modal-content">
        <div class="form">
            <form method="post" action="${pageContext.request.contextPath}/admin/addproduct" enctype="multipart/form-data">
                <div class="form-header">
                    <h1>Add A Product</h1>
                    <h3>Only and admin can add a product</h3>
                    <div class="form-input">
                        <label for="productName" class="productName"> Product Name <span class="compulsory"> *</span> </label>
                        <input class="input" type="text" id="productName" placeholder="Enter product name" name="productName">
                    </div>
                    <div class="form-input">
                        <label for="productPrice" class="productPrice"> Product Price <span class="compulsory"> *</span> </label>
                        <input class="input" type="number" id="productPrice" placeholder="Enter product price" name="productPrice">
                    </div>

                    <div class="form-input">
                        <label for="productDescription" class="productDescription"> Product Description <span class="compulsory"> *</span> </label>
                        <input class="input" type="text" id="productDescription" placeholder="Enter product description" name="productDescription">
                    </div>
                    <div class="form-input">
                        <label for="productCategory" class="productCategory"> Product Category <span class="compulsory"> *</span> </label>
                        <input class="input" type="text" id="productCategory" placeholder="Enter product category" name="productCategory" >
                    </div>
                    <div class="form-input">
                        <label for="productImage" class="productImage"> Product Image <span class="compulsory"> *</span> </label>
                        <input class="input" type="file" id="productImage"  name="productImage" >
                    </div>
                    <div>
                        <button class="save-btn add-product-button" type="submit">Add Product</button>
                        <button class="cancel-btn add-product-cancel" type="button"
                                onclick="window.location.href='${pageContext.request.contextPath}/admin/dashboard'">Cancel
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
