<%@page import="java.util.ArrayList"%>
<%@page import="negocio.Candela"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@page import= "utilidades.*" %>
<%@page import="negocio.*" %>
<%@page session="true" %>

 <%
 
 	//NOTA IMPOTANTE: Se distingue entre la llamada al AltaTomo.jsp simple y la llamada a AltaTomo.jsp desde AltaCatalogo.jsp,
 	//mantieniendo la referenica a null del catalogoNuevo.
 	// Si esta referencia es null, significa que la llamada fue hecha desde el AltaTomo, en cambio si es <> de null significa que fue hecha
 	// desde un altaTomo comun.
 	
 	//Hay que tener en cuenta que si se cancela un alta de Catalogo (en cualquier punto), debe llamarse a un ResetCatNuevo.jsp, que setee la referencia
 	// de catalogoNuevo de Candela a null. En cada pagina de ERROR.HTML, se tiene un hipervinculo que redirige a un ResetCatNuevo.jsp que se encarga de ello.
 	
 	HttpSession candela_sesion= request.getSession();
 	
 	Candela candela=(Candela)candela_sesion.getAttribute("candela");
	String nroTomo= request.getParameter("codigoTomo");
 	String descripcion=request.getParameter("descripcion");
 	
 	
	if(nroTomo!=null && (!nroTomo.equals(""))){ 						 
		int nro_tomo= Integer.parseInt(nroTomo);
		
		//1. Hay que validar que el tomo no Exista en el CatalogoVigente de Candela. Para ello, se pide el catalogoVigente a 
		//Candela y se leen los tomos y se busca y confirma que  el tomo a agregar no este en Candela.
		
		//Catalogo catalogo=(Catalogo)candela_sesion.getAttribute("catalogoNuevo");
		
		Catalogo catalogo=null;
		if(candela.getCatalogoNuevo()!= null){//Si la referencia es null, significa que AltaTomo se llamo desde el menu del usuario, no desde el AltaCatalogo.jsp
			 catalogo=(Catalogo)candela.getCatalogoNuevo();
				
		}else{
			catalogo=candela.getCatalogoVigente();
		}
		
		
		if ( candela.getCatalogoVigente().tomoExiste(nro_tomo)==false){ //Si el tomo no esta en la coleccion de tomos del catalogoVigente de Candela, se obtiene el catalogo nuevo y se añade un nuevo tomo
			candela_sesion.setAttribute("codigoTomo", new Integer(nro_tomo));
		%>	
				<jsp:forward page="formDescripcionTomo.jsp" />
		<%	 
		}else{
			//Si el tomo Existe, se genera un error
			%>
				<jsp:forward page="errorTomoExiste.swf" /> 
			<%
		} 
	
		
		//Se da de alta el tomo y se lo asigna al catalogo nuevo o vigente.
		catalogo.altaTomo(nro_tomo, descripcion,candela.esCatalogoNuevo());
		
	
		//Guardar la descripcion en la session, ya que si se pasa de nuevo se pierde parte de la 
		//descripcion
		candela_sesion.setAttribute("descripTomo", descripcion);
		
		
		//Genero un XML con los productos Libres llamado: productosDisponibles.xml.
		GeneradorXML gen=new GeneradorXML(candela);
		gen.generarProdCatNoAsociados();
		
		
			%>
				<jsp:forward page="formAsociarProdTomo.swf"/>
				<!-- <jsp:forward page="formAsociarProdTomo.jsp"/> --> 
			<%	
	}else{//Sino se llama  al swf.	
	%>
		<jsp:forward page="formAltaTomo.swf"/>
		
<% 		}	%>
    

