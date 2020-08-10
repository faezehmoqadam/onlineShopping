<%@page language="java" contentType="text/html; ISO-8859-1"
pageEncoding="ISO-8859-1" %>
<%@page isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html >
    <html>
    <head>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

        <meta charset="UTF-8">
        <title>Sign Up</title>
        <link rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
        <script>
            var check = function() {
            if (document.getElementById('password').value ==
                document.getElementById('confirm_password').value) {
                document.getElementById('message').style.color = 'green';
                document.getElementById('message').innerHTML = 'matching';
            } else {
                document.getElementById('message').style.color = 'red';
                document.getElementById('message').innerHTML = 'not matching';
            }
        }
        </script>
    </head>
    <body>
    <jsp:include page="_header.jsp"/>
    <jsp:include page="_menu.jsp"/>

        <form:form action="${pageContext.request.contextPath}/SignUp" method="POST" modelAttribute="account">
            <table>
                <tr>
                    <td><form:label path="userName" >User Name *</form:label></td>
                    <td><form:input path="userName" required="required"  /> </td>
                    <td style="color:red;"><form:errors path="userName"></form:errors> </td>
                </tr>
                <tr>
                    <td><form:label path="password">Password *</form:label></td>
                    <td><form:password id="password" path="password" required="required" onkeyup="check()"/></td>
                    <td style="color:red;"><form:errors path="password"></form:errors> </td>
                </tr>

                <tr>
                    <td><form:label path="confirmPassword">Confirm Password *</form:label></td>
                    <td><form:password id="confirmPassword" path="confirmPassword" required="required" onkeyup="check()"/></td>
                    <td style="color:red;"><form:errors path="confirmPassword"></form:errors>
                    </td>
                </tr>

                <tr>
                    <td><form:label path="email">Email *</form:label></td>
                    <td><form:input path="email" required="required"/> </td>
                    <td style="color:red;"><form:errors path="email"></form:errors> </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td><input type="submit" value="Sign Up" id="btnSubmit" onclick="return Validate()"/> <input type="reset"
                                                                       value="Reset" /></td>
                </tr>
            </table>
            <script type="text/javascript">
                function Validate() {
                    var password = document.getElementById("password").value;
                    var confirmPassword = document.getElementById("confirmPassword").value;
                    if (password != confirmPassword) {
                        alert("Passwords do not match.");
                        return false;
                    }
                    return true;
                }
            </script>
        </form:form>
    </div>
    <jsp:include page="_footer.jsp"/>
    </body>
    </html>