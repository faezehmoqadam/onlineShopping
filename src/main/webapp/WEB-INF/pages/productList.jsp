<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">

</head>
<body>

<jsp:include page="_header.jsp" />
<jsp:include page="_menu.jsp" />

<fmt:setLocale value="en_US" scope="session"/>

<div class="page-title">Product List</div>

<form action="productList">
    <select name="cat" path="category">
        <option value="All">All</option>
        <option value="food">food</option>
        <option value="Clothing">Clothing</option>
        <option value="sport equipments">sport equipments</option>
        <option value="Kitchenware">Kitchenware</option>
        <option value="other">other</option>
    </select>
    <input type="submit" value="Show">
</form>
<%
    if(request.getParameter("cat")==null || request.getParameter("cat").equals("All"))
        out.print("<h2>ALL</h2>\n");
    else {
%><h3>${paginationProducts.list[0].category}</h3><%}%>

<c:forEach items="${paginationProducts.list}" var="prodInfo">
    <div class="product-preview-container">
        <ul>
            <li><img class="product-image"
                     src="${pageContext.request.contextPath}/productImage?code=${prodInfo.code}" /></li>

            <li class="product-code">Code: ${prodInfo.code}</li>
            <li class="product-name">Name: ${prodInfo.name}</li>
            <li style="display: inline-block">
            <li class="price">Price: <fmt:formatNumber value="${prodInfo.price}" type="currency"/></li>
            <li class="product-buy"><a
                    class="fa fa-cart-plus"
                    href="${pageContext.request.contextPath}/buyProduct?code=${prodInfo.code}"></a></li>
            </li>
            <security:authorize  access="hasRole('ROLE_MANAGER')">
                    <a style="color:red;"
                       href="${pageContext.request.contextPath}/product?code=${prodInfo.code}">
                    Edit Product</a>
                <h4>Existence: ${prodInfo.isExist()}</h4>
                </li>
            </security:authorize>
        </ul>
    </div>

</c:forEach>
<br/>


<c:if test="${paginationProducts.totalPages > 1}">
    <div class="page-navigator">
        <c:forEach items="${paginationProducts.navigationPages}" var = "page">
            <c:if test="${page != -1 }">
                <a href="productList?page=${page}" class="nav-item">${page}</a>
            </c:if>
            <c:if test="${page == -1 }">
                <span class="nav-item"> ... </span>
            </c:if>
        </c:forEach>

    </div>
</c:if>

<jsp:include page="_footer.jsp" />

</body>
</html>