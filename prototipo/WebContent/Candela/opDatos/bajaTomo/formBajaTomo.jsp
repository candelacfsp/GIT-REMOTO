<%@page import="excepciones.TomoNoEncontradoException"%>
<%@page import="javax.swing.JOptionPane"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Baja de Tomo</title>
</head> 
    
    
<%@page session="true" %>
<%@page import="negocio.*" %>   
<%@page import="utilidades.*" %> 
<%@page import="java.util.*" %>
<%@page import="excepciones.*" %>
<%@page import="utilidades.GeneradorXML" %>

<%

	HttpSession candela_sesion= request.getSession();
	Candela candela=(Candela)candela_sesion.getAttribute("candela");
	String codTomo=request.getParameter("codigoTomo");
	if(codTomo!=null && (!codTomo.equals(""))){
		int codigoTomo= Integer.parseInt(codTomo); 
		//Nota: para borrar el Tomo, debo setearle a cada uno de los productos de la tabla ProductoBD
		// null, como tomo, y luego borrar el tomoBD en si.
		
		//1.Leer los tomos de memoria, encontrar el tomo y borrar el tomo  de la coleccion de tomos de Catalogo
		
		//TODO : La baja de Tomo, desasocia los productos de un tomo, y luego elimina solamente el tomo; Los productos quedan
		// guardados en la BD, sin estar asignados a un tomo especifico.
		
		try{
			Catalogo catalogo= candela.getCatalogoVigente();
			catalogo.bajaTomo(codigoTomo);
			//Si no se lanzo ninguna excepcion, se redirige al HTML de exito!
			
		}catch(SQLException sql){
			
			System.out.println("Ocurrio un error al manipular los tomos con la BD! ");
			sql.printStackTrace();
			response.sendRedirect("../Error-O.jsp");
		
		}catch(TomoNoExisteExcepcion tne){
			//tne.mensajeDialogo("No se encuentra registrado el tomo en el Sistema");
			candela_sesion.setAttribute("mensaje", "No se encuentra registrado el tomo en el Sistema");
			response.sendRedirect("bajaTomo.jsp");
		}
		if (!response.isCommitted()){
				//Despues de dar de baja un tomo en el sistema,se llama a generarTomosXML()
				//que actualiza el XML que contiene los tomos actuales y desde donde lee AS2.
				GeneradorXML gen=new GeneradorXML(candela);
				gen.generarXMLTomos();
				gen.generarProductosNoAsociados();
				gen.generarTomosVigentes();
				response.sendRedirect("tomoDadoBaja.jsp");
		}
		
	}
%>    
