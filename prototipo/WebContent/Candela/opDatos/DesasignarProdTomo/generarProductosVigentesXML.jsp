<%@page import="utilidades.GeneradorXML"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%@page session="true" %>
<%@page import="negocio.*" %>


<%
	/* generarProductosVigentesXML.jsp llama a generador XML para generar los productos que estan asignados a un 
	tomo seleccionado desde flash.*/

	HttpSession candela_sesion=request.getSession();
	Candela cand=(Candela) candela_sesion.getAttribute("candela");
	
	String nroTomo=request.getParameter("numeroTomo");
	int numeroTomo=Integer.parseInt(nroTomo);
	
	
	GeneradorXML gen= new GeneradorXML(cand);
	gen.generarProductosVigentes(numeroTomo,500); 
	
%>

</body>
</html>