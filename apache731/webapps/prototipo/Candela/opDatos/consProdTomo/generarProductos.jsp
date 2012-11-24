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
<%@page import="utilidades.*" %>
<%
	/*generarProductos.jsp genera un xml con todos los productos de un tomo seleccionado en el
	comboBox*/
	
	HttpSession candela_sesion=request.getSession();
	Candela cand=(Candela)candela_sesion.getAttribute("candela");

	//Se pide el numero de tomo del que se tiene que generar el archivo XML  par alos productos.
	String numeroTomo= request.getParameter("nroTomo");
	int nroTomo= Integer.parseInt(numeroTomo);
	

	
	//Se llama al generador de XML para generar un xml con los productos asociados al tomo
	
	//NOTA1: SE TIENE QUE BUSCAR LA FORMA DE FORZAR AL FORM.SWF para que cargue XML DINAMICAMENTE,
	//sino un usuario seleciconara un tomo y se cargaran los productos, pero cuando seleccione otro
	//no se cargaran los productos de un XMl. REVISAR??
			
	//Se tiene que pasar al metodo DatosProdAsocTomo(), el numero de producto, para buscarlo en Candela
	// y obtener su tipo de prod.
	
	GeneradorXML gen=new GeneradorXML(cand);
	gen.generarDatosProdAsocTomo(nroTomo);
	
%>

</body>
</html>