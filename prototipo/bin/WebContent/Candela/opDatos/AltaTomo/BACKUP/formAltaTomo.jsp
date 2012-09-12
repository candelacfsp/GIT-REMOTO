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
				<jsp:forward page="errorTomoExiste.html" /> 
			<%
		} 
	
	}else{%>
    

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Documento sin t&iacute;tulo</title>
<style type="text/css">
<!--
#apDiv1 {
	position:absolute;
	left:0%;
	top:0%;
	width:100%;
	height:100%;
	z-index:1;
}
#apDiv2 {
	position:absolute;
	width:262px;
	height:74px;
	z-index:1;
	left: 378px;
	top: 57px;
}
.Estilo1 {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 24px;
	color: #CC0000;
}
#apDiv3 {
	position:absolute;
	width:384px;
	height:112px;
	z-index:2;
	left: 332px;
	top: 172px;
}
.Estilo2 {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 16px;
	color: #CC0000;
	font-weight: bold;
}
#apDiv4 {
	position:absolute;
	width:106px;
	height:27px;
	z-index:3;
	left: 145px;
	top: 299px;
}
-->
</style>
<script src="../../../Scripts/AC_RunActiveContent.js" type="text/javascript"></script>
</head>

<body>
<div id="apDiv1">
  <div id="apDiv2"><strong><span class="Estilo1">Ingrese el número de tomo a crear</span></strong></div>
  <div id="apDiv3">
    <form id="form1" name="form1" method="get" action="../AltaTomo/formAltaTomo.jsp">
      <table width="363" height="39" border="0">
        <tr>
          <td width="145" class="Estilo2">Número de tomo</td>
          <td width="208"><input type="text" name="codigoTomo" id="codigoTomo" /></td>
        </tr>
      </table>
      <table width="363" height="36" border="0">
        <tr>
          <td width="146"><div align="center">
            <input type="submit" name="botAcept" id="botAcept" value="Aceptar" />
          </div></td>
          <td width="207"><div align="center">
            <input type="reset" name="botRechazar" id="botRechazar" value="Restablecer" />
          </div></td>
        </tr>
      </table>
    </form>
    <a href="restablecerCat.jsp"><b>CANCELAR ALTA TOMO!</b></a>
  </div>
  <div id="apDiv4">
    <script type="text/javascript">
AC_FL_RunContent( 'codebase','http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0','width','105','height','27','title','cancelar','src','button7','quality','high','pluginspage','http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash','movie','button7' ); //end AC code
</script><noscript><object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0" width="105" height="27" title="cancelar">
      <param name="movie" value="button7.swf" />
      <param name="quality" value="high" />
      <embed src="button7.swf" quality="high" pluginspage="http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash" type="application/x-shockwave-flash" width="105" height="27" ></embed>
    </object>
  </noscript></div>
</div>
</body>
</html>
<%} %>
    