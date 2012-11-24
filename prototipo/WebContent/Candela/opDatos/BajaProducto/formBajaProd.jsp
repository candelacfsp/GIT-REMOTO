<%@page import="excepciones.ProductoNoExisteExcepcion"%>
<%@page import="excepciones.TomoAsignadoExcepcion"%>
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
<html xmlns="http://www.w3.org/1999/xhtml">

<%
	String codigo = request.getParameter("codigo");
String opcion= request.getParameter("opcion");
	if (codigo != null) {
		HttpSession candela_sesion = request.getSession();
		Candela candela = (Candela) candela_sesion
				.getAttribute("candela");
		try {
			candela.bajaDeProducto(Integer.parseInt(codigo));
		} catch (SQLException s) {
			s.printStackTrace();
			response.sendRedirect("../Error-O.jsp");
		} catch (TomoAsignadoExcepcion tomo) {
			//tomo.mensajeDialogo("Error, imposible dar de baja, el producto se encuentra asignado a un tomo");
			candela_sesion.setAttribute("mensaje", "Error, imposible dar de baja, el producto se encuentra asignado a un tomo");
			response.sendRedirect("bajaProducto.jsp");
		} catch (ProductoNoExisteExcepcion prod) {
			prod.mensajeDialogo("Error el producto no existe, imposible dar de baja");
			candela_sesion.setAttribute("mensaje", "Error el producto no existe, imposible dar de baja");
			response.sendRedirect("bajaProducto.jsp");
		}
		if (!response.isCommitted()) {
			GeneradorXML xml = new GeneradorXML(candela);
			xml.generarProductosNoAsociados(0);
			xml.generarXMLProductos(500);
			
			if (opcion.equals("si")) {
				response.sendRedirect("bajaProducto.jsp");
			} else {
				response.sendRedirect("../vistaOpDatos-producto.jsp");
			}

		}
	}
%>