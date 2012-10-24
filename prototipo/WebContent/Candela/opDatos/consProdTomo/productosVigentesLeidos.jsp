<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%@page import="negocio.*" %>    
<%@page session="true" %>    
<%
	HttpSession sesion_candela= request.getSession();
	ArrayList<Producto> productosCons= (ArrayList<Producto>) sesion_candela.getAttribute("productosConsultadosTomo");
	PrintWriter pw= response.getWriter();
	pw.println("<h1> Productos Vigentes en el  Tomo 2 </h1>");
	for(Producto prod: productosCons){
		pw.println(" Codigo de Producto: "+prod.getCodigo()+"<br>");
		pw.println("Descripcion del Producto: "+prod.getDescripcion()+"<br>"); 
		pw.println("---------------------------------------------------<br>");
 		
	}
%>
    
	
		<h3> <a href="formConsProdTom.jsp"><b>Retornar</b></a></h3>
</body>
</html>