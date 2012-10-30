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
	String URL=reps.crearRankingVend(candela);
	if(URL==null){//Si no hay al menos un vendedor en la colecciond e vendedores de Candela que tenga una factura asociada
		response.sendRedirect("noExistenVendFacturados.jsp");
		
	}else{
		response.sendRedirect("../../reporteRankingVend.pdf");
	}
%>

</body>
</html>