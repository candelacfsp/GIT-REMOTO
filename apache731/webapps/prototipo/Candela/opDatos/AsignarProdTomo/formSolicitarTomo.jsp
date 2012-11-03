<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Asignar Producto a Tomo</title>
</head>
<body>

<%@page import= "negocio.*" %>
<%@page import="java.util.*" %>
<%@page import="utilidades.*" %>

<%@page session="true" %>


<% 
	HttpSession candela_sesion= request.getSession();
	Candela candela=(Candela)candela_sesion.getAttribute("candela");

	String codTomo= request.getParameter("codigoTomo");
	String codProd= request.getParameter("codigoProd");
	
	if(codTomo!=null && (!codTomo.equals(""))){
		int codigo_tomo= Integer.parseInt(codTomo);
		int codigo_prod= Integer.parseInt(codProd);
		System.out.println("Continuando asignando prods a tomo");
		
		// Algoritmo:Verificar que el tomo este en vigencia, en la coleccion de tomos de candela. Buscar el producto y obtener una referencia. Luego buscar el tomo obtener una referencia. 
		//Añadir a la coleccion de productos del tomo la referencia de producto.
		//Llamar al metodo asociarProdTomo(codigoTomo,CodigoProd) para hacer la asignacion en la BD.
		
		int postomo=0;
		//Se declara el i aca para que pueda ser usado luego para acceder directamente al tomo del CatVigente y añadirle el ProductoNuevo
		//Busco el tomo y verifico  que el tomo exista
		
		ArrayList<Tomo> tomos= candela.getCatalogoVigente().getTomos();
		while (postomo<tomos.size() && (tomos.get(postomo).getCodigoTomo()!=codigo_tomo)){
			postomo++;
		}
		if(postomo<tomos.size()){ //Si encontre el Tomo, busco la posicion del producto en la coleccion de candela
			
			int posprod=0;
			ArrayList<Producto> productosCand= candela.getProductos();
			while (posprod<productosCand.size() && (productosCand.get(posprod).getCodigo()!=codigo_prod)){
				posprod++;
			}
			
			//Asigno el producto al tomo en memoria, y asocio el producto al tomo en la BD
			tomos.get(postomo).getProductos().add(productosCand.get(posprod));
		/*	int resultado=tomos.get(postomo).AsignarProdTomo(codigo_prod,codigo_tomo); //Esta variable "resultado" se emplea por si luego se añaden codigos de error para las excepciones SQL
			if(resultado ==Constantes.PRODUCTO_ASIGNADO){
				
				%>
				<h1>Producto asignado correctamente al Tomo!</h1>
				<h3><a href="formAsignarProdTomo.jsp"> <b> Regresar</b></a></h3>
			<% 
			}else{
				
			 
					%>
					
					<jsp:forward page="errorSQL.html"/>
					<%
			 
				//COLOCAR ACA LOS DISTINTOS CODIGOS DE ERROR PARA LA ASIGNACION DEL PRODUCTO
				
			}*/
			
		}else{ //Si no encontre el tomo redirijo a pagina de error
 
  
		%>
			<jsp:forward page="ErrorTomoNoExiste.html"/> 
		
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

	<h1> Ingrese el codigo del Tomo al que se asociara el Producto</h1>

<form id="form1" name="form1" method="post" action="formAsignarProdTomo.jsp">
    <table width="322" height="118" border="0">
      <tr>
        <td class="Estilo1">Codigo de Tomo</td>
        <td><div align="center">
          <input type="text" name="codigoTomo" id="codigoTomo" />
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
    
    <input type=HIDDEN name="codigoProd" value=<%=request.getParameter("codigoProd") %> />
  </form>

</body>
</html>

<% } %>