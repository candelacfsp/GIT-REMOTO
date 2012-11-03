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
<%
	HttpSession candela_sesion= request.getSession();
	Candela candela=(Candela)candela_sesion.getAttribute("candela");
	String codigo= request.getParameter("codigoProd");
	
	if(codigo!=null && (!codigo.equals("")) ){
		
		int codProd= Integer.parseInt(codigo);
		//Verifico que el producto exista en la BD de candela para poder asignarlo
		ArrayList<Producto> productosCand= candela.getProductos();
		int posproducto=0;
		while(posproducto<productosCand.size() && (productosCand.get(posproducto).getCodigo()!=codProd)){
			posproducto++;
		}
		if(posproducto<productosCand.size()){ //Si existe hago la comprobacion del producto en el catalogo
			
			//Verificar que el producto no Exista en el Catalogo Vigente
			ArrayList<Tomo> tomosVigentes= candela.getCatalogoVigente().getTomos();
			for(int i=0; i< tomosVigentes.size(); i++){ //Obtengo de cada tomo su coleccion de productos y la recorro
				ArrayList<Producto> prods= tomosVigentes.get(i).getProductos();
				for(int j=0; j< prods.size();j++){//Leo la lista de productos de cada tomo, y verifico que no este asignado al catalogo actual
					
					if(codProd==prods.get(j).getCodigo()){ //Si el producto esta en el catalogo, lo redirigo a la pagina de errorProducto
						//JSP: ESTABA COMO TOMO NO EXISTE
						%>
						<jsp:forward page="errorProdYaAsignado.jsp"/>
						
			<% 
						
					}
				}
				
			}											//Si el producto no esta en el catalogo vigente, lo redirigo a fomSolicitarTomo.jsp
		}else{ //Si el producto no existe muestro error
			
			%>
				<jsp:forward page="errorProductoNoExisteBD.html"/>
			<%
		}
		
		%>
				 
					<jsp:forward page="formSolicitarTomo.jsp"/>
		
<%  		
	}else{
%>
	<h1> Ingrese el codigo del producto que desea asociar al Tomo</h1>

<form id="form1" name="form1" method="post" action="formAsignarProdTomo.jsp">
    <table width="322" height="118" border="0">
      <tr>
        <td class="Estilo1">Codigo de Producto</td>
        <td><div align="center">
          <input type="text" name="codigoProd" id="codigoProd" />
        </div></td>
      </tr>
  
      <tr>
        <td><div align="center">
          <input type="submit" name="botAceptar" id="botAceptar" value="Aceptar" />
        </div></td>
        <td><div align="center">
          <input type="reset" name="botRestablecer" id="botRestablecer" value="Restablecer" />
        </div></td>
      </tr>
    </table>
  </form>
		
</body>
</html>

<% } %>