<%@page import="excepciones.ProductoNoExisteExcepcion"%>
<%@page import="excepciones.FacturaVencidaExcepcion"%>
<%@page import="excepciones.FacturaPagadaExcepcion"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="javax.swing.JOptionPane"%>
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
<%
	String nroPedido = request.getParameter("nroPedido");
	String userName = request.getParameter("userName");

	if (userName != null && nroPedido != null) {
		HttpSession candela_sesion = request.getSession();
		Candela candela = (Candela) candela_sesion
				.getAttribute("candela");

		try {
			candela.anulacionPedido(Integer.parseInt(nroPedido),

			userName);
			
		} catch (SQLException sql) {
			JOptionPane panel = new JOptionPane();
			panel.showMessageDialog(null, "Error de base de datos");
			response.sendRedirect("../Error-E.swf");
		} catch (FacturaVencidaExcepcion vencida) {
			vencida.mensajeDialogo("La factura se encuentra vencida, imposible anular");
			response.sendRedirect("../vistaEjecutivo.swf");

		} catch (FacturaPagadaExcepcion pagada) {
			pagada.mensajeDialogo("La factura se encuentra paga, imposible anular");
			response.sendRedirect("../vistaEjecutivo.swf");
		} catch (ProductoNoExisteExcepcion noExiste) {
			noExiste.mensajeDialogo("Error en actualizacion de stock de los productos, el producto a actualizar no existe!!!");
			response.sendRedirect("../vistaEjecutivo.swf");
		}
		if (!response.isCommitted()){
			GeneradorXML xml = new GeneradorXML(candela);
			xml.generarXMLUsuarios();
			response.sendRedirect("../vistaEjecutivo.swf");
		}
		
	}
%>