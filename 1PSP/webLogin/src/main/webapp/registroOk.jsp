<%--
  Created by IntelliJ IDEA.
  User: Isra
  Date: 12/12/2021
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registro ok</title>
</head>
<body>
<p>Se ha registrado con éxito, usuario <b>${userRegistrado.getNombre()}</b></p>
    <form action="logging">
        <button>Ir Logging</button>
    </form>
</body>
</html>
