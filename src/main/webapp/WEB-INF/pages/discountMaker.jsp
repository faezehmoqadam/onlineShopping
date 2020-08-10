<%--
  Created by IntelliJ IDEA.
  User: mmd
  Date: 8/10/2020
  Time: 8:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">

    <title>Discount Maker</title>
</head>
<body>

<jsp:include page="_header.jsp" />
<jsp:include page="_menu.jsp" />


<form action="${pageContext.request.contextPath}/discountMaker" method="post">
    <input type="text" name="code" placeholder="Code"/> <input type="text" name="amount" placeholder="Amount"/> <input type="submit" name="Add!"/>
</form>
<jsp:include page="_footer.jsp" />

</body>
</html>
