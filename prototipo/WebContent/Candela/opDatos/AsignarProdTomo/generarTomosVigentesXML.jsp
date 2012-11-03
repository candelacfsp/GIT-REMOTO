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
	/* generarTomosVigentes.jsp llama a generador XML para generar los tomos vigentes qeu se cargaran
	en el comboBox de tomo, en el CU desasignarProdTomo.*/

	HttpSession candela_sesion=request.getSession();
	Candela cand=(Candela) candela_sesion.getAttribute("candela");
	
	GeneradorXML gen= new GeneradorXML(cand);
	gen.generarTomosVigentes();

%>

</body>
</html>