<%@page import="javax.swing.JOptionPane"%>
<%@page import="utilidades.GeneradorXML"%>
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
 <%@page import="java.io.PrintWriter"%>
<body>
 
<%
	//formPagoFacturaImapaga.jsp, solicita al usuario el nro de dni, y crea una coleccion con todas las facturas que éste
	//no tiene pagas. Esta coleccion se guarda en un objeto HttpSession que puede ser accedido por otros jsp. Luego este jsp redirige
	// a MostrarFacturaImpaga.jsp que muestra todas las facturas Impagas que posee el usuario.
	
	//NOTA IMPORTANTE: Cuando se setee la facturaPersonal del usuario como Impaga, hacerlo en memoria y en base, PERO tener cuidado
	// si se hacen accesos a los usuarios de Candela ya que estos son usuariosBD y las modificiones que se realicen afectaran la BD directamente.
	
	
	String nrodni=request.getParameter("dni");

	if( nrodni!=null && (!nrodni.equals(""))){
		HttpSession candela_sesion= request.getSession();
		Candela candela=(Candela)candela_sesion.getAttribute("candela"); 
		JOptionPane panel = new JOptionPane();
		
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
				//Antes de redirigir se debe crear un XML de facturasImpagas del vendedor
				GeneradorXML gen=new GeneradorXML(candela);
				gen.generarFacturasImpagasUsuario(dni);
				
				
				
			

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

			
					
				
			
			}else{//Sino tiene facturas impagas, se procede a reenviarlo a un jsp donde se le informa de lo anteriormente dicho
				panel.showMessageDialog(null, "No posee facturas impagas el usuario seleccionado");
			
			}
		}else{
			panel.showMessageDialog(null, "El usuario ingresado no es un usuario de Candela CFSP");
			
		}
}
%>

	<form action="registrarFacturaImpaga.jsp" method="post">
		Numero de factura a pagar: <input type="text" id="nroFacturaImpaga" name="nroFacturaImpaga"><br><br>
		<input align="center" type="submit" value="Pagar Factura" name="Pagar Factura" >
		<a href="formPagoFacturaImpaga.jsp" align="center" ><b>Cancelar</b></a>
	</form>
</body>
</html>
</html>
