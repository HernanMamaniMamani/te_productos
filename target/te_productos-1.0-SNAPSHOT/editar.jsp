<%@page import="com.emergentes.modelo.Producto"%>
<%
    Producto prod = (Producto)request.getAttribute("prod");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Nuevo producto</h1>
        <form action="MainController" method post>
            <input type="hidden" name = "id" value="${prod.id}">
            <table>
                <tr>
                    <td>Nombre</td>
                    <td><input type="text" name="nombre" value="${lib.nombre}"></td>
                </tr>
                <tr>
                    <td>Precio</td>
                    <td><input type="text" name="titulo" value="${lib.precio}"></td>
                </tr>
                <tr>
                    <td>Cantidad</td>
                    <td><input type="text" name="categoria" value="${lib.cantidad}"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit"></td>
                </tr>
            </table>
            
        </form>
    </body>
</html>
