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
							candela.getConexion(), producto);
					if (color != null && talle != null) {
						detalle.setColor(color);
						detalle.setTalle(talle);
						detalle.setCantidad(Integer.parseInt(cantidadStock));

						colDetalles.add(detalle);
					}
					candela.actualizarColProdConDetalle(colDetalles);
					response.sendRedirect("pedidoVentaLocal.jsp");

				}
			}
		} else {//si selecciono que no!
			if (!response.isCommitted()) {//pregunto si ya se desplego en otra pagina
				//referencio a la coleccion de detalles
				ArrayList<DetallePedidoPersonal> colDetalles = (ArrayList<DetallePedidoPersonal>) candela_sesion
						.getAttribute("colDetalles");
				if (colDetalles.size()== 0) {//si es null la creo
					colDetalles = new ArrayList<DetallePedidoPersonal>();
				}
				for (int i = 0; i < candela.getColUsuarios().size(); i++) {
					//busco al usuario y si lo encuentro
					if (candela.getColUsuarios().get(i).getNombreUsr()
							.equals(userName)) {
						//busco el producto con el codigo proporcionado
						for (int j = 0; j < candela.getColProductos()
								.size(); j++) {
							System.out.println("candela:"
									+ candela.getColProductos().get(j)
											.getCodigo() + "codigo:"
									+ codigo);
							if (candela.getColProductos().get(j)
									.getCodigo() == Integer
									.parseInt(codigo)) {
								//Creo el producto seleccionado
								Producto producto = candela
										.getColProductos().get(j);

								//creo el detalle almacenandolo en el array de candela siempre y cuando cumpla
								//con que la cantidad solicitada es menor
								if (Integer.parseInt(cantidadStock) <= producto
										.getCantidadEnStock()) {

									DetallePedidoPersonal detalle = new DetallePedidoPersonal(
											candela.getConexion(), producto);
									if (color != null && talle != null) {
										detalle.setColor(color);
										detalle.setTalle(talle);

										detalle.setCantidad(Integer
												.parseInt(cantidadStock));

										colDetalles.add(detalle);
									}//guardo el detalle y lo almaceno en la coleccion de detalles
								 //salgo del if de color y talle
								}
							}//aca termina el if de si encuentro el producto o no
						}// termino de armar el paquete de colecciones de detalle ciclo for
						try {
							if (colDetalles.size() > 0){
							candela.actualizarColProdConDetalle(colDetalles);
							candela.ventaEnLocal(candela
									.getColUsuarios().get(i),
									colDetalles);
							//si todo va bien redirijo a vista ejecutivo
							//debería vaciar colDetalles
							colDetalles.clear();
							}
							response.sendRedirect("vistaEjecutivo-stock.jsp");
							
						} catch (SQLException sql) {
							sql.printStackTrace();
							response.sendRedirect("Error-E.jsp");
						} //fin del catch

					}// fin del si encuentro el usuario
				}// fin del for que busca un usuario
			}// fin del if de commit

		} // fin del else, si eligió el usuario NO

	} //si los parametros vienen con null
%>