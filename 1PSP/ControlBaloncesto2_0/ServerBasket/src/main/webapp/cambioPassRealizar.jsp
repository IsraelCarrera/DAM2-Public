<%--
  Created by IntelliJ IDEA.
  User: Isra
  Date: 01/01/2022
  Time: 21:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Usuario. Cambiar contraseña</title>
</head>
<body>
<form action="PassCambiada" method="GET">
    <input type="hidden" name="codigoPass" value="${codigoDeCambioPass}">
  <input type="password" name="PassAntigua" placeholder="Contraseña vieja"/>
  <input type="password" name="passNueva" placeholder="Nueva contraseña"/>
  <input type="password" name="passNuevaRepit" placeholder="Repite la nueva contraseña"/>
  <input type="submit" value="cambiar"/>
</form>
</body>
</html>
