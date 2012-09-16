<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*" %>
<%@page session="true" %>
<%@page import="negocio.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Consultar Producto de Tomo</title>
</head>
 

<%

	HttpSession candela_sesion= request.getSession();
	Candela candela=(Candela)candela_sesion.getAttribute("candela");

	String nroTomo=request.getParameter("codigoTomo");

	if(nroTomo!=null && (!nroTomo.equals(""))){
		//1. Leer los tomos del catalogo vigente, y busca el tomo Indicado.
		int numTomo= Integer.parseInt(nroTomo);
		
		ArrayList<Tomo> tomos= candela.getCatalogoVigente().getTomos();
		
		
		for(int i=0; i< tomos.size(); i++){
			if(tomos.get(i).getCodigoTomo()==numTomo){
				//2.Obtener los Productos del tomo y listarlos en HTML con un objeto PrintWriter
				ArrayList<Producto> prods= tomos.get(i).getProductos();
				//PrintWriter pw= response.getWriter();
				//pw.println("<h1> Productos vigentes en el numero de tomo </h1>");
				
				
				for(int j=0; j < prods.size();j++){
				 	System.out.println("Codigo Producto: "+ prods.get(j).getCodigo() + "Descripcion" + prods.get(j).getDescripcion());
					
				}
				
				//Guardo los productos en la Sesion de Candela
				candela_sesion.setAttribute("productosConsultadosTomo", prods);			
				//Redirijo a la pagina que generara el contenido de los productos que se mostrara en el HTML					
				%>
					<jsp:forward page="productosVigentesLeidos.jsp"/>		
				<% 
			}
		} 
		
	
		
		%>
			<jsp:forward page="errorTomoNoExiste.html"/> 		
		
		<%
		
	}else{
%>

<body>

						<h1> Ingrese el de Tomo que desea consultar	</h1>

			<form action="formConsProdTom.jsp" method="post">
				Codigo de Tomo: <input type="text" name="codigoTomo" value="Codigo Tomo">
				<input type="submit" value="Aceptar" name="aceptar"  >
			</form>
</body>
</html>
<% } %>