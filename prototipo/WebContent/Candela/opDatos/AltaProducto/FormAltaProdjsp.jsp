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

	if ((codigo != null) && (precio != null) && (cantidad != null)
			&& (descripcion != null)) {

		HttpSession candela_sesion = request.getSession();
		Candela candela = (Candela) candela_sesion
				.getAttribute("candela");
		JOptionPane panel = new JOptionPane();
		try {
			candela.altaDeProducto(Integer.parseInt(codigo),
					descripcion, Double.parseDouble(precio),
					Integer.parseInt(cantidad));
		} catch (SQLException s) {

			response.sendRedirect("../Error-O.jsp");
		} catch (ProductoExisteExcepcion p) {
			panel.showMessageDialog(null,
					"Error: el producto existe! imposible dar alta");
			response.sendRedirect("altaProducto.swf");

		}
		if (!response.isCommitted()) {
			//Debo llamar al generador de XML
			GeneradorXML xml = new GeneradorXML(candela);
			xml.generarXMLProductos();
			xml.generarProductosNoAsociados();
			JOptionPane panel2 = new JOptionPane();
			int opc = panel2.showConfirmDialog(null, "¿Desea volver a cargar otro producto?",
					"Carga de Producto", JOptionPane.YES_NO_OPTION);
			if (opc == JOptionPane.YES_OPTION) {
				response.sendRedirect("altaProducto.jsp");
			} else {
				response.sendRedirect("../vistaOpDatos-producto.jsp");
			}

		}
	}
%>