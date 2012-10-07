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
<%@page import="java.sql.SQLException" %>
<%@page session="true" %>


<%
	
	//marcarFactImp.jsp realiza el registro del pago de la factura en base de datos y en memoria.
	//Si esto se hiciera junto a registrarFacturaImpaga.jsp, entonces cunado facturaPagadaOK.jsp intentase
	//buscar y mostrar la factura impaga, se obtendria un error, ya que no se cuenta con facturas imapagas,
	//por que se hubieran marcado anteriormente.
	
	//Se marca la factura personal impaga como paga, tanto en BD como en memoria.
		HttpSession candela_sesion= request.getSession();
		Candela candela=(Candela)candela_sesion.getAttribute("candela"); 
		//Obtener la factura Impaga y marcarla como paga en la BD
		//NOTA: Agregar un combobox, para que se pueda seleccionar una factura a pagar en MostrarFacturaImpaga.jsp
		
	 	Integer nroFactImpaga1=(Integer)candela_sesion.getAttribute("nroFactImp");
		int  nroFactImpaga=nroFactImpaga1.intValue();
	
		
		//Se obtiene el numero de dni ingresado en la session desde el JSP anterior.
		Integer nroDni=(Integer)candela_sesion.getAttribute("dni");
		int dni=nroDni.intValue();
		
		int posUsuario=candela.verificarUsuario(dni);
		
		Usuario usr=candela.getcolUSRSOFTWARE().get(posUsuario);
		//Validar la factura Impaga ingresada
		
		int posFactImp=usr.verificarFacturaImpaga(nroFactImpaga); 
		
	
		try{
			usr.usrMarcarFactImp(posFactImp);
		}catch(SQLException sql){
			response.sendRedirect("../Error-E.jsp");
		}
		//Se borran las variables tamporales que se emplearon en la sesion
		candela_sesion.removeAttribute("nroFactImp");
		candela_sesion.removeAttribute("dni");
		response.sendRedirect("facturaPagadaOK.jsp");
%>
			