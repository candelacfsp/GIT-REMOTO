<%@page import="excepciones.ProductoNoEncontradoException"%>
<%@page import="excepciones.TomoNoEncontradoException"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="excepciones.*"%>
<%@page import="java.util.*" %>
<%@page import="utilidades.*" %>
<%@page import="negocio.*" %>
<%@page session="true" %>

<%
	HttpSession candela_sesion= request.getSession();
	Candela candela=(Candela)candela_sesion.getAttribute("candela");
	String cod= request.getParameter("codProducto");
	String nombre= request.getParameter("nombreProducto");
	
	if(cod!=null && (!cod.equals("")) || (nombre!=null && (!nombre.equals("")))){
		//1. Verificar que el producto exista, obteniendo los productos de Candela.
		
		int posProducto=-1;
		int codigoProd=-1;
		
		//Se llama al metodo existeProducto() en Candela, que determina si un producto existe en la coleccion de productos,
		//sino retorna el valor -1 (que equivale a Constantes.ERROR)
				
		if(cod!=null){
			 codigoProd=Integer.parseInt(cod);	
			  posProducto= candela.existeProductoCandela(codigoProd);
		}else{
			  posProducto=candela.existeProductoCandela(nombre);
		}
		
		if(posProducto!=Constantes.ERROR){ //Si encontre el producto, en la coleccion de productos de candela
			
				try{
					//El metodo desasignarProdTomo esta sobrecargado para soportar buscar el producto en la coleccion de Candela
					//por su codigo o su producto.
					if(cod!=null){
						candela.getCatalogoVigente().desasignarProdTomo(codigoProd);	
					}else{
				 			if(nombre!=null){
								candela.getCatalogoVigente().desasignarProdTomo(nombre);
				 			}

					}
					
				}catch(SQLException sql){
					System.out.println("Ocurrio un error con la BD al desasignar el tomo del Producto! ");
					%>
						<jsp:forward page="errorSQL.html"/> 
					<%
				
				}catch(ProductoNoAsocTomoException pne){
					System.out.println("Error: el producto no se encuentra asignado a ningun tomo");
					%>
						<jsp:forward page="errorProrNoAsociado.html"/>			
					<%
				}
				//Si la operacion tuvo exito se redirige a un HTML de exito
						%>
							<jsp:forward page="ProdDesasignadoOK.html"/> 
						<%
						
					 
					
			
			
		}else{
			//SI EL PRODUCTO NO EXISTE
			%>
					<jsp:forward page="errorProdNoExiste.html"/>
				
			<%
		}
	}else{
%>

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
	width:388px;
	height:54px;
	z-index:1;
	left: 351px;
	top: 108px;
}
#apDiv3 {
	position:absolute;
	width:387px;
	height:141px;
	z-index:2;
	left: 351px;
	top: 210px;
}
#apDiv4 {
	position:absolute;
	width:103px;
	height:24px;
	z-index:3;
	left: 211px;
	top: 392px;
}
.Estilo1 {	font-family: Arial, Helvetica, sans-serif;
	font-size: 16px;
	color: #CC0000;
}
-->
</style>
<script src="../../../Scripts/AC_RunActiveContent.js" type="text/javascript"></script>
</head>

<body>
<div id="apDiv1">
  <div id="apDiv2"><strong><span class="Estilo1">Ingrese el nombre y/o código del producto que desea desasignar.</span></strong></div>
  <div id="apDiv3">
    <form id="form1" name="form1" method="post" action="">
      <table width="384" height="95" border="0">
        <tr>
          <td width="185"><strong><span class="Estilo1"> Código de Producto</span></strong></td>
          <td width="189"><div align="center">
            <input type="text" name="codProducto" id="codProducto" />
          </div></td>
        </tr>
        <tr>
          <td><strong><span class="Estilo1"> Nombre del Producto</span></strong></td>
          <td><div align="center">
            <input type="text" name="nombreProducto" id="nombreProducto" />
          </div></td>
        </tr>
        <tr>
          <td colspan="2"><div align="center">
            <input type="submit" name="botAceptar" id="botAceptar" value="Aceptar" />
          </div></td>
        </tr>
      </table>
    </form>
  </div>
  <div id="apDiv4">
    <script type="text/javascript">
AC_FL_RunContent( 'codebase','http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0','width','100','height','22','title','cancelar','src','button1','quality','high','pluginspage','http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash','movie','button1' ); //end AC code
</script><noscript><object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0" width="100" height="22" title="cancelar">
      <param name="movie" value="button1.swf" />
      <param name="quality" value="high" />
      <embed src="button1.swf" quality="high" pluginspage="http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash" type="application/x-shockwave-flash" width="100" height="22" ></embed>
    </object>
  </noscript></div>
</div>
</body>
</html>

<%	}	%>	