<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="negocio.*" %>
<%@page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Asignar Producto a Tomo</title>
</head>
<body>
<%@page session="true" %>
<%@page import="utilidades.*" %>
<%
	//ORDEN DE LLAMADA a forms y swf:
		/*
		0- form en flash que pide el nro prod y el codigoTomo.
		1- generarTomosVigentesXML.jsp
		2- verificarProducto.jsp
		3. AsignarATomo.jsp
		4. ProductoAsignadoOK.swf
		
		*/


   //Primero se llama a verificar producto, que verifica que el producto ingresado por el usuario sea 
   //un producto valido.
   
	HttpSession candela_sesion= request.getSession();
	Candela candela=(Candela)candela_sesion.getAttribute("candela");
	String codigo= request.getParameter("codigoProd");
	
	if(codigo!=null && (!codigo.equals("")) ){
		
		int codProd= Integer.parseInt(codigo);
		
		if(candela.existeProductoCandela(codProd)!=Constantes.ERROR){ //Si existe hago la comprobacion del producto en el catalogo
			
		
			if(candela.getCatalogoVigente().estaProdAsignadoCatVigente(codProd)==true){ //Si el producto esta en el catalogo, lo redirigo a la pagina de errorProducto
						//JSP: ESTABA COMO TOMO NO EXISTE
			%>
				<jsp:forward page="errorProdYaAsignado.swf"/>
				<!--<jsp:forward page="errorProdYaAsignado.jsp"/> -->			
			<% 
						
			}//fin del IF
				
				
				//Si el producto no esta en el catalogo vigente, lo redirigo a fomSolicitarTomo.jsp
		}else{ //Si el producto no existe muestro error
			
			%>
				<jsp:forward page="errorProductoNoExisteBD.swf"/>
			<!-- 	<jsp:forward page="errorProductoNoExisteBD.html"/> -->
			<%
		}
		
			
		
		
		%>
		
				<!-- Si el producto es valido se llama directamente a AsignarATomo.jsp, que realiza la asignacion directa del producto con el tomo sinv validar el tomo, ya qu este se  -->
				<!-- valido cuando se genero generarTomosVigentesXML.jsp. --> 
				 	<jsp:forward page="AsignarATomo.jsp"/>
		
<%  } %>