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

HttpSession candela_sesion = request.getSession();
Candela candela = (Candela) candela_sesion
		.getAttribute("candela");
GeneradorXML xml = new GeneradorXML(candela);
xml.generarXMLVentas();
response.sendRedirect("estadistica/bin-debug/ventas.swf");

%>