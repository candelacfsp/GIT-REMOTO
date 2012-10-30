<%@page import="javax.swing.JOptionPane"%>
<%@page import="java.util.ArrayList"%>
<%@page import="negocio.Candela"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="utilidades.*"%>
<%@page import="negocio.*"%>
<%@page session="true"%>

<%
	//NOTA IMPOTANTE: Se distingue entre la llamada al AltaTomo.jsp simple y la llamada a AltaTomo.jsp desde AltaCatalogo.jsp,
	//mantieniendo la referenica a null del catalogoNuevo.
	// Si esta referencia es null, significa que la llamada fue hecha desde el AltaTomo, en cambio si es <> de null significa que fue hecha
	// desde un altaTomo comun.

	//Hay que tener en cuenta que si se cancela un alta de Catalogo (en cualquier punto), debe llamarse a un ResetCatNuevo.jsp, que setee la referencia
	// de catalogoNuevo de Candela a null. En cada pagina de ERROR.HTML, se tiene un hipervinculo que redirige a un ResetCatNuevo.jsp que se encarga de ello.

	HttpSession candela_sesion = request.getSession();

	Candela candela = (Candela) candela_sesion.getAttribute("candela");
	String nroTomo = request.getParameter("codigoTomo");
	String descripcion = request.getParameter("descripcion");

	if (nroTomo != null && (!nroTomo.equals(""))) {
		int nro_tomo = Integer.parseInt(nroTomo);

		//1. Hay que validar que el tomo no Exista en el CatalogoVigente de Candela. Para ello, se pide el catalogoVigente a 
		//Candela y se leen los tomos y se busca y confirma que  el tomo a agregar no este en Candela.

		Catalogo catalogo = null;
		if (candela.getCatalogoNuevo() != null) {//Si la referencia es null, significa que AltaTomo se llamo desde el menu del usuario, no desde el AltaCatalogo.jsp
			catalogo = (Catalogo) candela.getCatalogoNuevo();

		} else {
			catalogo = candela.getCatalogoVigente();
		}
		//Si el catalogo no existe
		if (catalogo.getAnioVigencia() == Constantes.ERROR) {
			JOptionPane
					.showMessageDialog(
							null,
							"No puede dar de alta un tomo sin dar de alta un catalogo previamente.Antes de intentar dar de alta un tomo, dirijase al Alta de Cátalogo.");
			response.sendRedirect("../vistaOpDatos-catalogo.jsp");
		} else {

			if (candela.getCatalogoVigente().tomoExiste(nro_tomo) == false) { //Si el tomo no esta en la coleccion de tomos del catalogoVigente de Candela, se obtiene el catalogo nuevo y se añade un nuevo tomo
				candela_sesion.setAttribute("codigoTomo", new Integer(
						nro_tomo));

			} else {
				//Si el tomo Existe, se genera un error
				JOptionPane panel = new JOptionPane();
				panel.showMessageDialog(null,
						"El tomo existe!, ingrese otro.");
				response.sendRedirect("formAltaTomoEmbed.jsp");
			}

			//Se da de alta el tomo y se lo asigna al catalogo nuevo o vigente.
			if (!response.isCommitted()) {
				ArrayList<Producto> colProductos = candela
						.getColProductos();
				GeneradorXML gen = new GeneradorXML(candela);
				//sumatoria sirve para contar la cantidad de productos asociados a los tomos
				//del catalogo vigente y los compara
				//con los productos de candela.
				
				int sumatoria = 0;

				for (int j = 0; j < candela.getCatalogoVigente()
						.getTomos().size(); j++) {
					sumatoria += candela.getCatalogoVigente()
							.getTomos().get(j).getProductos().size();
				}
				if (colProductos.size() > sumatoria) {

					catalogo.altaTomo(nro_tomo, descripcion,
							candela.esCatalogoNuevo());

					//Guardar la descripcion en la session, ya que si se pasa de nuevo se pierde parte de la 
					//descripcion
					candela_sesion.setAttribute("descripTomo",
							descripcion);

					//Genero un XML con los productos Libres llamado: productosDisponibles.xml.

					//gen.generarProdCatNoAsociados();
					//ver esto 9 - 9 -12
					gen.generarProductosNoAsociados();
					//se actualiza el archivo xml que posee los tomos que que se pueden dar de baja
					gen.generarTomosVigentes();
					

					response.sendRedirect("formAsociarProdTomoEmbed.jsp");
				} else {
					candela.setCatalogoNuevo(null);
					JOptionPane
							.showMessageDialog(
									null,
									"Debe existir al menos un producto para asignar al tomo, antes de crearlo");
					response.sendRedirect("../vistaOpDatos-catalogo.jsp");
				}
			} else {//Sino se llama  al swf.	
				if (!response.isCommitted()) {
					response.sendRedirect("formAltaTomoEmbed.jsp");
				}
			}
		}//Fin de if catalogo no existe
	}
%>


