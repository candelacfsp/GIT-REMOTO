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
<title>Insert title here</title>
</head>
<body>
<%
	//FacturaPagadaOK.jsp, se encarga de mostrar un comprobante de pago para la factura que se selecciono a pagar.
	
	HttpSession candela_sesion= request.getSession();
	Candela candela=(Candela)candela_sesion.getAttribute("candela"); 	
	
	//int posFactImp=( (Integer)(candela_sesion.getAttribute("posicionFacturaImpaga")) ).intValue();
	//ArrayList<FacturaPersonalBD> factsImpagas=(ArrayList<FacturaPersonalBD>)candela_sesion.getAttribute("colFacturaImpaga");
	
	//Obtengo la coleccion de facturas Impagas y la posicion de una factImpaga de un Usuario, en base a su dni
	//String nroDni=(String)request.getAttribute("dni");
	//int dni=Integer.valueOf(nroDni);
	
	//Obtengo el dni de la session de Candela
	Integer nroDni=(Integer)candela_sesion.getAttribute("dni");
	int dni=nroDni.intValue();
	
	int posUsuario=candela.verificarUsuario(dni);
	Usuario usr=candela.getcolUSRSOFTWARE().get(posUsuario);
	
	
	//Se obtiene el numero de factura impaga de la session
	
	Integer numFactImp=(Integer)candela_sesion.getAttribute("nroFactImp");
	int nroFactImpaga=numFactImp.intValue();
	
	//Se obtiene el numero de factura impaga
	//String numFactImp=(String)request.getAttribute("nroFacturaImpaga");
	//int nroFactImpaga= Integer.valueOf(numFactImp); //EXCEPCION
	
	int posFactImp=usr.verificarFacturaImpaga(nroFactImpaga); //EXCEPCION!!!
		
		
	ArrayList<FacturaPersonal> factsImpagas=usr.obtenerFactImpagas();
	
	
	PrintWriter pw= response.getWriter();
	
	pw.println("<h5 align='center'>Comprobante de Pago de Factura de Personal.</h5>");
	pw.println("\tNro_Factura\tFecha\tTipo");
	pw.println("....................................................................<br>");
	pw.println("\t"+factsImpagas.get(posFactImp).getNumero()+"\t"+factsImpagas.get(posFactImp).getFecha()+"\t"+factsImpagas.get(posFactImp).getTipo());
	pw.println("<h5 align='center'>Detalles de la Factura:</h5>");
	pw.println("....................................................................<br>");
	pw.println("\tRenglon\tNombre_Producto\tCantidad\tPrecio\tSubtotal");
	
		//Buscar los pedidos personal correspondientes a la factura personal
		//DetallePedidoPersonalBD [] detsPedido=factsImpagas.get(posFactImp).getPedidoPersonalBD().getColDetallesPedidoPersonalBD();
		ArrayList<DetallePedidoPersonal> detsPedido=factsImpagas.get(posFactImp).getPedidoPers().getDetalles();	
		
		
	System.out.println("Numero de Detalles en el Pedido Personal : "+ detsPedido.size());	
		
	/* ProductoBD p1= null;
	p1=detsPedido[0].getProductoBD();
	 */
	 Producto p1=null;
	 p1=detsPedido.get(0).getProd();
	 if(p1!=null){
		System.out.println("Datos del productoBD asociado al detalle: "+p1.getDescripcion()+" "+p1.getPrecio()+" "+p1.getCodigo());	
	}else{
		System.out.println("Error no existe un producto asociado al detalle ");
		System.exit(1);
	}
		
	 for(int i=0; i<detsPedido.size();i++){
		pw.println("\t"+(i+1)+"\t"+detsPedido.get(i).getProd().getDescripcion()+"\t"+
			detsPedido.get(i).getCantidad()+"\t"+detsPedido.get(i).getProd().getPrecio()+"\t"+(detsPedido.get(i).getCantidad()*detsPedido.get(i).getProd().getPrecio()) );
	} 	
	/* for(int i=0; i<detsPedido.length;i++){
		pw.println("\t"+(i+1)+"\t"+detsPedido[i].getProductoBD().getDescripcion()+"\t"+
			detsPedido[i].getCantidad()+"\t"+detsPedido[i].getProductoBD().getPrecio()+"\t"+(detsPedido[i].getCantidad()*detsPedido[i].getProductoBD().getPrecio()) );
	} */
	
	//Redirigir a formPagoFacturaImpaga.jsp, y que este .jsp se encargue de realizar siempre que es invocado la destruccion de
	// la coleccion de facturasPersonalBD Impagas que posee la sesion
%>
	<br><br>
	<a align="center" href="marcarFactImp.jsp"><b>Finalizar pago de Factura</b></a>
</body>
</html>