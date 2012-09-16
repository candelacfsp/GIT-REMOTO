<%@page import="excepciones.ProductoNoExisteExcepcion"%>
<%@page import="excepciones.TomoAsignadoExcepcion"%>
<%@page import="javax.swing.JOptionPane"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@page import="net.java.ao.Entity"%>
<%@page import="net.java.ao.EntityManager"%>
<%@page import="java.awt.Desktop"%>
<%@page import="utilidades.*"%>
<%@page import="persistencia.*"%>
<%@page import="negocio.*"%>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<%
	String codigo = request.getParameter("codigo");

	if (codigo != null) {
		HttpSession candela_sesion = request.getSession();
		Candela candela = (Candela) candela_sesion
				.getAttribute("candela");
		try {
			candela.bajaDeProducto(Integer.parseInt(codigo));
		} catch (SQLException s) {

			response.sendRedirect("../Error-O.swf");
		} catch (TomoAsignadoExcepcion tomo) {
			tomo.mensajeDialogo("Error, imposible dar de baja, el producto se encuentra asignado a un tomo");
			response.sendRedirect("bajaProducto.swf");
		} catch (ProductoNoExisteExcepcion prod) {
			prod.mensajeDialogo("Error el producto no existe, imposible dar de baja");
			response.sendRedirect("bajaProducto.swf");
		}
		if (!response.isCommitted()) {
			GeneradorXML xml = new GeneradorXML(candela);
			xml.generarProductosNoAsociados();
			JOptionPane panel2= new JOptionPane();
			int opc = panel2.showConfirmDialog(null,
					"¿Desea volver a dar de baja otro producto?",
					"Baja de Producto", JOptionPane.YES_NO_OPTION);
			if (opc == JOptionPane.YES_OPTION) {
				response.sendRedirect("bajaProducto.swf");
			} else {
				response.sendRedirect("../vistaOpDatos.swf");
			}

		}
	}
%>