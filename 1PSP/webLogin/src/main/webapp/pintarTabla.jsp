<%--
  Created by IntelliJ IDEA.
  User: Isra
  Date: 12/12/2021
  Time: 18:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Pintando tabla de usuarios</title>
</head>
<body>
  <h2>Usuarios registrados en esta web:</h2>
  <table class="default">
      <tr>
          <th>ID</th>
          <th>Nombre</th>
          <th>Pass</th>
      </tr>
      <c:forEach items="${listaUser}" var="usu">
          <tr>
              <td>${usu.getId()}</td>
              <td>${usu.getNombre()}</td>
              <td>${usu.getPass()}</td>
          </tr>
      </c:forEach>
  </table>
</body>
</html>
