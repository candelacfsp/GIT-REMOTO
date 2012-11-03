<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%@page import="utilidades.GeneradorXML" %>
<%@page import="negocio.Candela" %>
<%@page session="true" %>

<% 
	//redirect.jsp actualiza por medio de un objeot generadorXML el XML con los pedidos fabrica
	//no recibidos. Luego redirige a el recepPedidoFabrica.swf.
	
	HttpSession candela_sesion= request.getSession();
	Candela cand= (Candela) candela_sesion.getAttribute("candela");
	
	GeneradorXML gen=new GeneradorXML(cand);
	//Se generanlos pedidos fabrica marcados y sus facturas fabricas impagas
	gen.generarXMLPedidoFabrica();
	gen.generarXMLFacturasFabrica();
	response.sendRedirect("recepPedidoFabrica.jsp");
%>