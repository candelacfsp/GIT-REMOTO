<%@page import="javax.swing.JOptionPane"%>
<%@page import="java.text.ParseException"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="persistencia.*"%>
<%@page import="negocio.*"%>
<%@page import="java.util.*"%>
<%@page import="persistencia.PedidoFabricaBD"%>
<%@page session="true"%>
<%@page import="net.java.ao.EntityManager"%>
<%@page import="utilidades.Constantes"%>
<%@page import="java.text.ParsePosition"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Alta de Factura Impaga</title>
</head>
<!-- IngresarFactura.jsp se encarga de recibir los datos ingresados en el form formIngresoFactura.swf  -->
<%
	//NOTA: Como el numero de pedido se recibe de un combobox no es necesario validarlo.
	String tipofact = request.getParameter("tipoFactura");
	String fechafact = request.getParameter("fechaFactura");
	JOptionPane panel = new JOptionPane();

	if ((tipofact != null && (!tipofact.equals("")) && (fechafact != null && (!fechafact
			.equals(""))))) {
		HttpSession candela_sesion = request.getSession();
		Candela candela = (Candela) candela_sesion
				.getAttribute("candela");

		//Transforma a entero el numero de pedido a un valor entero
		String nPedido = request.getParameter("nroPedido");

		int nroPedido = Integer.parseInt(nPedido);

		//Recuperar la coleccion de pedidos no facturados de candela.
		ArrayList<PedidoFabrica> peds = candela
				.getPedidosFabricaNoFacturados();

		// Buscar la posicion del pedidoFabrica en la coleccion de Candela, que guardo la jsp anterior en la sesion.
		int posPedido = candela.verificarPedido(nroPedido);

		//Convertir la fechaIngresada en un formato valido
		Date fechaFactura = null;
		try {
			SimpleDateFormat fechas = new SimpleDateFormat("yyyy-MM-dd");
			//Date fechaFactura= fechas.parse(fechafact,new ParsePosition(0));
			fechaFactura = (Date) fechas.parse(fechafact);

			//Marcar el pedido ingresado como "Recibido" en memoria

			peds.get(posPedido)
					.marcarPedidoRecibido(
							peds.get(posPedido).getNumeroPedido(),
							fechaFactura);

			//Crear la facturaFabrica impaga de ese pedido en memoria
			int tipoFactura = 0;

			//Si el usuario ingresa un tipo de factura distinto de 'A' o 'a', se considera que necesita
			//una factura de tipo 'B'.

			if (tipofact.equals("A") || tipofact.equals("a")) {
				tipoFactura = 1;
			} else {
				tipoFactura = 2;
			}

			candela.agregarFacturaImpaga(peds.get(posPedido),
					tipoFactura, fechaFactura);
			
			candela_sesion.setAttribute("mensaje", "La factura se registró correctamente");
			response.sendRedirect("redirect.jsp");
		} catch (SQLException sql) {
			response.sendRedirect("../Error-E.jsp");
		} catch (ParseException pe) {
			
			candela_sesion.setAttribute("mensaje", "Error al introducir la fecha, debe ser del estilo año-mes-dia:aaaa-mm-dd");
			response.sendRedirect("../vistaEjecutivo-stock.jsp");
		}
	}
%>