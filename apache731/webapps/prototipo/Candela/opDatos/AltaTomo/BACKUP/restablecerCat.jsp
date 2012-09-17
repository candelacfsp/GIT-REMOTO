<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<%@page import="negocio.Candela" %>
<%@page import="negocio.Catalogo" %>
<%@page session="true" %>
<%
	//Utilizo este .jsp para restablecer la referencia del catalogoVigente con el catalogoActual
	// si un usuario cancela alta de tomo o AsociarProducto a Tomo
 
	HttpSession candela_sesion= request.getSession();
	Candela cand=(Candela)candela_sesion.getAttribute("candela");
	
	if(cand.esCatalogoNuevo()==true){
		//Se reestablece la referenica de CatalogoNuevo a null.
		cand.setCatalogoNuevo(null);
		%>
			<jsp:forward page="../AltaCatalogo/formAltaCatalogo.jsp"/>
		<%
	}else{
		%>
			<jsp:forward page="formAltaTomo.jsp"/>
		<%
		
	}
%>
	

<body>

</body>
</html>