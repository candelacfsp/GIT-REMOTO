<%@page import="excepciones.ProductoExisteExcepcion"%>
<%@page import="javax.swing.JOptionPane"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@page import="net.java.ao.Entity"%>
<%@page import="net.java.ao.EntityManager"%>
<%@page import="utilidades.*"%>
<%@page import="persistencia.*"%>
<%@page import="negocio.*"%>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<%
	String codigo = request.getParameter("codigo");
	String cantidad = request.getParameter("cantidad");
	String precio = request.getParameter("precio");
	String descripcion = request.getParameter("descripcion");
	String mensajeEstado= request.getParameter("mensaje");

	HttpSession candela_sesion = request.getSession();
	Candela candela = (Candela) candela_sesion
			.getAttribute("candela");
	if (mensajeEstado == null){
	if ((codigo != null) && (precio != null) && (cantidad != null)
			&& (descripcion != null)) {

		try {
			candela.altaDeProducto(Integer.parseInt(codigo),
					descripcion, Double.parseDouble(precio),
					Integer.parseInt(cantidad));

		} catch (SQLException s) {
			s.printStackTrace();
			response.sendRedirect("../Error-O.jsp");
			candela_sesion.setAttribute("opcion", null);
		} catch (ProductoExisteExcepcion p) {

			candela_sesion.setAttribute("mensaje",
					"Error: el producto existe! imposible dar alta");
			candela_sesion.setAttribute("descripcionProducto", descripcion);
			candela_sesion.setAttribute("precioProducto", precio);
			candela_sesion.setAttribute("cantidadProducto",cantidad);
			response.sendRedirect("altaProducto.jsp");

		} catch (NumberFormatException formato) {

			candela_sesion
					.setAttribute("mensaje",
							"El formato del precio es inapropiado, la plantilla es: ENTERO.DECIMAL");
			
			response.sendRedirect("altaProducto.jsp");
		}
		if (!response.isCommitted()) {
			//Debo llamar al generador de XML
			GeneradorXML xml = new GeneradorXML(candela);
			xml.generarXMLProductos(0);
			xml.generarProductosNoAsociados(500);
			//envio de vuelta a alta pregunta para que se le pregunte al usuarios
			//por medio del swf
			candela_sesion.setAttribute("mensaje", "pregunta");
			response.sendRedirect("altaProducto.jsp");
		
		}
	
	}
	}else{
		//debo reenviar al alta producto dependiendo del si o no
		if (mensajeEstado.equals("si")){
			candela_sesion.setAttribute("mensaje", null);
			response.sendRedirect("altaProducto.jsp");
		}else{
			candela_sesion.setAttribute("mensaje", null);
			response.sendRedirect("../vistaOpDatos-producto.jsp");
		}
	}
%>