<%@page import="excepciones.ProductoNoExisteExcepcion"%>
<%@page import="excepciones.TipoProductoExcepcion"%>
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
	String tipoproducto = request.getParameter("tipoproducto");

	if (codigo != null && tipoproducto != null) {
		HttpSession candela_sesion = request.getSession();
		Candela candela = (Candela) candela_sesion
				.getAttribute("candela");

		try {
			candela.asignarATipoDeProducto(
					Integer.parseInt(tipoproducto),
					Integer.parseInt(codigo));
		} catch (TipoProductoExcepcion tipo) {
			tipo.mensajeDialogo("El Tipo de producto no existe");
			
		} catch (ProductoNoExisteExcepcion prod) {
			prod.mensajeDialogo("El producto no existe");
			
		}
		if (!response.isCommitted()) {
			GeneradorXML xml = new GeneradorXML(candela);
			xml.generarXMLProductos();
			xml.generarXMLTipoDeProductos();
			response.sendRedirect("../vistaOpDatos-producto.jsp");
		}
	}
%>