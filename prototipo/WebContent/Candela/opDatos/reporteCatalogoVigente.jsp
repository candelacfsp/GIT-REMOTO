<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="javax.swing.JOptionPane"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@page import="net.java.ao.Entity"%>
<%@page import="net.java.ao.EntityManager"%>
<%@page import="utilidades.*"%>
<%@page import="persistencia.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@page import="net.java.ao.Entity"%>
<%@page import="net.java.ao.EntityManager"%>
<%@page import="utilidades.*"%>
<%@page import="persistencia.*"%>
<%@page buffer="NONE"%>
<%@page import="java.net.URI"%>


<%@page import="java.net.URI"%>


<%@page import="negocio.*"%>

<%@page session="true"%>
<%

HttpSession candela_sesion = request.getSession();
Candela candela = (Candela) candela_sesion
		.getAttribute("candela");
Reportes reportar = new Reportes();
reportar.crearPDFCatalogoVigente(candela);
response.sendRedirect("../catalogoVigente.pdf");
%>

