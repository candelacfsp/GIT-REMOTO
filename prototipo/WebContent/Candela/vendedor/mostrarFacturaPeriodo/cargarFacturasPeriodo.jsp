<%@page import="java.text.SimpleDateFormat"%>
<%@page import="excepciones.ProductoNoExisteExcepcion"%>
<%@page import="javax.swing.JOptionPane"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="java.sql.SQLException"%>
<%@page import="net.java.ao.Entity"%>
<%@page import="net.java.ao.EntityManager"%>
<%@page import="utilidades.*"%>
<%@page import="persistencia.*"%>
<%@page import="negocio.*"%>
<%@page session="true"%>



<%
	String inicio = request.getParameter("inicio");
	String fin = request.getParameter("fin");

	if (inicio != null && fin != null) {
		HttpSession candela_sesion = request.getSession();
		Candela candela = (Candela) candela_sesion
				.getAttribute("candela");
		String nombreUsuario = (String) candela_sesion
				.getAttribute("nombreUsr");
		ArrayList<Usuario> usuarios = candela.getcolUSRSOFTWARE();

		for (int i = 1; i < usuarios.size(); i++) {
			if (usuarios.get(i).getNombreUsuario()
					.equals(nombreUsuario)) {
				int dni = usuarios.get(i).getDni();
				String apellido = usuarios.get(i).getApellido();
				String nombre = usuarios.get(i).getNombre();
				Reportes reporte = new Reportes();

				SimpleDateFormat formato = new SimpleDateFormat(
						"yyyy-MM-dd");
				Date fechaInicio = null;
				Date fechaFin = null;
				fechaInicio = formato.parse(inicio);
				fechaFin = formato.parse(fin);

				reporte.CrearPDFFacturaPeriodo(candela, fechaInicio,
						fechaFin, dni, nombre, apellido);
				response.sendRedirect("../../FacturasPeriodo.pdf");
			}
		}
	}
%>


