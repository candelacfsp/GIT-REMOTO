<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@page session="true" %>
<%@page import="negocio.*" %>
<%@page import="persistencia.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registro de Factura Impaga</title>
</head>


<%
	HttpSession candela_sesion= request.getSession();
	Candela candela=(Candela)candela_sesion.getAttribute("candela"); 

	//Se recupera la posicion del usuario desde la session
	//String posUsuario=(String)request.getSession().getAttribute("posUsuario");
	//int posUsr=Integer.parseInt(posUsuario);

	//Se recupera el arrayList de facturasPersonalBD Impagas desde la session
	
	ArrayList<FacturaPersonalBD> factImpagas=(ArrayList<FacturaPersonalBD>)candela_sesion.getAttribute("colFacturaImpaga");
	

	//Recupero la posicion del usuario
	ArrayList<usuarioBD> usuarios= candela.getColUsuarios();
	
	//FacturaPersonalBD[] factImpaga=usuarios.get(posUsr).getColFacturas();
	
	//Se obtiene un objeto pw, qeu se encarga de generar el contenido HTML que sera visible por el usuario
	PrintWriter pw= response.getWriter();
	
	for(FacturaPersonalBD fact1: factImpagas){
			System.out.println("datos de factura:"+fact1.getNumero()+fact1.getFecha());
			
			pw.println("<h1 align='center'><font color='blue'>Listado de Facturas Impagas:</font></h1>");
			pw.println("<br><br>");
			pw.println("----------------------------------------------------------------------------<br>");
			pw.println("	Numero de Factura: "+fact1.getNumero()+"<br>");
			pw.println("	Fecha: "+fact1.getFecha()+"<br>");
			
			switch(fact1.getTipo()){
			case 1:
				pw.println("\tTipo de Factura: "+"Factura A"+"<br>");
				break;
			default:
				pw.println("\tTipo de Factura: "+"Factura B"+"<br>");
				break;
			}
			//DetallePedidoPersonalBD []detalles= fact1.getPedidoPersonalBD().getColDetallesPedidoPersonalBD();
			
			pw.println("\tDetalles de Factura: "+ "<br>");
			pw.println("<div align='center'>-------------------------------------------------------------------------------------------------------------------------</div><br>");
			pw.println("<div align='center'>Renglon Producto Codigo Producto PU	Cantidad </div> <br>");
			pw.println("<div align='center'>-------------------------------------------------------------------------------------------------------------------------</div><br>");
			
			//Se leen los detalles del pedido personal asociado a la factura, con el fin de tener el nombre del producto en la
			//impresion de la factura
			DetallePedidoPersonalBD[] dets=fact1.getPedidoPersonalBD().getColDetallesPedidoPersonalBD();
			
			for(int i=0; i<dets.length; i++){
				pw.println("<div align='center'>"+(i+1)+" "+dets[i].getProductoBD().getDescripcion()+"	"+dets[i].getProductoBD().getCodigo()+"	"+dets[i].getProductoBD().getPrecio() +" "+dets[i].getCantidad()+"</div><br>");
			}
			pw.println("---------------------------------------------------------------------------------------------------------------------------------------------------<br>");
			
	
	}//Fin muestra de facturas

%>
<body>
	<form action="registrarFacturaImpaga.jsp" method="post">
		Numero de factura a pagar: <input type="text" id="nroFacturaImpaga" name="nroFacturaImpaga"><br><br>
		<input align="center" type="submit" value="Pagar Factura" name="Pagar Factura" >
		<a href="formPagoFacturaImpaga.jsp" align="center" ><b>Cancelar</b></a>
	</form>
</body>
</html>

