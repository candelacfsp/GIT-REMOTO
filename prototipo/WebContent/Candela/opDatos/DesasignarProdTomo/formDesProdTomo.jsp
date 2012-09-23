<%@page import="javax.swing.JOptionPane"%>
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
	String cod= request.getParameter("codigoProducto");
	String nombre= request.getParameter("nombreProducto");
	String tomo=request.getParameter("codigoTomo");
	
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
						JOptionPane panel= new JOptionPane();
						panel.showMessageDialog(null, "Producto desasignado correctamente!");
						GeneradorXML xml = new GeneradorXML(candela);
						xml.generarProductosNoAsociados();
						xml.generarTomosVigentes();
						response.sendRedirect("../vistaOpDatos.swf");
					}else{
				 			if(nombre!=null){
								candela.getCatalogoVigente().desasignarProdTomo(nombre);
								JOptionPane panel= new JOptionPane();
								panel.showMessageDialog(null, "Producto desasignado correctamente!");
								GeneradorXML xml = new GeneradorXML(candela);
								xml.generarProductosNoAsociados();
								xml.generarTomosVigentes();
								response.sendRedirect("../vistaOpDatos.swf");
				 			}

					}
					
				}catch(SQLException sql){
					response.sendRedirect("../vistaOpDatos.swf");
				
				}catch(ProductoNoAsocTomoException pne){
					pne.mensajeDialogo("El producto no se encuentra asignado a ningún tomo!");
					response.sendRedirect("../vistaOpDatos.swf");
				}
				
						
					 
					
			
			
		}else{
			if (!response.isCommitted()){
				JOptionPane panel= new JOptionPane();
				panel.showMessageDialog(null, "El producto no existe");
				response.sendRedirect("../vistaOpDatos.swf");
			}
		}
	}
	%>