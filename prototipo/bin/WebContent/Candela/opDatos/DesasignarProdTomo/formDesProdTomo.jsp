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

	/* NOTA: Este formDesProdTomo.jsp debe desasignar un producto de un tomo. Debe permitir seleccionar uno de los tomos cargados en un
	comboBox, y debe permitir buscar el tomo por su numero que estara en otro comboBox o por su nombre que estara en otro comboBox. 
	CONSUTLAR ESTO!!!
	*/
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
						<jsp:forward page="errorSQL.swf"/>
						<!--<jsp:forward page="errorSQL.html"/> --> 
					<%
				
				}catch(ProductoNoAsocTomoException pne){
					System.out.println("Error: el producto no se encuentra asignado a ningun tomo");
					%>
						<jsp:forward page="errorProdNoAsociado.swf"/>
						<!--<jsp:forward page="errorProrNoAsociado.html"/>-->			
					<%
				}
				//Si la operacion tuvo exito se redirige a un HTML de exito
						%>
							<jsp:forward page="ProdDesasignadoOK.swf"/>
							<!--<jsp:forward page="ProdDesasignadoOK.html"/> --> 
						<%
						
					 
					
			
			
		}else{
			//SI EL PRODUCTO NO EXISTE
			%>
					<jsp:forward page="errorProdNoExiste.html"/>
				
			<%
		}
	}
	%>