<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<%@page import="negocio.Candela" %>
<%@page import="utilidades.Reportes" %>
<%@page session="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<% Reportes reps= new Reportes();
	HttpSession candela_sesion = request.getSession(false);
	Candela candela=(Candela)candela_sesion.getAttribute("candela");
	reps.crearRankingVend(candela);
	response.sendRedirect(candela.getDirectorio()+"reporteRanking.pdf");
%>

</body>
</html>