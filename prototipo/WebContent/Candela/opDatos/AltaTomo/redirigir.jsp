<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%@page session="true" %>
<%@page import="negocio.Candela" %>
<%@page import="utilidades.GeneradorXML" %>
<%
	/*redirigir.jsp tiene como objetivo hacer las tareas finales de asignacion de catalogo, y
		verifica si debe redirigir a un form de alta tomo o de alta catalogo, en funcion de si
		el catalogoNuevo es null o no.
	*/
	
	HttpSession candela_sesion=request.getSession();
	Candela candela=(Candela)candela_sesion.getAttribute("candela");
	
	
	//Se emplea una variable booleana para revisar si debe redirigirse al alta de catalogo o
	// alta de tomo. Si esta var es verdadera se redirige a Catalogo, sino se redirige a altaTomo
	boolean redirigirAAltaCatalogo;
	if(candela.esCatalogoNuevo()==true){
		redirigirAAltaCatalogo=true;
		
		//Generar un XML que guarda un snapshot del catalogo actual.
		
		GeneradorXML gen= new GeneradorXML(candela);
		gen.generarHistorico();

	}else{
		redirigirAAltaCatalogo=false;
		//En el caso de que sea un catalogoNuevo, tiene que sustiruirse el catalogoVigente por el 
		//catalogoNuevo en memoria.
		
		//1 Asignar a la referenica de CatalogoVigente, la referencia del CatalogoNuevo.
		candela.setCatalogoVigente(candela.getCatalogoNuevo());
		//2.Se elimina la referencia del catalogo nuevo.
		candela.setCatalogoNuevo(null);
	}
	
	//Remover atributos de la sesion
	candela_sesion.removeAttribute("descripTomo");
	candela_sesion.removeAttribute("cdigoTomo");
	response.sendRedirect("../vistaOpDatos.swf");
	
%>

</body>
</html>