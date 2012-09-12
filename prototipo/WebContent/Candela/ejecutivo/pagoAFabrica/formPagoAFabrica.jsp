<%@page import="net.java.ao.EntityManager"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page session="true" %>
<%@page import="negocio.*" %>
<%@page import="persistencia.*" %>
<%@page import="java.util.ArrayList" %>

<%
	String nroFactura=request.getParameter("nroFactura");
	if(nroFactura!=null && (!nroFactura.equals("")) ){
	
	
		int numeroFactFab= Integer.parseInt(nroFactura);
		HttpSession candela_sesion= request.getSession();
		Candela candela=(Candela)candela_sesion.getAttribute("candela"); 
		//1.Buscar la facturaFabrica en la coleccion de facturas de Candela.
		ArrayList<FacturaFabrica> facturasFab= candela.getFacturasFabrica();
		
		int posFacturaFab=0;
		while(posFacturaFab<facturasFab.size() && facturasFab.get(posFacturaFab).getNumero()!=numeroFactFab){
			posFacturaFab++;
		}
		
		if(posFacturaFab<facturasFab.size()){//Si la factura existe
			
			if(facturasFab.get(posFacturaFab).esPagada()==false){
			
			//Se guarda cada una de las facturas seleccionadas por el usuario en la sesion de Candela,
			// que luego es tomada por un .jsp que muestra los elementos de la coleccion
			
		
			
			ArrayList<FacturaFabrica> facts=null;
			facts=(ArrayList<FacturaFabrica>)candela_sesion.getAttribute("FacturasFabricaPagadas");	
			if(facts==null){
				facts=new ArrayList<FacturaFabrica>();			
			}
			facts.add(facturasFab.get(posFacturaFab));
			candela_sesion.setAttribute("FacturasFabricaPagadas",facts);
						
			//Setear la facturaFabrica como factura paga en memoria y en la BD
			facturasFab.get(posFacturaFab).setPagada(true);
			
			EntityManager em= candela.getEm();
			FacturaFabricaBD[] fact=em.find(FacturaFabricaBD.class,"numero=?",facturasFab.get(posFacturaFab).getNumero());
			fact[0].setPagada(true);
			fact[0].save();
			
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
		
	
		
		//3.setear FacturaFabrica como paga.
		
	
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
		<form action="formPagoAFabrica.jsp" method="post" align="center">
			Numero de factura : <input type="text" name="nroFactura" id="nroFactura"><br><br>
			<input type="submit" value="Registrar Pago" name="Facturar" >
			<input type="submit" value="Cancelar" name="Cancelar" >
		</form>

</body>
</html>


<%	}	%>