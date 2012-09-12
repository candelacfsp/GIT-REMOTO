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
				
				}catch(SQLException sql){
					//Se vuelve a setear la factura Fabrica como Impaga
					candela.getFacturasFabrica().get(posFacturaFab).setPagada(false);
					sql.printStackTrace();
					%>
						<jsp:forward page="errorGuardarFacturaBD.html"/>
					<%
				}	
				
			%>
				<jsp:forward page="FacturaAnadidaOK.jsp"/>
			<%			
			
			}else{
			%>	
				<jsp:forward page="errorFacturaYaPagada.html"/>
			<%
			}
			
		}else{ //Si la factua no existe
			%>
				<jsp:forward page="errorNoExisteFact.html"/>
			<%
		}
		
	
		
	
	}else{

 %>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
		<h1 align="center"> <font color="red"  >Ingrese el numero de factura que desea abonar.</h1><br>
		
		<div align="center">
			<form action="formPagoAFabrica.jsp" method="post" align="center">
				Numero de factura : <input type="text" name="nroFactura" id="nroFactura"><br><br>
				<input type="submit" value="Registrar Pago" name="Facturar" >
				<input type="submit" value="Cancelar" name="Cancelar" >
			</form>
		</div>
</body>
</html>


<%	}	%>