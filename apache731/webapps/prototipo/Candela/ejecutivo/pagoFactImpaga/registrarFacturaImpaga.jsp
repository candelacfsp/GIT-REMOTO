<%@page import="javax.swing.JOptionPane"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page session="true" %>
<%@page import="negocio.*" %>
<%@page import="persistencia.*" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<%
	//RegistrarFacturaImpaga.jsp, se encarga de buscar en la coleccion de FacturasPersonalImpagasBD la el nro de factura ingresado,
	// de registrar el cambio en la Base de Datos y de marcar la facturaPersonal en la coleccion que posee candela.
	//Luego redirige a facturaPagadaOK.jsp, que emite el comprobante de pago en formato HTML.
	
	String nroFactImp=request.getParameter("nroFacturaImpaga");
	
	
	if(nroFactImp!=null && (!nroFactImp.equals(""))){
		
		HttpSession candela_sesion= request.getSession();
		Candela candela=(Candela)candela_sesion.getAttribute("candela"); 
		//Obtener la factura Impaga y marcarla como paga en la BD
		//NOTA: Agregar un combobox, para que se pueda seleccionar una factura a pagar en MostrarFacturaImpaga.jsp
		
		
		
	 	int nroFactImpaga=Integer.parseInt(nroFactImp);
		
	
		
		//Obtener la coleccion de facturas impagas del usuario.
		//String nroDni=(String)request.getAttribute("dni");
		
		
		//int dni=Integer.valueOf(nroDni);
		
		//Se obtiene el numero de dni ingresado en la session desde el JSP anterior.
		Integer nroDni=(Integer)candela_sesion.getAttribute("dni");
		int dni=nroDni.intValue();
		JOptionPane panel= new JOptionPane();
		
		int posUsuario=candela.verificarUsuario(dni);
		
		Usuario usr=candela.getcolUSRSOFTWARE().get(posUsuario);
		//Validar la factura Impaga ingresada
		
		int posFactImp=usr.verificarFacturaImpaga(nroFactImpaga); 
		
		
		ArrayList<FacturaPersonal> factsImpagas=null;
			factsImpagas=usr.obtenerFactImpagas();
		if(factsImpagas!=null){//Si la factura impaga ingresada es valida.
	
			//Se guarda la posicion de la factura que se pago (del arreglo de facturas impagas del usuario)
			
			candela_sesion.setAttribute("nroFactImp",new Integer(nroFactImpaga));
			
			//Se redirecciona a un archivo.jsp que formatea la emision de un comprobante
			response.sendRedirect("marcarFactImp.jsp");
		}else{
				//panel.showMessageDialog(null, "La factura es inv�lida...");
				candela_sesion.setAttribute("mensaje","La factura es inv�lida");
				response.sendRedirect("mostrarFacturaImpaga.jsp");
		}
	if (!response.isCommitted()){
		response.sendRedirect("../vistaEjecutivo-factura.jsp");
	}
	}
%>