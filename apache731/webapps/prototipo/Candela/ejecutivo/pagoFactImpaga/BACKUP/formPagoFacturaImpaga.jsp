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
 <%@page session="true" %>
 
<%
	//formPagoFacturaImapaga.jsp, solicita al usuario el nro de dni, y crea una coleccion con todas las facturas que �ste
	//no tiene pagas. Esta coleccion se guarda en un objeto HttpSession que puede ser accedido por otros jsp. Luego este jsp redirige
	// a MostrarFacturaImpaga.jsp que muestra todas las facturas Impagas que posee el usuario.
	
	//NOTA IMPORTANTE: Cuando se setee la facturaPersonal del usuario como Impaga, hacerlo en memoria y en base, PERO tener cuidado
	// si se hacen accesos a los usuarios de Candela ya que estos son usuariosBD y las modificiones que se realicen afectaran la BD directamente.
	
	
	String nrodni=request.getParameter("dni");

	if( nrodni!=null && (!nrodni.equals(""))){
		HttpSession candela_sesion= request.getSession();
		Candela candela=(Candela)candela_sesion.getAttribute("candela"); 
		
		int dni=Integer.parseInt(nrodni);
		
		int posUsuario=candela.verificarUsuario(dni);
		if(posUsuario!=Constantes.ERROR){//Usuario existe 
			
			ArrayList<FacturaPersonal> factsImp=null;
			factsImp=candela.getcolUSRSOFTWARE().get(posUsuario).obtenerFactImpagas();
			
			if(factsImp!=null){ //Si el usuario tiene facturas impagas entonces se continua con el procesamiento
				
				
				//Se almacena la posicion de la factura impaga en la session para poder se recuperada por otro JSP.
				//session.setAttribute("colFacturaImpaga",facturasImpagasUsr );
				//session.setAttribute("posUsuario", Integer.toString(posUsuario));
				
				//Se guarda en la session el dni del usuario para usarlo desde otra jsp posteriomente
				Integer nroDni= new Integer(dni);
				session.setAttribute("dni", nroDni);
				
				//Redirigir a un JSP que muestre las facturas Impagas y que registre el pago de la Factura
				%>
					<jsp:forward page="MostrarFacturaImpaga.jsp"/>
				<%
			
			}else{//Sino tiene facturas impagas, se procede a reenviarlo a un jsp donde se le informa de lo anteriormente dicho
				%>
					<jsp:forward page="NoHayFacturasImpagas.html"/>
				<%
			
			}
		}else{
			%>
				<jsp:forward page="errorUsrNoExiste.html"/>
			<%
			
		}
			//}else{//Sino se redirije al usuario indicando que este no tiene facturas impagas
			
				//	<jsp:forward page="NoExisteFacturasAsociadas.html"/>
				
				
			//}
	}else{
%>
<body>
<h1 align="center"><font  color="red">Ingrese los datos del Vendedor, al que se le registrara el pago de la factura. </font></h1>
				<br>
				<div align="center">
					<form align="center" action="formPagoFacturaImpaga.jsp" align="center" method="post" >
						 Dni Vendedor/Director: <input type="text" id="dni" name="dni"/><br><br>
						<input type="submit" id="Aceptar" value="Aceptar">
						<input type="reset" id="Cancelar" value="Cancelar">				
					</form>
					<a align="center" href="vistaEjecutivo.html" ><b><font color="green">Regresar</font> </b></a>
				</div>
</body>
</html>
<%	}	%>
