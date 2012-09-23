<%@page import="javax.swing.JOptionPane"%>
<%@page import="java.sql.SQLException"%>
<%@page import="excepciones.TomoNoEncontradoException"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Asignar Producto a Tomo</title>
</head>
<body>

<%@page import= "negocio.*" %>
<%@page import="java.util.*" %>
<%@page import="utilidades.*" %>

<%@page session="true" %>


<% 

	//Segundo, se obtiene el codigo de tomo seleccionado en el formulario .swf.
	
	HttpSession candela_sesion= request.getSession();
	Candela candela=(Candela)candela_sesion.getAttribute("candela");

	String codTomo= request.getParameter("codigoTomo");
	String codProd= request.getParameter("codigoProducto");
	
	if(codTomo!=null && (!codTomo.equals(""))){
		int codigo_tomo= Integer.parseInt(codTomo);
		int codigo_prod= Integer.parseInt(codProd);
		System.out.println("Continuando asignando prods a tomo");
		
		// Algoritmo:Verificar que el tomo este en vigencia, en la coleccion de tomos de candela. Buscar el producto y obtener una referencia. Luego buscar el tomo obtener una referencia. 
		//Añadir a la coleccion de productos del tomo la referencia de producto.
		//Llamar al metodo asociarProdTomo(codigoTomo,CodigoProd) para hacer la asignacion en la BD.
		try{
			candela.getCatalogoVigente().AsignarProdTomo(codigo_prod, codigo_tomo,candela.getColProductos(),candela.esCatalogoNuevo(),"");
			JOptionPane panel = new JOptionPane();
			panel.showMessageDialog(null, "Producto asignado correctamente al tomo");
			GeneradorXML xml= new GeneradorXML(candela);
			xml.generarProductosNoAsociados();
			xml.generarTomosVigentes();
			response.sendRedirect("../vistaOpDatos.swf");
		}catch(TomoNoEncontradoException tne){
			tne.printStackTrace();
			tne.mensajeDialogo("Error, el tomo no se encontro en la coleccion de tomos del catalogo Vigente");	
		//Si no encontre el tomo redirijo a pagina de error
 		response.sendRedirect("../vistaOpDatos.swf");
		}catch(SQLException sql){
				sql.printStackTrace();
				response.sendRedirect("../Error-O.swf");
		}
		// Sino ocurrieron excepciones, se muestra mensaje de Exito.
			
	}
%>