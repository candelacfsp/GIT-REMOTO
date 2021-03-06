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

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="negocio.*" %>
<%@page session="true" %>
<%@page import="java.util.*" %>
<%@page import="utilidades.Constantes" %>

		<%
	 	HttpSession candela_sesion= request.getSession();
	 	Candela candela=(Candela)candela_sesion.getAttribute("candela");
		
		//NOTA: El numeroTomo se pasa directamente desde la formAltaTomo.jsp a formDescripcionTomo.jsp, ya que todavia
		// no se tiene informacion suficiente para poder crearlo y asignarlo al catalogo.
	
	 	//String nroT= request.getParameter("codigoTomo");	 
		String descripcion= request.getParameter("descripTomo");
		
		
		if(descripcion!=null){
			//Se obtiene el codigo de tomo de la sesion de Candela.
			Integer codT= (Integer) candela_sesion.getAttribute("codigoTomo");
			int codigoTomo=codT.intValue();
			
					
			//Se obtiene el catalogo al que se le esta asignando el tomo(nuevo o vigente)
			Catalogo catalogo=null;
			
			if(candela.esCatalogoNuevo()==true){
				catalogo=candela.getCatalogoNuevo();
			}else{
				catalogo=candela.getCatalogoVigente();
			}
			
			//Se da de alta el tomo y se lo asigna al catalogo nuevo o vigente.
			catalogo.altaTomo(codigoTomo, descripcion,candela.esCatalogoNuevo());
			
			//int nroTomo= Integer.parseInt(nroT);
			//Guardar la descripcion en la session, ya que si se pasa de nuevo se pierde parte de la 
			//descripcion
			candela_sesion.setAttribute("descripTomo", descripcion);
				%>
					<jsp:forward page="formAsociarProdTomo.jsp"/> 
				<%	
		 }else{
		%>


<body>
<div id="apDiv1">
  <div id="apDiv2"><strong><span class="Estilo1">Ingrese la descripcion del tomo</span></strong></div>
  <div id="apDiv3">
    <form id="form1" name="form1" method="get" action="formDescripcionTomo.jsp">
      <table width="363" height="39" border="0">
        <tr>
          <td width="145" class="Estilo2">Descripción de tomo</td>
          <td width="208"><textarea name="descripTomo" id="descripTomo"rows="5" cols="10"></textarea></td>
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
      <input type="hidden" name=codigoTomo value=<%=request.getParameter("codigoTomo") %>>
      <input type="hidden" name=descripTomo value=<%=request.getParameter("descripTomo") %>>
    </form>
  </div>
  <div id="apDiv4">
    <script type="text/javascript">
AC_FL_RunContent( 'codebase','http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0','width','108','height','26','title','regresar','src','button4','quality','high','pluginspage','http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash','movie','button4' ); //end AC code
</script><noscript><object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0" width="108" height="26" title="regresar">
      <param name="movie" value="button4.swf" />
      <param name="quality" value="high" />
      <embed src="button4.swf" quality="high" pluginspage="http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash" type="application/x-shockwave-flash" width="108" height="26" ></embed>
    </object>
  </noscript></div>
</div>
		<a href="restablecerCat.jsp" align="center"><b>CANCELAR ALTA TOMO!</b></a>
</body>
</html>

<% } %>
