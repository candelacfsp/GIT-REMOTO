<%@page import="net.java.ao.EntityManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

 <%@page session="true" %>
<%@page import="negocio.*" %>   
<%@page import="utilidades.*" %> 
<%@page import="java.util.*" %>
<%@page import="persistencia.PedidoFabricaBD" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Recepcion de Pedido de Fabrica</title>
</head>

<%
	String nroPed= request.getParameter("nroPedido");
	if(nroPed!=null && (!nroPed.equals("")) ){
		//Se obtiene el numero de pedido
		int nroPedido=Integer.parseInt(nroPed);
		
		//Se obtiene candela de la session.
		HttpSession candela_sesion= request.getSession();
		Candela candela=(Candela)candela_sesion.getAttribute("candela"); 
		
		int posPedido=candela.verificarPedido(nroPedido);
		if(posPedido==Constantes.ERROR){ //Si el pedido no Existe
		
			%>
				<jsp:forward page="errorNoExistePedido.html"/>
			<%
		
		}else {//Si el pedido existe
			
			if(candela.getPedidosFabricaNoFacturados().get(posPedido).estaRecibido()==false){//Si el pedido no esta Recibido
				//Añadir el pedido como posicionPedidoFacturado dentro de la sesion de candela
				//para recuperarlo en el formIngresarFactura.jsp, en lugar de realizar nuevamente la busqueda alli.
				//candela_sesion.setAttribute("posPedidoFabrica", new Integer(posPedido));
				
				//Se guarda el numero de pedido que se marcara dentro de la sesion de candela. NOTA: El autoboxing no se realiza automaticamente.
				
				int numPedido=candela.getPedidosFabricaNoFacturados().get(posPedido).getNumeroPedido();
				Integer npedido=new Integer(numPedido);
				candela_sesion.setAttribute("nroPedido", npedido);
				%>
					<jsp:forward page="formIngresarFactura.jsp"/>
				<%
				
			}else{// Sino  el pedido ya esta marcado como recibido
				%>
					<jsp:forward page="errorPedYaMarcado.html"/>
				<%
			}
		}
	}else{
%>

<body>
				<h1><font color="red">Ingrese el número de Pedido que desea marcar como recibido desde Fábrica</font></h1>
				<br>
				<div align="center">
					<form action="formRecepcion.jsp" align="center" method="post" >
						Numero de Pedido: <input type="text" id="nroPedido" name="nroPedido"/><br><br>
						<input type="submit" id="Aceptar" value="Aceptar">
						<input type="reset" id="Cancelar" value="Cancelar">				
					</form>
					<a align="center" href="vistaEjecutivo.html" ><b><font color="green">Regresar</font> </b></a>
				</div>
</body>
</html>

<%	}	%>