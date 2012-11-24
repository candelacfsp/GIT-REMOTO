<%@page import="java.sql.SQLException"%>
<%@page import="utilidades.GeneradorXML"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<%@page import="negocio.Candela"%>
<%@page import="negocio.Catalogo"%>
<%@page session="true"%>
<%
	/*Utilizo cancelarAltaTomo.jsp para restablecer la referencia del catalogoVigente (con el verdadero catalogo no con el nuevo) 
	 si un usuario cancela CU "AsociarProducto a Tomo" */

	HttpSession candela_sesion = request.getSession();
	Candela cand = (Candela) candela_sesion.getAttribute("candela");
	boolean esCatNuevo=cand.esCatalogoNuevo();
	ArrayList<Integer> codigosProducto=(ArrayList<Integer>) candela_sesion.getAttribute("codigosProducto");
	GeneradorXML gen= new GeneradorXML(cand);
	
	
	//Se obtiene el codigo de tomo de la sesion de candela
	Integer codigoT= (Integer)candela_sesion.getAttribute("codigoTomo");
	int codigoTomo=codigoT.intValue();
	try{
		
		//Se desasocian los productos dados de alta en memoria y BD.
		cand.getCatalogoVigente().cancelarAltaTomo(codigosProducto,codigoTomo);
			
	}catch(SQLException sql){
		response.sendRedirect("../Error-O.jsp");
	}
	
	//Se elimina la coleccion de codigos de tomo en la sesion
	if(candela_sesion.getAttribute("codigosProducto")!=null){
		candela_sesion.removeAttribute("codigosProducto");
	}
	candela_sesion.removeAttribute("codigoTomo");
	candela_sesion.removeAttribute("descripTomo");
	
	//Se actualizan los XML que son leidos por los demas CU
	gen.generarProductosNoAsociados();
	//se actualiza el archivo xml que posee los tomos que que se pueden dar de baja
	gen.generarTomosVigentes();
	gen.generarXMLTomos();
	response.sendRedirect("../vistaOpDatos-catalogo.jsp");
	
	
%>


<body>

</body>
</html>