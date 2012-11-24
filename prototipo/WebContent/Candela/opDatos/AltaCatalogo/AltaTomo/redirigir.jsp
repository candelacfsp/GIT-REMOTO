<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%@page session="true"%>
	<%@page import="negocio.*"%>
	<%@page import="utilidades.GeneradorXML"%>
	<%
		/*redirigir.jsp tiene como objetivo hacer las tareas finales de asignacion de catalogo, 
		se encarga de generar el historico y generar los XML con los productos y tomos del nuevo catalogo.
		Y de eliminar el catalogoVigente  y reemplazarlo por el catalogoNuevo.
		 */

		HttpSession candela_sesion = request.getSession();
		Candela candela = (Candela) candela_sesion.getAttribute("candela");
		GeneradorXML gen = new GeneradorXML(candela);
		
		
		//Generar un XML que guarda un snapshot del catalogo actual.
		gen.generarHistorico();
		
		
		
		//Se desasocian todos los productos de los tomos (en memoria y en BD) que estan en el catalogo
		//vigente, se borran todas las tuplas de los tomos y se eliminan todas las tuplas de catalogos anteriores
		//en el caso de que hubiesen mas de 2 tuplas
		Catalogo catVig = candela.getCatalogoVigente();
		catVig.delTomosCatVig(); 
		
		//BACK-UP VERSION VIEJA
		//Se recorre la coleccion de productos en el catalogoVigente y se los desasocia
		//en memoria y en BD.
		/* Catalogo catVig = candela.getCatalogoVigente();
		ArrayList<Producto> prodMemoria = candela.getProductos();
		for (int i = 0; i < prodMemoria.size(); i++) {
			System.out.println(prodMemoria.get(i).getCodigo());
			if (catVig.estaProdAsocTomo(prodMemoria.get(i).getCodigo())) {
				
				catVig.desasignarProdTomo(prodMemoria.get(i)
						.getCodigo());
			}
		} */
		
		
		//Como es un catalogoNuevo, tiene que sustiruirse el catalogoVigente por el 
		//catalogoNuevo en memoria.
		//1 Asignar a la referenica de CatalogoVigente, la referencia del CatalogoNuevo.
		candela.setCatalogoVigente(candela.getCatalogoNuevo());
		//2.Se elimina la referencia del catalogo nuevo.
		candela.setCatalogoNuevo(null);
	
		//Generar los xmls actualizados del catalogo
		gen.generarXMLProductos();
		gen.generarTomosVigentes();
		gen.generarProductosNoAsociados();
		gen.generarXMLTomos();

		//Remover atributos de la sesion
		candela_sesion.removeAttribute("descripTomo");
		candela_sesion.removeAttribute("codigoTomo");
		candela_sesion.removeAttribute("codigosProducto");
		response.sendRedirect("../../vistaOpDatos-catalogo.jsp");
		
		//TODO MODIFICADO RODRIGO 23-11-2012
		
				//VERSION VIEJA QUE DETECTA SI EL CATALOGO ES NUEVO O ES VIEJO Y REDIRIJE A UNA VISTA 
				//DETERMINADA SEGUN EL TIPO DE CATALOGO.
				//Se emplea una variable booleana para revisar si debe redirigirse al alta de catalogo o
				// alta de tomo. Si esta var es verdadera se redirige a Catalogo, sino se redirige a altaTomo
				/*boolean redirigirAAltaCatalogo;
				GeneradorXML gen = new GeneradorXML(candela);
				if (candela.esCatalogoNuevo() == true) {
					redirigirAAltaCatalogo = true;

					//Generar un XML que guarda un snapshot del catalogo actual.

					gen.generarHistorico();
					Catalogo catVig = candela.getCatalogoVigente();
					ArrayList<Producto> prodMemoria = candela.getProductos();
					for (int i = 0; i < prodMemoria.size(); i++) {
						System.out.println(prodMemoria.get(i).getCodigo());
						if (catVig.estaProdAsocTomo(prodMemoria.get(i).getCodigo())) {
							catVig.desasignarProdTomo(prodMemoria.get(i)
									.getCodigo());
						}
					}
					//En el caso de que sea un catalogoNuevo, tiene que sustiruirse el catalogoVigente por el 
					//catalogoNuevo en memoria.

					//1 Asignar a la referenica de CatalogoVigente, la referencia del CatalogoNuevo.
					candela.setCatalogoVigente(candela.getCatalogoNuevo());
					//2.Se elimina la referencia del catalogo nuevo.
					candela.setCatalogoNuevo(null);

				} else {
					redirigirAAltaCatalogo = false;

				}*/
	%>

</body>
</html>