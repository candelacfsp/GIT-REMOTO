<%@page import="Excepciones.TomoNoEncontradoException"%>
<%@page import="javax.swing.JOptionPane"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Baja de Tomo</title>
</head> 
    
    
<%@page session="true" %>
<%@page import="negocio.*" %>   
<%@page import="utilidades.*" %> 
<%@page import="java.util.*" %>
<%@page import="Excepciones.*" %>

<%

	HttpSession candela_sesion= request.getSession();
	Candela candela=(Candela)candela_sesion.getAttribute("candela");
	String codTomo=request.getParameter("codigoTomo");
	if(codTomo!=null && (!codTomo.equals(""))){
		int codigoTomo= Integer.parseInt(codTomo); 
		//Nota: para borrar el Tomo, debo setearle a cada uno de los productos de la tabla ProductoBD
		// null, como tomo, y luego borrar el tomoBD en si.
		
		//1.Leer los tomos de memoria, encontrar el tomo y borrar el tomo  de la coleccion de tomos de Catalogo
		
		//TODO : La baja de Tomo, desasocia los productos de un tomo, y luego elimina solamente el tomo; Los productos quedan
		// guardados en la BD, sin estar asignados a un tomo especifico.
		
		try{
			Catalogo catalogo= candela.getCatalogoVigente();
			catalogo.bajaTomo(codigoTomo);
			//Si no se lanzo ninguna excepcion, se redirige al HTML de exito!
			%>
				<jsp:forward page="tomoDadoBaja.html"/> 
			<%
		}catch(SQLException sql){
			
			System.out.println("Ocurrio un error al manipular los tomos con la BD! ");
			sql.printStackTrace();
			%>
				<jsp:forward page="errorBorrarEnBD.html"/>		
			<%
		
		}catch(TomoNoExisteExcepcion tne){
			tne.mensajeDialogo("No se encuentr registrado el tomo en el Sistema");
			%>
				<jsp:forward page="errorTomoNoVigente.html"></jsp:forward>
			<%
		}
	
		
	}else{
%>    


<body>
		<h1> Ingrese el numero de tomo que desea dar de Baja.</h1>
	<form action="formBajaTomo.jsp" method="get">	
		<b>Código de Tomo:</b><input type="text" name="codigoTomo" value="Dar de Baja el Tomo">
		<input type="submit" value="Eliminar Tomo" name="bajaTomo">
		<input type="submit" value="Cancelar" name="candelarBajaTomo">		
	</form>

</body>
</html>

<%}	%>