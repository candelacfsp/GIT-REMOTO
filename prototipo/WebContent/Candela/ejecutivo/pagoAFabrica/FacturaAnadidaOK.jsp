<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page session="true" %>
<%@page import="negocio.*" %>
<%@page import="persistencia.*" %>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div align="center">
	<h1 > <font color="red"  >La factura fue registrada correctamente. Si desea abonar mas facturas pulse "Continuar carga",</h1><br>
	<h1 ><font color="red">sino pulse "Finalizar carga" para obtener un listado de las facturas que se abonaron.</font></h1>
	<a href="formPagoAFabrica.jsp"><b>Continuar Carga</b></a>
	<a href="mostrarFacturasFabPagas.jsp"><b>Finalizar Carga</b></a>
	</div>
</body>
</html>