<%--
  Created by IntelliJ IDEA.
  User: Isra
  Date: 11/12/2021
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Registrar usuario</title>
    </head>
    <body>
    <h1>REGISTRAR:</h1>
        <form action="veriRegistro" method="GET">
            <input type="text" name="nombre_usuario" placeholder="Nombre Usuario" />
            <input type="text" name="pass_usuario" placeholder="Contraseña" />
            <input type="submit" value="Registrarse"/>
        </form>
    </body>
</html>
