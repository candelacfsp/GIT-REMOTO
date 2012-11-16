<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@page import="persistencia.*" %>
<%@page import="negocio.*" %>
<%@page import="java.util.*" %>
<%@page import="persistencia.PedidoFabricaBD" %>
 <%@page session="true" %>
 <%@page import="net.java.ao.EntityManager"%>
 <%@page import="utilidades.Constantes" %>
 <%@page import="java.text.ParsePosition"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*" %>
<%@page session="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Alta de Factura Impaga</title>
</head>
<%



	String tipofact=request.getParameter("tipoFactura");
	String fechafact=request.getParameter("fechaFactura");
	
	if(  (tipofact!=null && (!tipofact.equals("")) && (fechafact!=null && (!fechafact.equals("")) ) )){
		HttpSession candela_sesion= request.getSession();
		Candela candela=(Candela)candela_sesion.getAttribute("candela"); 
		
		//Transforma a entero el numero de pedido a un valor entero
		//String npedido=request.getParameter("nroPedido");
		Integer npedido=(Integer)request.getSession(false).getAttribute("nroPedido");
		int nroPedido=npedido.intValue();
		
		//Recuperar la coleccion de pedidos no facturados de candela.
		ArrayList<PedidoFabrica> peds= candela.getPedidosFabricaNoFacturados();
		
		// Buscar la posicion del pedidoFabrica en la coleccion de Candela, que guardo la jsp anterior en la sesion.
		int posPedido= candela.verificarPedido(nroPedido);
		
		//Marcar el pedido ingresado como "Recibido" en memoria
		
		try{
				peds.get(posPedido).marcarPedidoRecibido(peds.get(posPedido).getNumeroPedido(), new Date());
		//Modificación 9 - 9- 12
		}catch(SQLException sql){
			System.out.println("Error no se encontro el pedido en la BD");
			sql.printStackTrace();
			%>
				<jsp:forward page="errorFacturaBD.html"/>
			<%
		}
		
		//Crear la facturaFabrica impaga de ese pedido en memoria
		int tipoFactura=0;
		
		//Si el usuario ingresa un tipo de factura distinto de 'A' o 'a', se considera que necesita
		//una factura de tipo 'B'.
		
		if(tipofact.equals("A") ||tipofact.equals("a")){
			tipoFactura=1;	
		}else{
			tipoFactura=2;
		}
		
		SimpleDateFormat fechas= new SimpleDateFormat("yyyy-MM-dd");
		Date fechaFactura= fechas.parse(fechafact,new ParsePosition(0));
		
		try{
			candela.agregarFacturaImpaga(peds.get(posPedido), tipoFactura, fechaFactura);
		 
		}catch(SQLException sql){
			System.out.println("Error al intentar almacenar la factura impaga en la BD");
			%>
				<jsp:forward page="errorFacturaBD.html"/>
			<%
			sql.printStackTrace();
		}
		
		//Redirigir a Jsp de Exito.
		%>
			<jsp:forward page="PedidoRecibidoOK.html"/>
		<%


}else{%>
<body>
<body>
				<h1 align="center"><font color="red">Ingrese los datos de la factura que se asociara con el pedido</font></h1>
				<br>
				<form action="formIngresarFactura.jsp" align="center" method="post" >
					Tipo de Factura: <input type="text" id="tipoFactura" name="tipoFactura"/><br><br>
					Fecha de Factura: <input type="text" id="fechaFactura" name="fechaFactura"/><br><br>
					<input type="hidden" id="nroPedido" name="nroPedido" value=<%= request.getParameter("nroPedido")%>>
					<input type="submit" id="Aceptar" value="Aceptar">
					<input type="reset" id="Cancelar" value="Cancelar">				
				</form>
				<a align="center" href="vistaEjecutivo.html" ><b><font color="green">Regresar</font> </b></a>
</body>
</html>



</body>
</html>
<%	}	%>