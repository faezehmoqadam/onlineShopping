<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>


<div class="menu-container">

    <a href="${pageContext.request.contextPath}/">Home</a>
    |
    <a href="${pageContext.request.contextPath}/productList">
        Product List
    </a>
    |
    <a href="${pageContext.request.contextPath}/shoppingCart">
        My Cart
        <i class="fa fa-shopping-cart" style="font-size: 20px;"></i>
    </a>
    |
    <security:authorize  access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
        <a href="${pageContext.request.contextPath}/orderList">
            Order List
        </a>
        |
    </security:authorize>

    <security:authorize  access="hasRole('ROLE_MANAGER')">
        <a href="${pageContext.request.contextPath}/product">
            Create Product
        </a>
        |
        <a href="${pageContext.request.contextPath}/discountMaker">
            Create Discount
        </a>
    </security:authorize>

</div>