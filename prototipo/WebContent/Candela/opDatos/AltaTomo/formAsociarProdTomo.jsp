<%@page import="net.java.ao.EntityManager"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@page import="utilidades.*" %>
<%@page import="persistencia.*" %>

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
	height:23px;
	z-index:1;
	left: 174px;
	top: 345px;
}
#apDiv3 {
	position:absolute;
	left:256px;
	top:91px;
	width:438px;
	height:49px;
	z-index:2;
}
.Estilo1 {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 16px;
	color: #CC0000;
}
#apDiv4 {
	position:absolute;
	width:200px;
	height:115px;
	z-index:2;
	left: 306px;
	top: 173px;
}
.Estilo2 {font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #CC0000; font-weight: bold; }
-->
</style>
<script src="../../../Scripts/AC_RunActiveContent.js" type="text/javascript"></script>
</head>

<%@page import="negocio.*" %>
<%@page session="true"%>



<% 
	HttpSession candela_sesion= request.getSession();
	Candela candela=(Candela)candela_sesion.getAttribute("candela");

	String codT= request.getParameter("codigoTomo");
	String codP= request.getParameter("codProducto");
	String descripcion=request.getParameter("descripTomo");
	
	Catalogo catalogo= (Catalogo) candela_sesion.getAttribute("catalogoNuevo");
	
	if(codP!=null && (!codP.equals(""))){
		System.out.println("El codigo de tomo es: "+codT+" y el codigo de prod es: "+codP +" y la descripcion es: "+descripcion);
		int codigoProducto=Integer.parseInt(codP);
		int codigoTomo= Integer.parseInt(codT);
			
		//1.Buscar el Producto que ingreso el usuario por medio de los Productos que tiene Candela
		//y mostrar los datos del producto(investigar, como generar HTML accediendo a <%= VALOR! % desde JSP). Considerar los casos de Error: El producto existe,
		//El producto se encuentra ya asociado al mismo tomo o a otro tomo.
		
		//1.0 Busco si el producto existe, sino lo redirijo a errorProdNoExiste.html
		
		ArrayList<Producto> prods= candela.getProductos();
		int i=0;
		while( i<prods.size() && (prods.get(i).getCodigo()!=codigoProducto)){
			i++;
		}
		if(i>=prods.size()){// Si no se encontro el producto, se envia a erorrProdNoExiste.html, que redirige formAltaProd.jsp en caso de
							//que el usuario desee, sino se finaliza
			
		%>
			<jsp:forward page="errorProdNoExiste.html" /> 
		<%
		}else{ //Si el producto existe
		
		//1.1.Busco por cada Producto si el producto esta asociado a algun tomo vigente (como candela conoce los productos, no es necesario buscarlo directamente en la BD)	
		
		ArrayList<Tomo> tomos= candela.getCatalogoVigente().getTomos();
	
		int k;  		
		for(k=0; k<tomos.size() ; k++){ // Se comienza a recorrer los tomos de candela, y verificando que el producto no este asociado a algun tomo
			ArrayList<Producto> productos= tomos.get(k).getProductos();

			for(int j=0; j<productos.size(); j++){
				
				if(productos.get(j).getCodigo()==codigoProducto){ //Si el codigo de Producto figura en la coleccion de productos de otro tomo, ya esta asociado a otro tomo
					
				 	 
					%>
						<jsp:forward page="errorProdAsocOtroTomo.html" /> 				
					
					<%
					}
				}
				
			}
		//Buscar y confirmar que el producto no este asociado al mismo tomo del catalogoNuevo 
		ArrayList<Tomo> tomosNuevoCat= catalogo.getTomos();
 		
		int postomoNuevo;
		for(postomoNuevo=0; postomoNuevo<tomosNuevoCat.size();postomoNuevo++){
			
			if(tomosNuevoCat.get(postomoNuevo).getCodigoTomo()==codigoTomo){// Si es el mismo tomo que me ingresaron, entonces obtener productos
				
				ArrayList<Producto> prods1= tomosNuevoCat.get(postomoNuevo).getProductos();
					
					for(int j=0; j<prods1.size(); j++){
						
						if(prods1.get(j).getCodigo()==codigoTomo){  //Si es es mismo producto ingresado, entonces ya esta asociado al tomo
							%>
							 <jsp:forward page="errorProdAsocAlTomo.html" />
							<% 
						}
					} 
			}
		}
		//Si el producto existe, y no esta asociado al tomo nuevo ni a ninguno de los actuales ENTONCES:
			//Crear el Tomo y agregar el tomo a la coleccion de tomos del catalogoVigente
			
			Tomo tom1= new Tomo(candela.getEm());
			tom1.setCodigoTomo(codigoTomo);
			tom1.setDescripcion(descripcion);
			catalogo.getTomos().add(tom1);
			System.out.println("Se creo el tomo en la sesion");
			
			//2. Agregar el tomo a la Base de Datos
			
			/*int resultado= catalogo.altaTomo(codigoTomo, descripcion);					
			
			if(resultado==Constantes.ALTA_TOMO_OK){
				System.out.println("Se dio de alta el tomo en la BD");
			}else{
				System.out.println("Error al dar de alta el tomo en la BD: Error SQL");
				System.exit(Constantes.EXCEPCION_SQL);
			}
			
	*/

		
		
			//1.Buscar el tomo cargado recientemente y asignarle el producto en memoria
			int postomo=0;
			while(postomo<tomosNuevoCat.size()&& (tomosNuevoCat.get(postomo).getCodigoTomo()!=codigoTomo)){
				postomo++;
			}
			//1.1 Buscar el Producto de la coleccion de productos de Candela, leerlo y asignarselo al Tomo del catalogoNuevo
			
			int posprod=0;
			while(postomo<prods.size()&& (prods.get(posprod).getCodigo()!=codigoProducto)){
				posprod++;
			}
			
			tomosNuevoCat.get(postomo).getProductos().add(prods.get(posprod));
			
			
			//2. Si es catalogo Nuevo: Dar de alta el Catalogo en la BD, buscar el tomoBD para asignarle el catalogo creado y finalmente asociar producto con tomo
			// Si no es catalogo Nuevo: Buscar el catalogoVigente en la BD, asociar el tomo al catVigente y asociar el producto con el tomo en la BD
			
			
			
			//2.1- Determinar si el Alta de Tomo se llamo de un alta de catalogo, o es un alta de tomo simple, comparando las rereferenicas de los catalogos
 			//Si es un alta de catalogo: escribir el nuevo catalogo en la BD y asignarle el tomo
			//Sino: buscar el catalogo en la BD y asignarle el tomo 
 			EntityManager em= candela.getEm();
			
 			//Defino una var temporal catalogoBD para recuperar el ID del catalogoVigente
			CatalogoBD catTemp[]= em.find(CatalogoBD.class);
			int COD_CAT_VIGENTE= catTemp[catTemp.length-1].getID();
			
			CatalogoBD [] cat1= new CatalogoBD[2];
			if(candela.getCatalogoVigente()!=candela_sesion.getAttribute("catalogoNuevo")){ //Si las referencias son <>, el alta tomo se llamo de un alta catalogo
				cat1[0]=em.create(CatalogoBD.class);
				cat1[0].setAnioVigencia(2012);
				cat1[0].save();
				
			}else{ //Sino, recuperar el catalogo vigente de la BD
				
				
				cat1=em.find(CatalogoBD.class,"id=?", COD_CAT_VIGENTE);

			}
			//2.2 Buscar el tomo en la BD y setearle el catalogoVigente
			
				TomoBD [] t1= em.find(TomoBD.class,"codigotomo= ?",codigoTomo);
				t1[0].setCatalogoBD(cat1[0]);
				t1[0].save();	
			
 			//2.3 Asignar el producto al Tomo en la BD, por medio de metodo :asignarProdTomo() 
			tomosNuevoCat.get(postomo).AsignarProdTomo(codigoProducto, codigoTomo,descripcion);					
						
			//2.4 Pisar el catalogoVigente en memoria con el catalogo de la Sesion (CatalogoNuevo)
			// y cambiar la Variable Global que tiene el numero de TomoActual
					candela.setCatalogoVigente(catalogo);
				 	//COD_CAT_VIGENTE=cat1[0].getID(); // Usando el objeto persistente cat1 que utilice para guardar el cat en la BD, obtener el ID del mismo
		
					%>
					<jsp:forward page="formProductoCorrecto.html" /> 	
				
					<%
		
		}
		
	
		}else{
	%>

<body>
<div id="apDiv1">
  <div id="apDiv2">
    <script type="text/javascript">
AC_FL_RunContent( 'codebase','http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0','width','100','height','22','title','cancelar','src','button8','quality','high','pluginspage','http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash','movie','button8' ); //end AC code
    </script>
    <noscript>
    <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0" width="100" height="22" title="cancelar">
      <param name="movie" value="button8.swf" />
      <param name="quality" value="high" />
      <embed src="button8.swf" quality="high" pluginspage="http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash" type="application/x-shockwave-flash" width="100" height="22" ></embed>
    </object>
    </noscript>
  </div>
  <div id="apDiv4">
  
  						 
    <form id="form1" name="form1" method="get" action="formAsociarProdTomo.jsp">
      <table width="300" border="0">
        <tr>
          <td width="165" class="Estilo2">Codigo de Producto:</td>
          <td width="125"><input type="text" name="codProducto" id="codProducto" /></td>
        </tr>        
        <tr>
          <td colspan="2"><div align="center">
            <input type="submit" name="botAceptar" id="botAceptar" value="Aceptar" />
          </div>            <div align="center"></div></td>
        </tr>
      </table>
      	 <input type="hidden" name=codigoTomo value=<%=request.getParameter("codigoTomo") %>>
      	 <input type="hidden" name=descripTomo value=<%=request.getParameter("descripTomo") %>>
    </form>
    <a href="restablecerCat.jsp"><b>CANCELAR ASOCIACION DE PRODUCTO!</b></a>
  </div>
</div>
<div id="apDiv3"><strong><span class="Estilo1">Tomo creado correctamente! Usted Ahora debe añadir productos al tomo. Seleccione el codigo del primer Producto a asociar y presione Aceptar</span></strong></div>
</body>
</html>
 
		
		
<%	}	%>