<%@page import="excepciones.ProductoNoExisteExcepcion"%>
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
	String precio = request.getParameter("precio");
	String cantidad = request.getParameter("cantidad");

	if (codigo != null) {
		HttpSession candela_sesion = request.getSession();
		Candela candela = (Candela) candela_sesion
				.getAttribute("candela");
		candela.iniciar();
	try{
		candela.modDeProducto(Integer.parseInt(codigo),
				Double.parseDouble(precio), Integer.parseInt(cantidad));
	}catch(ProductoNoExisteExcepcion prod){
			prod.mensajeDialogo("Error, el producto no existe, imposible modificar un producto");
			response.sendRedirect("../Error-O.swf");
	}
		if (!response.isCommitted()){
		GeneradorXML xml = new GeneradorXML(candela);
		xml.generarXMLProductos();
		response.sendRedirect("../exito-O.swf");
		}
	}
%>