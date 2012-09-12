
<%@page import="javax.swing.JOptionPane"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@page import="net.java.ao.Entity"%>
<%@page import="net.java.ao.EntityManager"%>
<%@page import="java.awt.Desktop"%>
<%@page import="utilidades.*"%>
<%@page import="persistencia.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@page import="net.java.ao.Entity"%>
<%@page import="net.java.ao.EntityManager"%>
<%@page import="java.awt.Desktop"%>
<%@page import="utilidades.*"%>
<%@page import="persistencia.*"%>
<%@page buffer="NONE"%>
<%@page import="java.net.URI"%>
<%@include file="GlobalVars.jsp"%>

<%@page import="java.net.URI"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="negocio.*"%>

<%@page session="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">


<%
	String nombre = request.getParameter("usuario");
	String contrasenia = request.getParameter("contrasenia");
	String directorio= getServletContext().getRealPath("/");
//Esto lo realice para obtener el path
	
	
	if ((nombre != null && contrasenia != null)
			&& (!nombre.equals("") && !contrasenia.equals(""))) {
		try {
			candela.setDirectorio(directorio+"Candela/"); //TODO DAMIAN 11-9-12 agregue
			candela.iniciar();
		} catch (SQLException e) {
			JOptionPane panel = new JOptionPane();
			panel.showMessageDialog(null,
					"Error de base de datos, imposible iniciar");
			response.sendRedirect("Index.swf");
		} catch (IllegalStateException e1) {
			JOptionPane panel = new JOptionPane();
			panel.showMessageDialog(null,
					"Error al iniciar el sistema... vuelva a internarlo");
			response.sendRedirect("Index.swf");
		}
		if (!response.isCommitted()) {
			HttpSession candela_sesion = request.getSession();
			candela_sesion.setAttribute("candela", candela);
			//Guardo en la sesion un arraylist de coldetalles para utilizarlo en venta en local
			ArrayList<DetallePedidoPersonal> colDetalles = new ArrayList<DetallePedidoPersonal>();
			candela_sesion.setAttribute("colDetalles", colDetalles);

			//Para discriminar entre el catalogo nuevo y el catalogo vigente, siempre se leera el catalogo de la sesion
			//asi si un metodo tiene que crear un nuevo catalogo, solamente sobreescribe el catalogo en la sesion con uno nuevo
			//y todos los demas metodos obtienen el cataloog de ahí

			candela_sesion.setAttribute("catalogoNuevo",
					candela.getCatalogoVigente());
			//candela_sesion.setMaxInactiveInterval(60*60*24*31);
			//Sesion de Candela que sera usada por todas las paginas JSP que la necesiten. Se
			//empleara la sesion como un contenedor para el objeto Candela, para que sea accedida
			// como una variable global.

			ArrayList<usuarioBD> usuarios = null;
			usuarios = candela.getColUsuarios();
			if (usuarios != null) {
				boolean encontrado = false;
				for (int i = 0; i < usuarios.size(); i++) {
					if ((usuarios.get(i).getNombreUsr().equals(nombre))
							&& (usuarios.get(i).getContrasenia()
									.equals(contrasenia))) {
						//si encuentro el usuario
						encontrado = true;
						switch (usuarios.get(i).getTipoDeUsrBD()
								.getID()) {

						case Constantes.VENDEDOR:
							response.sendRedirect("vendedor/vistaVendedor.swf");
							break;
						case Constantes.DIRECTOR:
							response.sendRedirect("director/vistaDirector.swf");
							break;
						case Constantes.EJECUTIVO:
							response.sendRedirect("ejecutivo/vistaEjecutivo.swf");
							break;
						case Constantes.OPERADORDEDATOS:
							response.sendRedirect("opDatos/vistaOpDatos.swf");
							break;
						}

					}

				}

				if (!encontrado) {

					response.sendRedirect("errorUsuario.swf");
				}
			}

		}
	}
%>
