<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">

</head>
<body>

<jsp:include page="_header.jsp" />
<jsp:include page="_menu.jsp" />

<div class="page-title">Product</div>

<c:if test="${not empty errorMessage }">
    <div class="error-message">
            ${errorMessage}
    </div>
</c:if>

<form:form modelAttribute="productForm" method="POST" enctype="multipart/form-data">
    <table style="text-align:left;">
        <tr>
            <td>Code *</td>
            <td style="color:red;">
                <c:if test="${not empty productForm.code}">
                       ${productForm.code}
                </c:if>
                <c:if test="${empty productForm.code}">
                    <form:input path="code" />
                    <form:hidden path="newProduct" />
                </c:if>
            </td>
            <td><form:errors path="code" class="error-message" /></td>
        </tr>

        <tr>
            <td>category *</td>
            <td>
                <form:select path="category">
                    <form:option value="food">food</form:option>
                    <form:option value="Clothing">Clothing</form:option>
                    <form:option value="sport equipments">sport equipments</form:option>
                    <form:option value="Kitchenware">Kitchenware</form:option>
                    <form:option value="other">other</form:option>
                </form:select>
            </td>

                </td>
        </tr>
        <tr>
            <td>Fee *</td>
            <td>
                <form:select path="Fee">
                    <form:option value="number">number</form:option>
                    <form:option value="k">k</form:option>
                    <form:option value="kg">kg</form:option>
                    <form:option value="m">m</form:option>
                    <form:option value="cm">cm</form:option>


                </form:select>

            </td>
        </tr>

        <tr>
            <td>Name *</td>
            <td><form:input path="name" /></td>
            <td><form:errors path="name" class="error-message" /></td>
        </tr>

        <tr>
            <td>Price *</td>
            <td><form:input path="price" /></td>
            <td><form:errors path="price" class="error-message" /></td>
        </tr>
<%--        <tr>--%>
<%--            <td>Quantity *</td>--%>
<%--            <td><form:input path="quantity" /></td>--%>
<%--            <td><form:errors path="quantity" class="error-message" /></td>--%>
<%--        </tr>--%>
        <tr>
        <td>Existence *</td>
            <td>
            <form:select path="exist">
            <form:option  value="false">Not Exist</form:option>
            <form:option value="true">Exist</form:option>
            </form:select>
            </td>
        </tr>
        <tr>
            <td>Image</td>
            <td>
                <img src="${pageContext.request.contextPath}/productImage?code=${productForm.code}" width="100"/></td>
            <td> </td>
        </tr>
        <tr>
            <td>Upload Image</td>
            <td><form:input type="file" path="fileData"/></td>
            <td> </td>
        </tr>


        <tr>
            <td>&nbsp;</td>
            <td><input type="submit" value="Submit" /> <input type="reset"
                                                              value="Reset" /></td>
        </tr>
    </table>
</form:form>
<jsp:include page="_footer.jsp" />

</body>
</html>