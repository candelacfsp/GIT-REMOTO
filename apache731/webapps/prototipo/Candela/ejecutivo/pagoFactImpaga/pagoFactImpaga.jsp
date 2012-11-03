<%@page import="negocio.Candela"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="utilidades.*"%>
<%@page import="negocio.*" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<%
	response.setHeader("Cache-Control",
			"no-cache, no-store, must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);

	HttpSession candela_sesion = request.getSession(false);
	Integer tipoUsr = (Integer) candela_sesion.getAttribute("tipoUsr");
	String mensaje= (String) candela_sesion.getAttribute("mensaje");

	if (tipoUsr == null) {
		response.sendRedirect("../../Index.swf");

	} else {
		int tipoUsuario = tipoUsr.intValue();
		//si no es un usuario autorizado a acceder a esta vista se lo redirige a Index.swf
		switch (tipoUsuario) {

		case Constantes.VENDEDOR:
			response.sendRedirect("../../vendedor/vistaVendedor.jsp");
			break;
		case Constantes.OPERADORDEDATOS:
			response.sendRedirect("../../opDatos/vistaOpDatos.jsp");
			break;
		case Constantes.DIRECTOR:
			response.sendRedirect("../../director/vistaDirector.jsp");
			break;

		}
		
		//TODO RODRIGO se generan un XML con todos los dnis de los vendedores, llamado
		// dniVendDirect.xml
		Candela cand= (Candela)candela_sesion.getAttribute("candela");
		GeneradorXML genDnis= new GeneradorXML(cand);
		genDnis.generarDnisVendDir();
		
%>
<% 		if(mensaje!=null){ %>
<body>
	<center>
		<object align="middle"
			classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=10.0.0.0"
			width="800" height="600">
			<param name="movie" value="pagoFactImpaga.swf?mensaje=<%=mensaje%>" />
			<param name="quality" value="high" />
			<embed src="pagoFactImpaga.swf?mensaje=<%=mensaje%>" quality="high"
				type="application/x-shockwave-flash" width="800" height="600"
				pluginspage="http://www.macromedia.com/go/getflashplayer"></embed>
		</object>
	</center>
	
	<%
		candela_sesion.setAttribute("mensaje",null);
		
	}else{
	%>
		<center>
		<object align="middle"
			classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=10.0.0.0"
			width="800" height="600">
			<param name="movie" value="pagoFactImpaga.swf" />
			<param name="quality" value="high" />
			<embed src="pagoFactImpaga.swf" quality="high"
				type="application/x-shockwave-flash" width="800" height="600"
				pluginspage="http://www.macromedia.com/go/getflashplayer"></embed>
		</object>
	</center>
<%
			}
		}
%>
</body>
</html>