<%@page import="excepciones.ProductoNoExisteExcepcion"%>
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

	if (codigo != null) {

		HttpSession candela_sesion = request.getSession();
		Candela candela = (Candela) candela_sesion
				.getAttribute("candela");

		try {

			double precio;
			precio = candela.consPrecioProd(Integer.parseInt(codigo));
			JOptionPane panel = new JOptionPane();
			panel.showMessageDialog(null, "El precio del producto es:"
					+ precio);
		} catch (ProductoNoExisteExcepcion prod) {
			//prod.mensajeDialogo("Error: el producto no existe!, imposible consultar el precio");
			candela_sesion.setAttribute("mensaje", "Error: el producto no existe!, imposible consultar el precio");
			response.sendRedirect("consPrecioProd.jsp");
		}
		if (!response.isCommitted()){
		response.sendRedirect("../vistaVendedor-producto.jsp");
		}
	}
%>