<%@page import="javax.swing.JOptionPane"%>
<%@page import="excepciones.TomoNoEncontradoException"%>
<%@page import="java.sql.SQLException"%>
<%@page import="excepciones.ProductoNoEncontradoException"%>
<%@page import="net.java.ao.EntityManager"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@page import="utilidades.*" %>
<%@page import="persistencia.*" %>

 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Documento sin t&iacute;tulo</title>
<style type="text/css">
<!--
#apDiv1 {
	position:absolute;
	left:0%;
	top:0%;
	width:100%;
	height:100%;
	z-index:1;
}
#apDiv2 {
	position:absolute;
	width:101px;
	height:23px;
	z-index:1;
	left: 174px;
	top: 345px;
}
#apDiv3 {
	position:absolute;
	left:256px;
	top:91px;
	width:438px;
	height:49px;
	z-index:2;
}
.Estilo1 {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 16px;
	color: #CC0000;
}
#apDiv4 {
	position:absolute;
	width:200px;
	height:115px;
	z-index:2;
	left: 306px;
	top: 173px;
}
.Estilo2 {font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #CC0000; font-weight: bold; }
-->
</style>
<script src="../../../Scripts/AC_RunActiveContent.js" type="text/javascript"></script>
</head>

<%@page import="negocio.*" %>
<%@page session="true"%>
<%@page import="java.sql.SQLException" %>
<%@page import="excepciones.*" %>

<% 

		/* ORDEN DE LLAMADA A JPS's(Version antigua):
			1.formAltaTomo.jsp (Se tiene un boton CANCELAR que redirige a reestablecerCat.jsp)
			2. formDescripcionTomo.jsp (Se tiene un boton CANCELAR que redirige a reestablecerCat.jsp)
			3. formAsociarProdTomo.jsp (Se tiene un boton CANCELAR que redirige a reestablecerCat.jsp)
			
			4. formProductoCorrecto1.html --> este form indica que se han asociado los productos correctamente al tomo.
				 Y ofrece al usaurio CONTINUAR LA CARGA DE PRODUCTOS o FINALIZAR LA CARGA DE PRODUCTOS.
				--Si se elige CONITNUAR se llama a: formAsociarProdTomo.jsp 
				--Si se elige FINALIZAR se llama a: redirigir.jsp
		*/




	//COSAS PARA REVISAR:
		//1. PROBAR REEMPLAZANDO EL CAMPO DE TEXOT DE LA DESCRIPCION POR UN TEXTAREA PARA QUE TOME LA DESCRIPCION
		//COMPLETA. --ARREGLADO
		//2. PROBAR LA PARTE DE INGRESO DE PRODUCTOS  A ASOCIAR A UN TOMO, CUANDO EL TOMO YA TIENE UN PRODUCTO, YA QUE EL FORM
		//TIRA UN VALOR NULL.
		//3.CORREGIR REDIRIGIR.JSP, QUE REDIRIGE MAL. --ARREGLADO
		
		
	HttpSession candela_sesion= request.getSession();
	Candela candela=(Candela)candela_sesion.getAttribute("candela");

	//String codT= request.getParameter("codigoTomo");
	String codP= request.getParameter("codProducto");
	//String descripcion=request.getParameter("descripTomo");
	
	
	
	if(codP!=null && (!codP.equals(""))){
		
		String descripcion=(String)candela_sesion.getAttribute("descripTomo");
		
		
		
		//Catalogo catalogo= (Catalogo) candela_sesion.getAttribute("catalogoNuevo");
		Catalogo catalogo=null;
		
		if(candela.esCatalogoNuevo()==true){
			catalogo=candela.getCatalogoNuevo();
			
		}else{
			catalogo=candela.getCatalogoVigente();
		}
		
		try{
			
		int codigoProducto=Integer.parseInt(codP);
		//int codigoTomo=codT.intValue();
		
		
		//int codigoTomo= Integer.parseInt(codT);
			
		//1.Buscar el Producto que ingreso el usuario por medio de los Productos que tiene Candela
		//y mostrar los datos del producto(investigar, como generar HTML accediendo a <%= VALOR! % desde JSP). 
		//Considerar los casos de Error: 
			//El producto existe,
			//El producto esta asociado a un  tomo vigente.
			//El producto se encuentra ya asociado al mismo tomo creado.
			
			
		//1.0 Busco si el producto existe, sino lo redirijo a errorProdNoExiste.html
		
		if(candela.productoEstaCargado(codigoProducto)==false){// Si no se encontro el producto, se envia a erorrProdNoExiste.html, que redirige formAltaProd.jsp en caso de
							//que el usuario desee, sino se finaliza
			
		%>
			<jsp:forward page="errorProdNoExiste.html" /> 
		<%
		}else{ //Si el producto existe
			
		//TODO IMPORTANTE Si un producto esta asociado directamente a un tomo de un catalogo qeu no esta en vigencia, entonces
		// se toma como que es un producto valido para asociarlo a un nuevo catalogo.
		//TODO IMPLEMENTAR UN JSP QUE INFORME AL USUARIO ACERCA DE QUE EL TOMO ESTA ASOCIADO A UN CATALOGO NO VIGENTE. Y LUEGO PREGUNTE SI DESEA ASOCIARLO
		//AL CATALOGO NUEVO?
		
		
				
				Catalogo catVigente=candela.getCatalogoVigente();// ERROR EN ESTA COMPROBACION
				
				if(catVigente.estaProdAsocTomo(codigoProducto)==true){ //Si el codigo de Producto figura en la coleccion de productos de otro tomo, ya esta asociado a otro tomo
					
				 	 
					%>
						<jsp:forward page="errorProdAsocOtroTomo.html" /> 				
					
					<%
					}
			}
		
			
	
		//Buscar y confirmar que el producto no este asociado al mismo tomo del catalogoNuevo 
							
		Catalogo catNuevo=null;
		if(candela.esCatalogoNuevo()==true){
				catNuevo=candela.getCatalogoNuevo();					
					if(catNuevo.estaProdAsocTomo(codigoProducto)==true){
								%>
								 <jsp:forward page="errorProdAsocAlTomo1.html" />
								<% 
							}
		}
						
			
		
		//Si el producto existe, y no esta asociado al tomo nuevo ni a ninguno de los actuales ENTONCES:
			//Crear el Tomo y agregar el tomo a la coleccion de tomos del catalogoNuevo
	
		try{
			
			//1.Se crea el tomo en memoria y en BD y se lo asigna al catalogo, en memoria y en BD.
				//1.1 Se crea el tomo en BD y en memoria y se lo asigna al catalogo.
			
				//ESTE METODO DEBE IR EN formDescripcionTomo.jsp.!!!!CORREGIR!!!
				//catalogo.altaTomo(codigoTomo, descripcion,candela.esCatalogoNuevo());
		
			//Se recupera el numeroTomo de la sesion de Candela
			Integer codT=(Integer)candela_sesion.getAttribute("codigoTomo");
			int codigoTomo=codT.intValue();

			//INICIO DE BLOQUE DE PRUEBA
				System.out.println("El codigo de tomo es: "+codT+" y el codigo de prod es: "+codP +" y la descripcion es: "+descripcion);
			//FIN DE BLOQUE DE PRUEBA
			
			
			//2. Se asocia el producto al tomo en memoria y en BD.
			catalogo.AsignarProdTomo(codigoProducto, codigoTomo, candela.getColProductos());
			
	
			
		}catch(TomoNoEncontradoException tne){
			System.out.println("Error al buscar el tomo para asignarlo con un producto");
			%>
				<jsp:forward page="errorBusquedaTomo.html" />
			<%
		
		}catch (SQLException  sql){
			
			System.out.println("Error al dar de alta el Tomo en la BD.");
			%>
				<jsp:forward page="errorAltaTomoBD.html"/>
			<%
		
		}
			//Se creo correctamente el catalogo, con almenos un tomo y al menos un producto.
			%>
				<jsp:forward page="formProductoCorrecto.swf" /> 	
			<%
			
			
		}catch(NumberFormatException nfe){
			%>
				<jsp:forward page="errorFormatosNumericos.html"/>
			<%					
		}
}else{
	%>
		
			<jsp:forward page="formaAsociarProdTomo.swf"/>
		
		
<%	}	%>