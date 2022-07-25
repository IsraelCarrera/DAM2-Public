<%--
  Created by IntelliJ IDEA.
  User: Isra
  Date: 12/12/2021
  Time: 18:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome back</title>
</head>
<body>
    <h2>Welcome a la página, señor/a <b>${user.getNombre()}</b></h2>
    <a href="TablaUser"> ir a ver la tabla de usuarios</a>
    <br>
    <a href="logout"> logout</a>
</body>
</html>
