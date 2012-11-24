<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="javax.swing.JOptionPane"%>
<%@page import="excepciones.TomoNoEncontradoException"%>
<%@page import="java.sql.SQLException"%>
<%@page import="excepciones.ProductoNoEncontradoException"%>
<%@page import="net.java.ao.EntityManager"%>
<%@page import="java.util.ArrayList"%>


<%@page import="negocio.*"%>
<%@page session="true"%>
<%@page import="java.sql.SQLException"%>
<%@page import="excepciones.*"%>

<%@page import="utilidades.*"%>
<%@page import="persistencia.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

	<%

	Candela candela = new Candela();
	candela.iniciar();
	GeneradorXML gen = new GeneradorXML(candela);
	String enviar=gen.pruebaCadenaXML();
	System.out.println("PESA:"+enviar.length());
	System.out.println("PESA2:"+enviar.getBytes().length);
	System.out.println("Buffer:"+response.getBufferSize());

	System.out.println("Buffer reseteado:"+response.getBufferSize());
	response.setBufferSize(13000*1024);
	System.out.println("Buffer2:"+response.getBufferSize());
	


	response.sendRedirect("pruebaCadenaXML.swf?enviar="+enviar);
	


%>
