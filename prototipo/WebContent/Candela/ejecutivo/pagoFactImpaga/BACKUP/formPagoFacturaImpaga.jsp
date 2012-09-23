<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<%@page import="persistencia.*" %>
<%@page import="negocio.*" %>
<%@page import="java.util.*" %>
<%@page import="persistencia.PedidoFabricaBD" %>
 <%@page session="true" %>
 <%@page import="net.java.ao.EntityManager"%>
 <%@page import="utilidades.Constantes" %>
 
<%
	String nrodni=request.getParameter("dni");

	if( nrodni!=null && (!nrodni.equals(""))){
		HttpSession candela_sesion= request.getSession();
		Candela candela=(Candela)candela_sesion.getAttribute("candela"); 
		
		int dni=Integer.parseInt(nrodni);
		
		 //Buscar si el usuario existe en la coleccion de usuarios de Candela
		ArrayList<usuarioBD> usuarios=candela.getColUsuarios();
		int posUsuario;
		for(posUsuario=0; posUsuario<usuarios.size(); posUsuario++){
			if(dni== usuarios.get(posUsuario).getDni()){
				break;
			}
		}
		 
		if(posUsuario<usuarios.size()){
			//Si existe, pedir las facturas asociadas al usuario, y buscar la factura que esta impaga.
			 
			FacturaPersonalBD [] facturasUsr=usuarios.get(posUsuario).getColFacturas();
			//Si el usuario tiene facturas asociadas se muestran solo las que estan impagas
			int posFacturaImpaga;
			if(facturasUsr.length>0){
				//Se guardan en una coleccion las facturas que estan impagas. La coleccion es recuperada desde la sesion de candela
				// y son mostradas por otro .jsp
 				ArrayList<FacturaPersonalBD> facturasImpagasUsr=new ArrayList<FacturaPersonalBD>();
 				
				for(posFacturaImpaga=0; posFacturaImpaga <facturasUsr.length;posFacturaImpaga++){
					if(facturasUsr[posFacturaImpaga].getPagada()==false){
						facturasImpagasUsr.add(facturasUsr[posFacturaImpaga]);
					}
				}
			
			if(facturasImpagasUsr.size()>0){ //Si el usuario tiene facturas impagas entonces se continua con el procesamiento
				//Se almacena la posicion de la factura impaga en la session para poder se recuperada por otro JSP.
				session.setAttribute("colFacturaImpaga",facturasImpagasUsr );
				session.setAttribute("posUsuario", Integer.toString(posUsuario));
				
				//Redirigir a un JSP que muestre las facturas Impagas y que registre el pago de la Factura
				%>
					<jsp:forward page="MostrarFacturaImpaga.jsp"/>
				<%
			
			}else{//Sino tiene facturas impagas, se procede a reenviarlo a un jsp donde se le informa de lo anteriormente dicho
				%>
					<jsp:forward page="NoHayFacturasImpagas.html"/>
				<%
			
			}
				
			
				
				
			}else{//Sino se redirije al usuario indicando que este no tiene facturas impagas
				%>
					<jsp:forward page="NoExistenFacturasAsociadas.html"/>
				<%
				
			}
			
			 
		}else{
			%>
				<jsp:forward page="errorUsrNoExiste.html"/>
			<%
			
		}
		
		
		
	 }else{
%>
<body>
<h1><font color="red">Ingrese los datos del Vendedor, al que se le registrara el pago de la factura. </font></h1>
				<br>
				<form action="formPagoFacturaImpaga.jsp" align="center" method="post" >
					 Dni Vendedor/Director: <input type="text" id="dni" name="dni"/><br><br>
					<input type="submit" id="Aceptar" value="Aceptar">
					<input type="reset" id="Cancelar" value="Cancelar">				
				</form>
				<a align="center" href="vistaEjecutivo.html" ><b><font color="green">Regresar</font> </b></a>

</body>
</html>
<%	}	%>
