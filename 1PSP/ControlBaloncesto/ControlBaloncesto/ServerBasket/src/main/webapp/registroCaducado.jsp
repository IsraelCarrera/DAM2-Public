<%--
  Created by IntelliJ IDEA.
  User: Isra
  Date: 01/01/2022
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registro caducado</title>
</head>
<body>
<p><b>${nombreUsuario}</b> ha tardado más de lo esperado y ha caducado su código de registro.</p>
<p>pinche en el botón de abajo para volver a recibir el correo de activación.</p>
<form action="reenvioCodigoAct" method="GET">
    <input type="hidden" name="codigoAct" value="${codigoActivacion}">
    <input type="submit" value="nuevo codigo"/>
</form>
</body>
</html>
