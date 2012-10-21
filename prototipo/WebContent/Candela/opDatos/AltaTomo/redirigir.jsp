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
		/*redirigir.jsp tiene como objetivo hacer las tareas finales de asignacion de catalogo, y
			verifica si debe redirigir a un form de alta tomo o de alta catalogo, en funcion de si
			el catalogoNuevo es null o no.
		 */

		HttpSession candela_sesion = request.getSession();
		Candela candela = (Candela) candela_sesion.getAttribute("candela");

		//Se emplea una variable booleana para revisar si debe redirigirse al alta de catalogo o
		// alta de tomo. Si esta var es verdadera se redirige a Catalogo, sino se redirige a altaTomo
		boolean redirigirAAltaCatalogo;
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

		}
		//Generar los xmls actualizados del catalogo
		gen.generarXMLProductos();
		gen.generarTomosVigentes();
		gen.generarProductosNoAsociados();
		gen.generarXMLTomos();

		//Remover atributos de la sesion
		candela_sesion.removeAttribute("descripTomo");
		candela_sesion.removeAttribute("codigoTomo");
		response.sendRedirect("../vistaOpDatos-catalogo.jsp");
	%>

</body>
</html>