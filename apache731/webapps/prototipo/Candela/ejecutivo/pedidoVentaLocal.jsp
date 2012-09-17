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
	String userName = request.getParameter("userName");
	String codigo = request.getParameter("codigo");
	String cantidadStock = request.getParameter("cantidad");
	String estado = request.getParameter("estado");
	String talle = request.getParameter("talle");
	String color = request.getParameter("color");

	if (userName != null && codigo != null) {
		HttpSession candela_sesion = request.getSession();
		Candela candela = (Candela) candela_sesion
				.getAttribute("candela");
		if (estado.equals("si")) {
			for (int i = 0; i < candela.getColProductos().size(); i++) {
				//busco el producto que me pasaron por parametros
				if (candela.getColProductos().get(i).getCodigo() == Integer
						.parseInt(codigo)) {
					//si lo encuentro creo un producto con los datos
					Producto producto = candela.getColProductos()
							.get(i);
					producto.setCantidadEnStock(Integer
							.parseInt(cantidadStock));
					//creo el detalle almacenandolo en el array de candela
					ArrayList<DetallePedidoPersonal> colDetalles = (ArrayList<DetallePedidoPersonal>) candela_sesion
							.getAttribute("colDetalles");

					DetallePedidoPersonal detalle = new DetallePedidoPersonal(
							candela.getEm(), producto);
					if (color != null && talle != null) {
						detalle.setColor(color);
						detalle.setTalle(talle);

						colDetalles.add(detalle);
					}
					response.sendRedirect("pedidoVentaLocal.swf");

				}
			}
		} else {
			if (((ArrayList<DetallePedidoPersonal>) candela_sesion
					.getAttribute("colDetalles")) != null) {
				//si la coleccion existe
				ArrayList<DetallePedidoPersonal> colDetalles = (ArrayList<DetallePedidoPersonal>) candela_sesion
						.getAttribute("colDetalles");
				for (int i = 0; i < candela.getColUsuarios().size(); i++) {
					if (candela.getColUsuarios().get(i).getNombreUsr()
							.equals(userName)) {
						//Creo el producto seleccionado
						Producto producto = candela.getColProductos()
								.get(i);

						//creo el detalle almacenandolo en el array de candela

						DetallePedidoPersonal detalle = new DetallePedidoPersonal(
								candela.getEm(), producto);
						if (color != null && talle != null) {
							detalle.setColor(color);
							detalle.setTalle(talle);
							detalle.setCantidad(Integer
									.parseInt(cantidadStock));

							colDetalles.add(detalle);
						}
						try {
							candela.ventaEnLocal(candela
									.getColUsuarios().get(i),
									colDetalles);
						} catch (SQLException sql) {
							JOptionPane panel = new JOptionPane();
							panel.showMessageDialog(null,
									"Error de base de datos");
						}
						response.sendRedirect("vistaEjecutivo.swf");
					}
					break;//corto la iteracion

				}

			}

		}
	}
%>