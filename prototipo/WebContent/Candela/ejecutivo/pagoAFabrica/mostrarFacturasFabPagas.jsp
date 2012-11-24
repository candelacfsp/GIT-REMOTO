<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@page session="true" %>
<%@page import="negocio.*" %>
<%@page import="persistencia.*" %>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%@page import="utilidades.GeneradorXML" %>
<%
	HttpSession candela_sesion= request.getSession();
	Candela candela=(Candela)candela_sesion.getAttribute("candela"); 
	
	
	//Se leen las facturas almacenadas en una coleccion dentro de la session de candela.
	//Al final se elimina la coleccion de la session.
	
	ArrayList<FacturaFabrica> facturas= (ArrayList<FacturaFabrica>)candela_sesion.getAttribute("FacturasFabricaPagadas");
	
	//Se muestran todos los detalles de las facturas fabrica pagadas con sus detalles de pedidos de personal.
	
		GeneradorXML gen1= new GeneradorXML(candela);
		gen1.generarFacturasFabricaPagadas(facturas,500);
	
	
		
		//Se borra la coleccion de facturas Pagas de la sesion de candela.
		candela_sesion.removeAttribute("FacturasPagasMostrar");
	
	

	
	//Cerrar el printWriter
	//pw.close();

%>			
</body>
</html>