<%@page import="utilidades.GeneradorXML"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<%@page import="negocio.Candela"%>
<%@page import="negocio.Catalogo"%>
<%@page session="true"%>
<%
	/*Utilizo restablecerCat.jsp para restablecer la referencia del catalogoVigente con el catalogoActual
	 si un usuario cancela alta de tomo o AsociarProducto a Tomo */

	HttpSession candela_sesion = request.getSession();
	Candela cand = (Candela) candela_sesion.getAttribute("candela");
	boolean esCatNuevo=cand.esCatalogoNuevo();
	ArrayList<Integer> codigosTomo=(ArrayList<Integer>) candela_sesion.getAttribute("CodigosTomos");
	
	GeneradorXML gen= new GeneradorXML(cand);
	
	//Si se cancelo y no se dio de alta nigun
		
		if ( esCatNuevo == true) {
		
			cand.getCatalogoNuevo().cancelarAltaCatalogo(esCatNuevo,codigosTomo);			
			//Se reestablece la referenica de CatalogoNuevo a null.
			cand.setCatalogoNuevo(null);
				//Se elimina la coleccion de codigos de tomo en la sesion
			if(candela_sesion.getAttribute("CodigosTomos")!=null){
				candela_sesion.removeAttribute("CodigosTomos");
			}
			//Se actualizan los XML que son leidos por los demas CU
			//se actualiza el archivo xml que posee los tomos que que se pueden dar de baja		
			gen.generarProductosNoAsociados();
			gen.generarTomosVigentes();
			gen.generarXMLTomos();
			response.sendRedirect("../AltaCatalogo/formaltaCatalogoEmbed.jsp");
	
		} else {
			cand.getCatalogoVigente().cancelarAltaCatalogo(esCatNuevo,codigosTomo);
			//Se elimina la coleccion de codigos de tomo en la sesion
			if(candela_sesion.getAttribute("CodigosTomos")!=null){
				candela_sesion.removeAttribute("CodigosTomos");
			}
			//Se actualizan los XML que son leidos por los demas CU
			gen.generarProductosNoAsociados();
			//se actualiza el archivo xml que posee los tomos que que se pueden dar de baja
			gen.generarTomosVigentes();
			gen.generarXMLTomos();
			
			response.sendRedirect("../vistaOpDatos-catalogo.jsp");
		}
	
%>


<body>

</body>
</html>