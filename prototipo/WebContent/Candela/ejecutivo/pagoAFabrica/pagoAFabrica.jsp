<%@page import="utilidades.GeneradorXML"%>
<%@page import="javax.swing.JOptionPane"%>
<%@page import="java.sql.SQLException"%>
<%@page import="net.java.ao.EntityManager"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page session="true" %>
<%@page import="negocio.*" %>
<%@page import="utilidades.Constantes" %>
<%@page import="persistencia.*" %>
<%@page import="java.util.ArrayList" %>

<%
	String nroFactura=request.getParameter("nroFactura");
	if(nroFactura!=null && (!nroFactura.equals("")) ){
	
	
		int numeroFactFab= Integer.parseInt(nroFactura);
		HttpSession candela_sesion= request.getSession();
		Candela candela=(Candela)candela_sesion.getAttribute("candela"); 
		//1.Buscar la facturaFabrica en la coleccion de facturas de Candela.
		int posFacturaFab=0;
		posFacturaFab=candela.verificarFacturaFabrica(numeroFactFab);
		//JOptionPane panel= new JOptionPane();
		
		if(posFacturaFab!=Constantes.ERROR){//Si la factura existe
			
			if(candela.getFacturasFabrica().get(posFacturaFab).esPagada()==false){
			
				//Se guarda cada una de las facturas seleccionadas por el usuario en la sesion de Candela,
				// que luego es tomada por un .jsp que muestra los elementos de la coleccion
				
			
				//Se crea una coleccion con las facturasFabrica pagadas en la sesion. Si el elemento no existe en la sesion,
				//entonces se crea.
				ArrayList<FacturaFabrica> facts=null;
				facts=(ArrayList<FacturaFabrica>)candela_sesion.getAttribute("FacturasFabricaPagadas");	
				if(facts==null){
					facts=new ArrayList<FacturaFabrica>();
					candela_sesion.setAttribute("FacturasFabricaPagadas",facts);			
				}
				facts.add(candela.getFacturasFabrica().get(posFacturaFab));
				try{
				
					candela.guardar_Facts_Fabrica(posFacturaFab);	
					
					
					//panel.showMessageDialog(null, "La factura a fábrica se ha registrado como paga correctamente");
					candela_sesion.setAttribute("mensaje", "La factura a fábrica se ha registrado como paga correctamente");
					
					GeneradorXML xml= new GeneradorXML(candela);
					xml.generarXMLFacturasFabrica();
					response.sendRedirect("pagoFabrica.jsp");
					
					//response.sendRedirect("../vistaEjecutivo.swf");
					
				
				}catch(SQLException sql){
					//Se vuelve a setear la factura Fabrica como Impaga
					candela.getFacturasFabrica().get(posFacturaFab).setPagada(false);
					sql.printStackTrace();
					response.sendRedirect("../Error-E.swf");
				}	
				
				
			
			}else{
				if (!response.isCommitted()){
					//panel.showMessageDialog(null, "La factura se encuentra pagada!");
					response.sendRedirect("../vistaEjecutivo.swf");
					candela_sesion.setAttribute("mensaje","La factura se encuentra pagada!");
					response.sendRedirect("../vistaEjecutivo-factura.jsp");
				}
			}
			
		}else{ //Si la factua no existe
			if (!response.isCommitted()){
			//	panel.showMessageDialog(null, "La factura seleccionada no existe!");
			//response.sendRedirect("../vistaEjecutivo.swf");
				candela_sesion.setAttribute("mensaje", "La factura seleccionada no existe!");
				response.sendRedirect("../vistaEjecutivo-factura.jsp");
			}
		}
	}
 %>
