<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="header-container">

    <div class="site-name">Online Shop</div>

    <div class="header-bar">
        <c:if test="${pageContext.request.userPrincipal.name != null}">
            Hello
            <a href="${pageContext.request.contextPath}/accountInfo">
                    ${pageContext.request.userPrincipal.name} </a>
            &nbsp;|&nbsp;

            <form method="post" action="${pageContext.request.contextPath}/logout" >
                <input type="submit" value="Logout">
            </form>
<%--            <a href="${pageContext.request.contextPath}/j_spring_security_logout">Logout</a>--%>
        </c:if>
        <c:if test="${pageContext.request.userPrincipal.name == null}">
            <a href="${pageContext.request.contextPath}/login">Login</a>
       <a href="${pageContext.request.contextPath}/SignUp">SignUp</a>
        </c:if>
    </div>
</div>