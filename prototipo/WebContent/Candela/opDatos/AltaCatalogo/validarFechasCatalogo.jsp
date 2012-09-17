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
	String fecha_fin= request.getParameter("fecha_fin");

	if( fecha_fin!=null && (!fecha_fin.equals("")) ){
	
		SimpleDateFormat fechas= new SimpleDateFormat("yyyy-MM-dd");
		
		Date fechaMayor=null;
		try{
			fechaMayor=(Date) fechas.parse(fecha_fin);
			
			//Se crea un objeto fecha que representa a la fecha actual, por medio de GregorianCalendar.
			//Esta fecha se emplea para realizar la ultima comparacion, donde la fecha de inicio de vigencia
			//del catalogo se compara con la fecha actual del sistema.
			
			Calendar calActual=Calendar.getInstance();
			String s1= calActual.get(Calendar.YEAR)+"-"+(calActual.get(Calendar.MONTH)+1)+"-"+calActual.get(Calendar.DAY_OF_MONTH);
			Date diaActual=(Date)fechas.parse(s1);
	
			//Se crea la fecha actual y se compara con la de inicio. La fecha de inicio no tiene que ser
			// menor a la fecha actual. Puede ser igual.
				
				boolean resultado=fechaMayor.before(diaActual);
				if(resultado==true){
					%>
			   			<jsp:forward page="errorFInicMenorFActual.swf"/> 
					<%
				}
		
			//1.Se llama a candela que creara un  catalogoNuevo y lo mantendra en memoria, hasta que el registro del catalogo este completo con todos sus datos
			candela.altaCatalogo(fechaMayor); 
			
			//TODO Cuando se llama al comando response.sendRedirect(), se baja hacia el directorio en el que se encuentra la pagina JSP
			// por lo que si se quiere redireccionar a otra pagina que se encuentra mas arriba, se debe usar la sintaxis: ../, que sube un 
			// directorio en el arbol de directorios, de lo contrario Tomcat no encontrara el recurso.
		
			
			response.sendRedirect("../AltaTomo/formAltaTomo.swf");
		}catch(ParseException pe){
			pe.printStackTrace();
			%>
				<jsp:forward page="errorFormatoFechas.swf"/>
			<%
		}
		
	}  %>