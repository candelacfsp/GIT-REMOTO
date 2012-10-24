<%@page import="java.text.ParseException"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.text.ParsePosition"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*" %>
<%@page import="negocio.*" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
 
 
<% 
	HttpSession candela_sesion=request.getSession();
	Candela candela= (Candela) candela_sesion.getAttribute("candela");
	String fecha_inicio= request.getParameter("fecha_inicio");
	String fecha_fin= request.getParameter("fecha_fin");

	if( (fecha_inicio!=null && (!fecha_inicio.equals("") ) ) && (fecha_fin!=null && (!fecha_fin.equals("")) ) ){
	
		SimpleDateFormat fechas= new SimpleDateFormat("yyyy-MM-dd");
		
		Date fechaMenor=null;
		Date fechaMayor=null;
		try{
			fechaMenor= (Date)fechas.parse(fecha_inicio);
			fechaMayor=(Date) fechas.parse(fecha_fin);
			
			//Se crea un objeto fecha que representa a la fecha actual, por medio de GregorianCalendar.
			//Esta fecha se emplea para realizar la ultima comparacion, donde la fecha de inicio de vigencia
			//del catalogo se compara con la fecha actual del sistema.
			
			Calendar calActual=Calendar.getInstance();
			String s1= calActual.get(Calendar.YEAR)+"-"+(calActual.get(Calendar.MONTH)+1)+"-"+calActual.get(Calendar.DAY_OF_MONTH);
			Date diaActual=(Date)fechas.parse(s1);
			
			
			Calendar calIngresado= Calendar.getInstance();
			calIngresado.setTime(fechaMenor);
			fechaMenor=(Date)calIngresado.getTime();
			
			
			if(fechaMayor.before(fechaMenor)){
				%>
					<jsp:forward page="errorFFinMenorFInicio.html" /> 
				<% 
			}else{
				if(fechaMayor.equals(fechaMenor)){
				%>
					<jsp:forward page="errorFechasIguales.html" /> 
				<%
				}else{
				//se crea la fecha actual y se compara con la de inicio. La fecha de inicio no tiene que ser
				// menor a la fecha actual. Puede ser igual.
				
					boolean resultado=fechaMenor.before(diaActual);
					if(resultado==true){
						%>
				   			<jsp:forward page="errorFInicMenorFActual.html"/> 
						<%
					}
				}
			}
		
			//1.Se llama a candela que creara un  catalogoNuevo y lo mantendra en memoria, hasta que el registro del catalogo este completo con todos sus datos
			candela.altaCatalogo(fechaMenor); 
			
			//TODO Cuando se llama al comando response.sendRedirect(), se baja hacia el directorio en el que se encuentra la pagina JSP
			// por lo que si se quiere redireccionar a otra pagina que se encuentra mas arriba, se debe usar la sintaxis: ../, que sube un 
			// directorio en el arbol de directorios, de lo contrario Tomcat no encontrara el recurso.
		
			%>
				<jsp:forward page="../AltaTomo/formAltaTomo.jsp" /> 	
			<%
		}catch(ParseException pe){
			pe.printStackTrace();
			%>
				<jsp:forward page="errorFormatoFechas.jsp"/>
			<%
		}
		
}else{ %>
    
    
    
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
	width:101px;
	height:24px;
	z-index:1;
	left: 208px;
	top: 397px;
}
#apDiv3 {
	position:absolute;
	width:539px;
	height:163px;
	z-index:2;
	left: 322px;
	top: 169px;
}
#apDiv4 {
	position:absolute;
	width:416px;
	height:89px;
	z-index:3;
	left: 358px;
	top: 48px;
}
.Estilo1 {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 16px;
	color: #CC0000;
}
-->
</style>
<script src="../../../Scripts/AC_RunActiveContent.js" type="text/javascript"></script>
</head>

<body>
<div id="apDiv1">
  <div id="apDiv2">
    <script type="text/javascript">
AC_FL_RunContent( 'codebase','http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0','width','100','height','22','title','cancelar','src','button1','quality','high','pluginspage','http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash','movie','button1' ); //end AC code
</script><noscript><object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0" width="100" height="22" title="cancelar">
      <param name="movie" value="button1.swf" />
      <param name="quality" value="high" />
      <embed src="button1.swf" quality="high" pluginspage="http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash" type="application/x-shockwave-flash" width="100" height="22" ></embed>
    </object>
  </noscript></div>
  <div id="apDiv3">
    <form id="form1" name="form1" method="post" action="formAltaCatalogo.jsp">
      <table width="512" height="117" border="0">
        <tr>
          <td width="308"><strong><span class="Estilo1">Fecha de Inicio de vigencia del catálogo</span></strong></td>
          <td width="194"><div align="center">
            <input type="text" name="fecha_inicio" id="fecha_inicio" />
          </div></td>
        </tr>
        <tr>
          <td><strong><span class="Estilo1">Fecha de fin de vigencia del catálogo</span></strong></td>
          <td><div align="center">
            <input type="text" name="fecha_fin" id="fecha_fin" />
          </div></td>
        </tr>
        <tr>
          <td><div align="center">
            <input type="submit" name="botAceptar" id="botAceptar" value="Aceptar" />
          </div></td>
          <td><div align="center">
            <input type="reset" name="botRestablecer" id="botRestablecer" value="Restablecer" />
          </div></td>
        </tr>
      </table>
    </form>
  </div>
  <div id="apDiv4">
    <p align="center"><strong><span class="Estilo1">Ingrese las fecha de inicio de vigencia del catálogo y fecha de baja del catálogo. Las fechas ingresadas deben estar con el formato: anio-mes-dia, incluyendo los guiones; y la cantidad de caracteres debe ser la siguiente: aaaa-mm-dd</span></strong></p>
  </div>
</div>
</body>
<!-- Cuando se pulse el Boton ACEPTAR se debe llamar al .JSP que una vez validadas las fechas redirigira al usuario directamente--> <!-- hacia el formulario de formAltaTomo.html, sin que el usuario lo llame explicitamente. -->
</html>
 
<% } %>