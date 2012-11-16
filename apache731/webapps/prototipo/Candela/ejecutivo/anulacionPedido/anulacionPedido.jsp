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
			//JOptionPane panel = new JOptionPane();
			//panel.showMessageDialog(null, "Error de base de datos");
			response.sendRedirect("../Error-E.jsp");
		} catch (FacturaVencidaExcepcion vencida) {
			
			candela_sesion.setAttribute("mensaje", "La factura se encuentra vencida, imposible anular.");
			response.sendRedirect("anulacionPedidoEmbed.jsp");
			//VERSION VIEJA
			//vencida.mensajeDialogo("La factura se encuentra vencida, imposible anular");
			//response.sendRedirect("../vistaEjecutivo.jsp");

		} catch (FacturaPagadaExcepcion pagada) {
			candela_sesion.setAttribute("mensaje", "La factura se encuentra paga, imposible anular.");
			response.sendRedirect("anulacionPedidoEmbed.jsp");
			
			//pagada.mensajeDialogo("La factura se encuentra paga, imposible anular");
			//response.sendRedirect("../vistaEjecutivo.jsp");
			
		} catch (ProductoNoExisteExcepcion noExiste) {
			candela_sesion.setAttribute("mensaje", "Error en la actualizacion de stock de los productos, el producto a actualizar no existe");
			response.sendRedirect("anulacionPedidoEmbed.jsp");
			
			/*noExiste.mensajeDialogo("Error en actualizacion de stock de los productos, el producto a actualizar no existe!!!");
			response.sendRedirect("../vistaEjecutivo.jsp");*/
		}
		if (!response.isCommitted()){
			GeneradorXML xml = new GeneradorXML(candela);
			xml.generarXMLUsuarios();
			response.sendRedirect("../vistaEjecutivo.jsp");
		}
		
	}
%>