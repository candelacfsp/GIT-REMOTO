<%@page import="com.sun.org.apache.xml.internal.resolver.Catalog"%>
<%@page import="com.sun.org.apache.bcel.internal.generic.INEG"%>
<%@page import="javax.swing.JOptionPane"%>
<%@page import="excepciones.TomoNoEncontradoException"%>
<%@page import="java.sql.SQLException"%>
<%@page import="excepciones.ProductoNoEncontradoException"%>
<%@page import="net.java.ao.EntityManager"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="utilidades.*"%>
<%@page import="persistencia.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Documento sin t&iacute;tulo</title>

</head>

<%@page import="negocio.*"%>
<%@page session="true"%>
<%@page import="java.sql.SQLException"%>
<%@page import="excepciones.*"%>

<%
	
	HttpSession candela_sesion = request.getSession();
	Candela candela = (Candela) candela_sesion.getAttribute("candela");
	String mensaje = (String) candela_sesion.getAttribute("mensaje");
	String codP = request.getParameter("codProducto");
	String opcion = request.getParameter("opcion");
	
	
	if (codP != null && (!codP.equals(""))) {
		GeneradorXML xml = new GeneradorXML(candela);
		String descripcion = (String) candela_sesion
				.getAttribute("descripTomo");
	
		//TODO MODIFICADO RODRIGO 23-11-2012
		
		//Se obtiene el catalogo nuevo por medio de Candela
		
		Catalogo catalogo=candela.getCatalogoNuevo();
		
		//Version vieja BACKUP!
		/*Catalogo catalogo = null;
		if (candela.esCatalogoNuevo() == true) {
			catalogo = candela.getCatalogoNuevo();

		} else {
			catalogo = candela.getCatalogoVigente();
		}*/

		try {

			int codigoProducto = Integer.parseInt(codP);
			//int codigoTomo=codT.intValue();

			//int codigoTomo= Integer.parseInt(codT);

			//1.Buscar el Producto que ingreso el usuario por medio de los Productos que tiene Candela
			//y mostrar los datos del producto(investigar, como generar HTML accediendo a <%= VALOR! % desde JSP). 
			//Considerar los casos de Error: 
			//El producto existe,
			//El producto esta asociado a un  tomo vigente.
			//El producto se encuentra ya asociado al mismo tomo creado.

			//1.0 Busco si el producto existe.
			// Si no se encontro el producto, se envia a erorrProdNoExiste.html, que redirige formAltaProd.jsp en caso de
			//que el usuario desee, sino se finaliza
			
			if (candela.productoEstaCargado(codigoProducto) == false) {
				candela_sesion.setAttribute("mensaje","El producto no existe, vuelva a intentarlo..." );
					xml.generarProductosNoAsociados();
					response.sendRedirect("formAsociarProdTomoEmbed.jsp");
			} else { //Si el producto existe

				//NOTA: IMPORTANTE Si un producto esta asociado directamente a un tomo de un catalogo qeu no esta en vigencia, entonces
				// se toma como que es un producto valido para asociarlo a un nuevo catalogo.
				

				Catalogo catVigente = candela.getCatalogoVigente();
				//Si el codigo de Producto figura en la coleccion de productos de otro tomo, ya esta asociado a otro tomo
				if (catVigente.estaProdAsocTomo(codigoProducto) == true) { 
					candela_sesion.setAttribute("mensaje", "El producto se encuentra asociado a otro tomo");
					xml.generarProductosNoAsociados();
						response.sendRedirect("formAsociarProdTomoEmbed.jsp");
				}
			}//FIn de IF de producto existe

			//Buscar y confirmar que el producto no este asociado al mismo tomo del catalogoNuevo 

			Catalogo catNuevo = null;
			if (candela.esCatalogoNuevo() == true) {
				catNuevo = candela.getCatalogoNuevo();
				if (catNuevo.estaProdAsocTomo(codigoProducto) == true) {
					xml.generarProductosNoAsociados();
					candela_sesion.setAttribute("mensaje", "El producto ya se encuentra asociado al tomo actual");
					response.sendRedirect("formAsociarProdTomoEmbed.jsp");
				}
			}

			//Si el producto existe, y no esta asociado al tomo nuevo ni a ninguno de los actuales ENTONCES:
			//Crear el Tomo y agregar el tomo a la coleccion de tomos del catalogoNuevo

			try {
				if (!response.isCommitted()) {
					//1.Se crea el tomo en memoria y en BD y se lo asigna al catalogo, en memoria y en BD.
					//1.1 Se crea el tomo en BD y en memoria y se lo asigna al catalogo.

				
					//Se recupera el numeroTomo de la sesion de Candela
					Integer codT = (Integer) candela_sesion
							.getAttribute("codigoTomo");
					int codigoTomo = codT.intValue();

				
					//2. Se asocia el producto al tomo en memoria y en BD.
					catalogo.AsignarProdTomo(codigoProducto,
							codigoTomo, candela.getColProductos(),
							candela.esCatalogoNuevo(), descripcion);
							
					
					if (opcion.equals("si")) {
						xml.generarTomosVigentes();
						xml.generarProductosNoAsociados();
						//Se tienen que almacenar los codigosProducto qeu se fueron asociados
						//en la sesion de Candela. Los codigosProducto sirven para eliminar los productos
						//del tomo dado de alta en memoria y en BD.
						
						ArrayList<Integer> codigosProducto=null;
						codigosProducto= (ArrayList<Integer>)candela_sesion.getAttribute("codigosProducto");
						if(codigosProducto==null){
							codigosProducto=new ArrayList<Integer>();
						}
						//Se añade el codigo de producto y se añade a la sesion de Candela
						codigosProducto.add(codigoProducto);
						candela_sesion.setAttribute("codigosProducto", codigosProducto);	
						
						response.sendRedirect("formAsociarProdTomoEmbed.jsp");
					}else {				
							response.sendRedirect("redirigir.jsp");
					}
				}

			} catch (TomoNoEncontradoException tne) {
				System.out
						.println("Error al buscar el tomo para asignarlo con un producto");
				//tne.mensajeDialogo("Error al buscar el tomo para asignarle el producto...");
				candela_sesion.setAttribute("mensaje", "Error al buscar el tomo para asignarlo con un producto");
				response.sendRedirect("formAltaTomoEmbed.jsp");

			} catch (SQLException sql) {
				//Si ocurre un error en BD se debe restablecer el catalogo anterior y eliminar el tomo
				//que quedo sin asignar.
				
				//NOTA: AGREGAR PARA QUE LLAME A "restablecerCat.jsp"!!!!
				
				System.out
						.println("Error al dar de alta el Tomo en la BD.");
				response.sendRedirect("../Error-O.jsp");
			}
			//Se creo correctamente el catalogo, con almenos un tomo y al menos un producto.

		} catch (NumberFormatException nfe) {
		
			candela_sesion.setAttribute("mensaje", 	"Error en el formato numérico");
			response.sendRedirect("formAsociarProdTomoEmbed.jsp");
		}

	}
%>