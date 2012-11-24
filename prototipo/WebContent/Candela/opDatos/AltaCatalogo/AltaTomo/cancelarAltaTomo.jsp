<%@page import="negocio.Candela"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@page session="true" %>
<%
	/* 
		cancelarAltaTomo.jsp debe setear la referencia del catalogNuevo de candela a
		null.
	*/
	
	//Cabecera jsp para evitar el cacheo del navegador
	response.setHeader("Cache-Control",
			"no-cache, no-store, must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
	
	//Se setea la referenica del catalogo nuevo a null.
	HttpSession candela_sesion= request.getSession();
	Candela cand= (Candela)candela_sesion.getAttribute("candela");
	if(cand!=null){
		cand.setCatalogoNuevo(null);
	}
	response.sendRedirect("../../vistaOpDatos-catalogo.jsp");
%>

</body>
</html>