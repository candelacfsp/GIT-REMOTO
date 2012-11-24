<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@page session="true" %>
<%@page import="negocio.*" %>
<%@page import="persistencia.*" %>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%
	HttpSession candela_sesion= request.getSession();
	Candela candela=(Candela)candela_sesion.getAttribute("candela"); 
	
	
	//Se leen las facturas almacenadas en una coleccion dentro de la session de candela.
	//Al final se elimina la coleccion de la session.
	
	ArrayList<FacturaFabrica> facturas= (ArrayList<FacturaFabrica>)candela_sesion.getAttribute("FacturasFabricaPagadas");
	//2. Mostrar los datos de la facturaFabrica(con sus pedidos personal y sus detalles de pedido personal)
	PrintWriter pw=response.getWriter();
	
	
	if(facturas==null){
		pw.println("<h1><font color='red'>No hay facturas seleccionadas que mostrar</font></h1>");
		pw.println("<a href='formPagoAFabrica'><b>Regresar</b></a>");
		
	}else{
		for(FacturaFabrica fact1: facturas){
		
		//Se muestran los Datos dela Factura a Fabrica
			pw.println("<div align='center'><h2 align='center' >Factura a Fabrica</h2>");
			pw.println("------------------------------------------------------------------------------------------------------------------<br>");
			pw.println("|Numero de Factura|  |Tipo de Factura|  |Fecha de emision de factura|  |Numero de Pedido|<br>");
			
			
			pw.println(fact1.getNumero()+" "+( (fact1.getTipo()==1)?"A":"B")+" "+fact1.getFecha()+
			" "+fact1.getPedidofab().getNumeroPedido()+"<br>");
			pw.println("<h2 align='center' >Detalles de Factura a Fabrica</h2>");
			pw.println("------------------------------------------------------------------------------------------------------------------<br>");
				
			ArrayList<PedidoPersonal> pedidos=fact1.getPedidofab().getPedidos();
			for(PedidoPersonal ped: pedidos){
			 	pw.println("<font color='blue'>Pedido de Personal número: "+ped.getNumeroPedido()+"</font> <br>");
			 	pw.println("------------------------------------------------------------------------------------------------------------------ <br>");
				pw.println("Detalles de Pedido del Personal  <br>");
				pw.println("------------------------------------------------------------------------------------------------------------------ <br>");
				pw.println(" |Nro_Renglon| |Producto| |Cantidad| |Precio_Unitario|  |Subtotal|<br>");
				pw.println("------------------------------------------------------------------------------------------------------------------ <br>");
					

				ArrayList<DetallePedidoPersonal> detalles=ped.getDetalles();
				for(int i=0; i<detalles.size(); i++){
						pw.println((i+1)+" "+detalles.get(i).getProd().getDescripcion()+" "+detalles.get(i).getCantidad()+
						" "+detalles.get(i).getProd().getPrecio()+" "+detalles.get(i).subtotal()+"<br>");
					 
					}
			
				pw.println(" ------------------------------------------------------------------------------------------------------------------ <br>");
				pw.println("Total del pedido del personal: "+ped.total()+"<br>");
				pw.println(" *********************************************************************************************** <br>");
			
		}
			pw.println(" ------------------------------------------------------------------------------------------------------------------ <br>");
			pw.println("Total de la FacturaFabrica: "+fact1.total()+"<br><br>");
			pw.println("*********************************************************************************************** <br>");
			
			
		//Mostrar que se abono correctamente factura
					pw.println("<h2 align='center'><font color='blue'>Se ha Abonado correctamente la factura a fábrica.</font></h2><p>");
					pw.println("<a href=formPagoAFabrica.jsp> Aceptar </a></div>");
		}
		
		
		//Por cada una de las facturas a fabrica pagadas, se debe remover la factura de la coleccion de facturas de Candela y
		// el pedidofabrica asociado con esa factura de la coleccion correspondinete.
		/* 	
		for(FacturaFabrica fab1: facturas){
			candela.getFacturasFabrica().remove(fab1);
			candela.getPedidosFabricaNoFacturados().remove(fab1.getPedidofab());
		} */
		
		//Se borra la coleccion de facturas Pagas de la sesion de candela.
		candela_sesion.removeAttribute("FacturasPagasMostrar");
	}
	

	
	//Cerrar el printWriter
	pw.close();

%>			
</body>
</html>