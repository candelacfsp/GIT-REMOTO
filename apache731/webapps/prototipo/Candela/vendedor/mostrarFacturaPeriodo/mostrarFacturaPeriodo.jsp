<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="utilidades.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<%
	response.setHeader("Cache-Control",
			"no-cache, no-store, must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);

	HttpSession candela_sesion = request.getSession(false);
	Integer tipoUsr = (Integer) candela_sesion.getAttribute("tipoUsr");
	String mensaje=(String) candela_sesion.getAttribute("mensaje");

	if (tipoUsr == null) {
		response.sendRedirect("../../Index.swf");

	} else {
		int tipoUsuario = tipoUsr.intValue();
		//si no es un usuario autorizado a acceder a esta vista se lo redirige a Index.swf
		switch (tipoUsuario) {

		case Constantes.DIRECTOR:
			response.sendRedirect("../../director/vistaDirector.jsp");
			break;
		case Constantes.OPERADORDEDATOS:
			response.sendRedirect("../../opDatos/vistaOpDatos.jsp");
			break;
		case Constantes.EJECUTIVO:
			response.sendRedirect("../../ejecutivo/vistaEjecutivo.jsp");
			break;

		}
%>
<body>
	<center>
	<% if (mensaje!=null){ %>
		<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=10.0.0.0"
			width="800" height="600">
			<param name="movie" value="mostrarFacturaPeriodo.swf" />
			<param name="quality" value="high" />
			<embed src="mostrarFacturaPeriodo.swf?mensaje=<%=mensaje%>" quality="high"
				type="application/x-shockwave-flash" width="800" height="600"
				pluginspage="http://www.macromedia.com/go/getflashplayer"></embed>
		</object>
		<% candela_sesion.setAttribute("mensaje", null);
		}else{%>
		
			<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=10.0.0.0"
			width="800" height="600">
			<param name="movie" value="mostrarFacturaPeriodo.swf" />
			<param name="quality" value="high" />
			<embed src="mostrarFacturaPeriodo.swf" quality="high"
				type="application/x-shockwave-flash" width="800" height="600"
				pluginspage="http://www.macromedia.com/go/getflashplayer"></embed>
		</object>
		
		<%} %>
	</center>
</body>
</html>

<%
	}
%>
