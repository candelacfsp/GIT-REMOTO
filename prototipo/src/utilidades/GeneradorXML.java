package utilidades;


import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import persistencia.TipoDeProductoBD;
import persistencia.TipoDeUsrBD;
import persistencia.usuarioBD;
import utilidades.GeneradorXML.Lista.ColMeses;

import negocio.Candela;
import negocio.Catalogo;
import negocio.DetallePedidoPersonal;
import negocio.FacturaFabrica;
import negocio.FacturaPersonal;
import negocio.PedidoFabrica;
import negocio.PedidoPersonal;
import negocio.Producto;
import negocio.TipoDeProducto;
import negocio.Tomo;
import negocio.Usuario;

public class GeneradorXML {
	private Candela candela;
	private String directorioActual;
	
	public GeneradorXML(Candela candela){
		this.candela=candela;
		this.directorioActual= candela.getDirectorio();
	}
	
	//TODO el filtrado de los xmls se debe realizar desde AS2
	public  void generarXMLProductos(){
		ArrayList<Producto> colProductos = candela.getProductos();
		Element root = new Element("Productos");
		for (int i = 0; i < colProductos.size(); i++) {
			Element producto = new Element("Producto");
			producto.setAttribute("codigo",
					Integer.toString(colProductos.get(i).getCodigo()));
			producto.setAttribute("descripcion", colProductos.get(i)
					.getDescripcion());
			producto.setAttribute("precio",
					Double.toString(colProductos.get(i).getPrecio()));
			producto.setAttribute("cantidad", Integer.toString(colProductos
					.get(i).getCantidadEnStock()));
			producto.setAttribute("tipoProducto",Integer.toString(colProductos.get(i).getTipoProducto()));

			root.addContent(producto);
		}

		Document doc = new Document(root);//Creamos el documento



		try {


			XMLOutputter salida = new XMLOutputter(Format.getPrettyFormat());
			FileOutputStream file = new FileOutputStream(directorioActual+"productos.xml");
			salida.output(doc, file);
			file.flush();
			file.close();
			salida.output(doc, System.out);


		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto");

			e.printStackTrace();
		}
	}
	public void generarXMLTipoDeProductos(){
		ArrayList<TipoDeProductoBD> colTipoDeProducto = candela.getColTipoDeProducto();
		Element root = new Element("Tipos_De_Productos");
		for (int i = 0; i < colTipoDeProducto.size(); i++) {
			Element tipoDeProducto = new Element("TipoDeProducto");
			tipoDeProducto.setAttribute("codigo",
					Integer.toString(colTipoDeProducto.get(i).getCodTipoProd()));
			tipoDeProducto.setAttribute("descripcion", colTipoDeProducto.get(i)
					.getDescripcion());


			root.addContent(tipoDeProducto);
		}

		Document doc = new Document(root);//Creamos el documento

		try {


			XMLOutputter salida = new XMLOutputter(Format.getPrettyFormat());
			FileOutputStream file = new FileOutputStream(directorioActual+"TipoDeProducto.xml");
			salida.output(doc, file);
			file.flush();
			file.close();
			salida.output(doc, System.out);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto");
			e.printStackTrace();
		}
	}
	public void generarXMLTipoDeUsr(){
		ArrayList<TipoDeUsrBD> colTipoDeUsr = candela.getColTipoUsr();
		Element root = new Element("Tipos_De_Usuarios");
		for (int i = 0; i < colTipoDeUsr.size(); i++) {
			Element tipoDeUsr = new Element("TipoDeUsr");
			tipoDeUsr.setAttribute("id",
					Integer.toString(colTipoDeUsr.get(i).getID()));

			root.addContent(tipoDeUsr);
		}

		Document doc = new Document(root);//Creamos el documento

		try {


			XMLOutputter salida = new XMLOutputter(Format.getPrettyFormat());
			FileOutputStream file = new FileOutputStream(directorioActual+"TipoDeUsr.xml");
			salida.output(doc, file);
			file.flush();
			file.close();
			salida.output(doc, System.out);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto");
			e.printStackTrace();
		}
	}
	public void generarXMLUsuarios(){
		ArrayList<usuarioBD> colUsuarios = candela.getColUsuarios();
		Element root = new Element("Usuarios");
		for (int i = 0; i < colUsuarios.size(); i++) {
			Element usuario = new Element("usuario");
			usuario.setAttribute("nombre",
					colUsuarios.get(i).getNombre());
			usuario.setAttribute("apellido",colUsuarios.get(i).getApellido());
			usuario.setAttribute("dni",Integer.toString(colUsuarios.get(i).getDni()));
			usuario.setAttribute("userName",colUsuarios.get(i).getNombreUsr());

			for (int j = 0; j < colUsuarios.get(i).getColFacturas().length; j++) {
				if (!colUsuarios.get(i).getColFacturas()[j].getPagada()){
					Element facturas=new Element("facturasImpagas");
					facturas.setAttribute("factura",Integer.toString(colUsuarios.get(i).getColFacturas()[j].getNumero()));
					usuario.addContent(facturas);


				}

			}

			root.addContent(usuario);
		}

		Document doc = new Document(root);//Creamos el documento

		try {


			XMLOutputter salida = new XMLOutputter(Format.getPrettyFormat());
			FileOutputStream file = new FileOutputStream(directorioActual+"Usuarios.xml");
			salida.output(doc, file);
			file.flush();
			file.close();
			salida.output(doc, System.out);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto");
			e.printStackTrace();
		}
	}
	public void generarXMLTomos(){
		Catalogo catalogo= candela.getCatalogoVigente();
		ArrayList<Tomo> colTomos= catalogo.getTomos();


		Element root = new Element("Tomos");
		for (int i = 0; i < colTomos.size(); i++) {
			Element tomo = new Element("tomo");
			tomo.setAttribute("codigo", Integer.toString(colTomos.get(i).getCodigoTomo()));
			tomo.setAttribute("descripcion",colTomos.get(i).getDescripcion());
			root.addContent(tomo);
		}

		Document doc = new Document(root);//Creamos el documento

		try {


			XMLOutputter salida = new XMLOutputter(Format.getPrettyFormat());
			FileOutputStream file = new FileOutputStream(directorioActual+"tomos.xml");
			salida.output(doc, file);
			file.flush();
			file.close();
			salida.output(doc, System.out);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto");
			e.printStackTrace();
		}
	}
	public void generarXMLFacturasFabrica(){
		ArrayList<FacturaFabrica>facturaFabrica=candela.getFacturasFabrica();



		Element root = new Element("Facturas_A_Fabrica");
		for (int i = 0; i < facturaFabrica.size(); i++) {
			if (!facturaFabrica.get(i).esPagada()){
				Element factura = new Element("Factura");
				factura.setAttribute("numero",Integer.toString(facturaFabrica.get(i).getNumero()));

				root.addContent(factura);
			}
		}

		Document doc = new Document(root);//Creamos el documento

		try {


			XMLOutputter salida = new XMLOutputter(Format.getPrettyFormat());
			FileOutputStream file = new FileOutputStream(directorioActual+"facturasFabrica.xml");
			salida.output(doc, file);
			file.flush();
			file.close();
			salida.output(doc, System.out);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto");
			e.printStackTrace();
		}
	}
	public void generarXMLFacturasPersonal(){
		ArrayList<FacturaPersonal>facturaPersonal=candela.getFacturasPersonal();
		Element root = new Element("Facturas_A_Personal");
		for (int i = 0; i < facturaPersonal.size(); i++) {
			if (!facturaPersonal.get(i).getPagada()){// TODO RODRIGO MODIFICADO
				Element factura = new Element("Factura");
				factura.setAttribute("numero",Integer.toString(facturaPersonal.get(i).getNumero()));


				root.addContent(factura);
			}
		}

		Document doc = new Document(root);//Creamos el documento

		try {


			XMLOutputter salida = new XMLOutputter(Format.getPrettyFormat());
			FileOutputStream file = new FileOutputStream(directorioActual+"facturasPersonal.xml");
			salida.output(doc, file);
			file.flush();
			file.close();
			salida.output(doc, System.out);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto");
			e.printStackTrace();
		}
	}
	public void generarXMLPedidoFabrica(){
		ArrayList<PedidoFabrica>pedidoFabrica=candela.getPedidosFabricaNoFacturados();
		Element root = new Element("Pedidos_a_fabrica");
		for (int i = 0; i < pedidoFabrica.size(); i++) {
			if(pedidoFabrica.get(i).estaRecibido()==false){//Se generan los pedidos fabrica que no estan recibidos.


				Element pedido = new Element("pedido");
				pedido.setAttribute("nroPedido",Integer.toString(pedidoFabrica.get(i).getNumeroPedido()));
				root.addContent(pedido);
			}
		}

		Document doc = new Document(root);//Creamos el documento

		try {


			XMLOutputter salida = new XMLOutputter(Format.getPrettyFormat());
			FileOutputStream file = new FileOutputStream(directorioActual+"pedidoFabrica.xml");
			salida.output(doc, file);
			file.flush();
			file.close();
			salida.output(doc, System.out);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto");
			e.printStackTrace();
		}
	}


	public void generarXMLPedidoPersonal(){
		ArrayList<PedidoPersonal>pedidoPersonal=candela.getPedidosPersonal();
		Element root = new Element("Pedidos_personal");
		for (int i = 0; i < pedidoPersonal.size(); i++) {

			Element pedido = new Element("pedido");
			pedido.setAttribute("nroPedido",Integer.toString(pedidoPersonal.get(i).getNumeroPedido()));

			root.addContent(pedido);

		}

		Document doc = new Document(root);//Creamos el documento

		try {


			XMLOutputter salida = new XMLOutputter(Format.getPrettyFormat());
			FileOutputStream file = new FileOutputStream(directorioActual+"pedidoPersonal.xml");
			salida.output(doc, file);
			file.flush();
			file.close();
			salida.output(doc, System.out);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto");
			e.printStackTrace();
		}
	}
	public void generarXMLProductosEnStock(){
		ArrayList<Producto>productos=candela.getColProductos();
		Element root = new Element("ProductosEnStock");
		for (int i = 0; i < productos.size(); i++) {

			if (productos.get(i).getCantidadEnStock() > 0){
				Element producto = new Element("Producto");
				producto.setAttribute("codigo",
						Integer.toString(productos.get(i).getCodigo()));
				producto.setAttribute("descripcion", productos.get(i)
						.getDescripcion());
				producto.setAttribute("precio",
						Double.toString(productos.get(i).getPrecio()));
				producto.setAttribute("cantidad", Integer.toString(productos
						.get(i).getCantidadEnStock()));

				root.addContent(producto);
			}

		}

		Document doc = new Document(root);//Creamos el documento

		try {


			XMLOutputter salida = new XMLOutputter(Format.getPrettyFormat());
			FileOutputStream file = new FileOutputStream(directorioActual+"productosEnStock.xml");
			salida.output(doc, file);

			file.flush();
			file.close();
			salida.output(doc, System.out);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto en Stock");
			e.printStackTrace();
		}
	}



	public void generarXMLVentas(){

		ArrayList<Lista> tipos= new ArrayList<Lista>();

		//obtengo las facturas a fabrica
		ArrayList<FacturaFabrica> facturas = this.candela.getFacturasFabrica();
		Date hoy= new Date();
		for (int i = 0; i < facturas.size(); i++) {
			//si la fecha de la factura supera a la de 3 meses se incluye en el trimestre
			if (facturas.get(i).getFecha().getMonth()>=(hoy.getMonth()-3)){
				//obtengo los pedidos de las facturas

				//TODO ver el tema de utilizar DATE debe sumarle 1 al mes (0-11)
				int mesFactura=facturas.get(i).getFecha().getMonth()+1;
				ArrayList<PedidoPersonal> pedidos = facturas.get(i).getPedidofab().getPedidos();


				for (int j = 0; j < pedidos.size(); j++) {
					//obtengo detalles
					ArrayList<DetallePedidoPersonal> detalles = pedidos.get(j).getDetalles();
					for (int k = 0; k < detalles.size(); k++) {
						//Por cada detalle busco si existe agrege el tipo
						int codigo=detalles.get(k).getProd().getTipoProducto();

						boolean encontrado= false;
						for (int l = 0; l < tipos.size(); l++) {
							if (tipos.get(l).getCodigo()==codigo){
								//si encuentro el tipo de codigo
								encontrado=true;
								ArrayList<ColMeses> meses = tipos.get(l).getMes();
								boolean mesEncontrado=false;
								for (int m = 0; m < meses.size(); m++) {
									if(meses.get(m).getMes()==mesFactura){
										//si encontrè el mes adecuado incremento la venta
										mesEncontrado=true;
										meses.get(m).setCantVentas(meses.get(m).getCantVentas()+1);
										tipos.get(l).setMes(meses);
									}
								}
								if (!mesEncontrado){
									ColMeses mes= new GeneradorXML.Lista(codigo).new ColMeses(mesFactura,1);
									meses.add(mes);
									tipos.get(l).setMes(meses);
								}


							}
						}
						if (!encontrado){

							//si no encuentro el tipo de codigo
							Lista lista = new Lista(codigo);
							ArrayList<ColMeses> meses= new ArrayList<GeneradorXML.Lista.ColMeses>();
							ColMeses mes = new GeneradorXML.Lista(codigo).new ColMeses(mesFactura,1);
							meses.add(mes);
							lista.setMes(meses);
							tipos.add(lista);

						}

					}
				}
			}
		}

		Element root = new Element("Ventas");
		for (int i = 0; i < tipos.size(); i++) {
			if (tipos.get(i)!= null){
				Element tipoProd= new Element("tipoProducto");
				tipoProd.setAttribute("codigo",Integer.toString(tipos.get(i).getCodigo()));
				for (int j = 0; j < tipos.get(i).getMes().size(); j++) {
					if (tipos.get(i).getMes().get(j)!= null){
						Element mes= new Element("mes");
						mes.setAttribute("mes",Integer.toString(tipos.get(i).getMes().get(j).getMes()));
						mes.setAttribute("ventas",Integer.toString(tipos.get(i).getMes().get(j).getCantVentas()));
						tipoProd.addContent(mes);
					}
				}

				root.addContent(tipoProd);
			}


		}



		Document doc = new Document(root);//Creamos el documento

		try {


			XMLOutputter salida = new XMLOutputter(Format.getPrettyFormat());
			FileOutputStream file = new FileOutputStream(directorioActual+"ventas.xml");
			salida.output(doc, file);

			file.flush();
			file.close();
			salida.output(doc, System.out);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto en Stock");
			e.printStackTrace();
		}
	}

	public void generarFacturasImpagasUsuario(int dni){



		Element root=null;
		//Se busca el Vendedor en memoria por medio de su DNI.
		int i=0;
		ArrayList<Usuario> colUsuarios= candela.getcolUSRSOFTWARE();
		while (i<colUsuarios.size() && (colUsuarios.get(i).getDni()!=dni) ) {
			i++;			
		}
		if(i<colUsuarios.size()){//Si encontre el usuario en la coleccion de Candela
			//Recorrer sus facturas impagas
			ArrayList<FacturaPersonal> factsImpUsr=candela.getcolUSRSOFTWARE().get(i).obtenerFactImpagas();
			if(factsImpUsr!=null){// SI el usuario tiene facturas impagas, se crea el elemento raiz y se a�aden sus hijos

				root=new Element("facturas_impagas");

				//Se recorre la coleccion de facts impagas y se a�aden los hijos

				for (int j = 0; j < factsImpUsr.size(); j++) {
					Element factImp= new Element("facturaImpaga");
					factImp.setAttribute("numeroFact", Integer.toString(factsImpUsr.get(j).getNumero()));
					factImp.setAttribute("tipoFact", Integer.toString(factsImpUsr.get(j).getTipo()));


					//Se castea la fecha de la factura a String
					SimpleDateFormat formato=new SimpleDateFormat("aaaa-MM-dd");
					String fecha= formato.format( factsImpUsr.get(j).getFecha()); 


					factImp.setAttribute("fechaFact", fecha);


					//Se crea el subnodo detalle de Factura
					Element detalle= new Element("detalle");
					ArrayList<DetallePedidoPersonal> detsFacts= factsImpUsr.get(j).getPedidoPers().getDetalles();

					for (int k = 0; k < detsFacts.size(); k++) {
						detalle.setAttribute("codigoProd",Integer.toString(detsFacts.get(k).getProd().getCodigo()));
						detalle.setAttribute("descrip",detsFacts.get(k).getProd().getDescripcion());
						detalle.setAttribute("cantProd",Double.toString(detsFacts.get(k).getCantidad()));
						detalle.setAttribute("precio", Double.toString(detsFacts.get(k).getProd().getPrecio()));


					}
					//Se a�ade detalle al nodo superior factImp
					factImp.addContent(detalle);

					//Se a�ade el elemento a la raiz del XML
					root.addContent(factImp);
				}

			}
			//Se crea el documento con la raiz root
			Document doc=new Document(root);
			try {

				XMLOutputter salida = new XMLOutputter(Format.getPrettyFormat());
				FileOutputStream file = new FileOutputStream(directorioActual+"factsImpagas.xml");
				salida.output(doc, file);

				file.flush();
				file.close();
				salida.output(doc, System.out);

			} catch (Exception e) {
				System.out.println("Error al crear XML de Producto en Stock");
				e.printStackTrace();
			}

		}


	}




	public  void generarTodo(){
		this.generarXMLProductos();
		this.generarXMLFacturasFabrica();
		this.generarXMLFacturasPersonal();
		this.generarXMLPedidoPersonal();
		this.generarXMLTipoDeProductos();
		this.generarXMLTipoDeUsr();
		this.generarXMLTomos();
		this.generarXMLUsuarios();
		this.generarXMLProductosEnStock();
		this.generarXMLVentas();
		this.generarXMLProductosEnStock();
		this.generarXMLPedidoFabrica();//Son pedidos Fabrica no recibidos.
		this.generarProductosNoAsociados();
		System.out.println("Directorio:"+this.directorioActual);
	}




	public void generarFacturasFabricaPagadas(ArrayList<FacturaFabrica>facturas){

		Element root= new Element("FacturasFabPagadas");
		Element detallesPers=null;

		for (int i = 0; i < facturas.size(); i++) {
			Element factura1= new Element("factura");

			factura1.setAttribute("nroFact",Integer.toString(facturas.get(i).getNumero()));

			SimpleDateFormat formato= new SimpleDateFormat("aaaa-MM-dd");
			factura1.setAttribute("fechaFact",formato.format(facturas.get(i).getFecha()));

			String tipoFact=null;
			if(facturas.get(i).getTipo()==1){
				tipoFact="A";
			}else{
				tipoFact="B";
			}
			factura1.setAttribute("tipoFact",tipoFact);

			Element pedidoFab= new Element("pedidoFab");

			pedidoFab.setAttribute("fechaEmisionFactFab",formato.format(facturas.get(i).getPedidofab().getFecha_emision()));
			pedidoFab.setAttribute("fechaRecepcionFactFab",formato.format(facturas.get(i).getPedidofab().getFecha_emision()));
			pedidoFab.setAttribute("nroPedidoFab",Integer.toString(facturas.get(i).getPedidofab().getNumeroPedido()));


			//Se obtienen los pedidos personal en el pedido a fabrica
			ArrayList<PedidoPersonal> pedidosPers= facturas.get(i).getPedidofab().getPedidos();
			Element pedidoPers=null;
			for (int j = 0; j < pedidosPers.size(); j++) {
				pedidoPers= new Element("PedidoPers");

				pedidoPers.setAttribute("nroPedidoPers",Integer.toString(pedidosPers.get(j).getNumeroPedido()));
				pedidoPers.setAttribute("fechaEmisionPedidoPers",formato.format((pedidosPers.get(j).getFecha_emision())));
				pedidoPers.setAttribute("fechaRecepcionPedidosPers", formato.format(Integer.toString(pedidosPers.get(j).getNumeroPedido())));


				ArrayList<DetallePedidoPersonal> dets=pedidosPers.get(i).getDetalles();

				for (int k = 0; k < dets.size(); k++) {

					detallesPers= new Element("detallePedidoPersonal");

					detallesPers.setAttribute("cantidadProductoPedPersonal",Float.toString((dets.get(k).getCantidad())));
					detallesPers.setAttribute("descripcionProducto",dets.get(k).getProd().getDescripcion());
					detallesPers.setAttribute("codigoProducto",Integer.toString(dets.get(k).getProd().getCodigo()));
					detallesPers.setAttribute("precioProducto",Double.toString(dets.get(k).getProd().getPrecio()));


				}				
			}
			//Se forma el arbol de nodos XML.
			pedidoPers.addContent(detallesPers);
			pedidoFab.addContent(pedidoPers);
			factura1.addContent(pedidoFab);
			root.addContent(factura1);

		}

		Document doc=new Document(root);
		try {

			XMLOutputter salida = new XMLOutputter(Format.getPrettyFormat());
			FileOutputStream file = new FileOutputStream(directorioActual+"FactFabricaPagada.xml");
			salida.output(doc, file);

			file.flush();
			file.close();
			salida.output(doc, System.out);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto en Stock");
			e.printStackTrace();
		}



	}

	/**
	 * Este metodo genera un archivo XML con los datos pertenecientes a los productos de un
	 * determinado tomo, para colocar en un DataGrid.
	 * @param nroTomo: numero del tomo del qeu se generaran los datos XML de los productos.
	 */
	public  void generarDatosProdAsocTomo(int nroTomo){


		ArrayList<Tomo>tomos=candela.getCatalogoVigente().getTomos();
		Element root = new Element("ProductosDeTomo");
		for (int i = 0; i < tomos.size(); i++) {

			if (tomos.get(i).getCodigoTomo()==nroTomo){ //Se busca el numero de tomo
				ArrayList<Producto> prodsTomo=tomos.get(i).getProductos();

				for(int j=0;j<prodsTomo.size();j++){ //Se recorren los productos
					Element producto = new Element("Producto");
					producto.setAttribute("codigo",
							Integer.toString(prodsTomo.get(j).getCodigo()));
					producto.setAttribute("descripcion", prodsTomo.get(j).getDescripcion());
					producto.setAttribute("precio", Double.toString(prodsTomo.get(j).getPrecio()));
					producto.setAttribute("cantidad", Integer.toString(prodsTomo.get(j).getCantidadEnStock()));

					producto.setAttribute("tipoDeProducto", Integer.toString(prodsTomo.get(j).getTipoProducto()));


					root.addContent(producto);
				}
			}

		}

		Document doc = new Document(root);//Creamos el documento

		try {


			XMLOutputter salida = new XMLOutputter(Format.getPrettyFormat());
			FileOutputStream file = new FileOutputStream(directorioActual+"datosProductoAsocTomo.xml");
			salida.output(doc, file);

			file.flush();
			file.close();
			salida.output(doc, System.out);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto en Stock");
			e.printStackTrace();
		}



	}
	/**
	 * Este metodo genera los codigos de los tomos vigentes en un catalogo.
	 * 
	 */
	public void generarTomosVigentes(){

		Catalogo catalogo= candela.getCatalogoVigente();
		ArrayList<Tomo> colTomos= catalogo.getTomos();


		Element root = new Element("Tomos");
		for (int i = 0; i < colTomos.size(); i++) {
			Element tomo = new Element("tomo");
			tomo.setAttribute("codigo", Integer.toString(colTomos.get(i).getCodigoTomo()));
			root.addContent(tomo);
		}

		Document doc = new Document(root);//Creamos el documento

		try {


			XMLOutputter salida = new XMLOutputter(Format.getPrettyFormat());
			FileOutputStream file = new FileOutputStream(directorioActual+"tomosVigentes.xml");
			salida.output(doc, file);
			file.flush();
			file.close();
			salida.output(doc, System.out);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto");
			e.printStackTrace();
		}

	}

	/**
	 * Este metodo genera los codigos de los productos vigentes de un tomo seleccionado
	 * en un  comboBox.
	 * @param numeroTomo
	 */

	public void generarProductosVigentes(int nroTomo){




		ArrayList<Tomo>tomos=candela.getCatalogoVigente().getTomos();
		Element root = new Element("ProductosDeTomo");
		for (int i = 0; i < tomos.size(); i++) {

			if (tomos.get(i).getCodigoTomo()==nroTomo){ //Se busca el numero de tomo
				ArrayList<Producto> prodsTomo=tomos.get(i).getProductos();

				for(int j=0;j<prodsTomo.size();j++){ //Se recorren los productos
					Element producto = new Element("Producto");
					producto.setAttribute("codigo",
							Integer.toString(prodsTomo.get(j).getCodigo()));

					root.addContent(producto);
				}
			}

		}

		Document doc = new Document(root);//Creamos el documento

		try {


			XMLOutputter salida = new XMLOutputter(Format.getPrettyFormat());
			FileOutputStream file = new FileOutputStream(directorioActual+"productosVigentes.xml");
			salida.output(doc, file);

			file.flush();
			file.close();
			salida.output(doc, System.out);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto en Stock");
			e.printStackTrace();
		}


	}
	//TODO DAMIAN agregue metodo 5-9-12
	/***
	 * Generar Productos no asociados
	 */
	public void generarProductosNoAsociados(){

		Element root = new Element("ProductosNoAsociados");
		ArrayList<Producto> productosMemoria = candela.getColProductos();
		//Recorro toda la memoria con todos los productos
		for (int i = 0; i < productosMemoria.size(); i++) {
			if (!buscarProductoAsociado(productosMemoria.get(i).getCodigo())){
				Element producto = new Element("Producto");
				producto.setAttribute("codigo", Integer.toString(productosMemoria.get(i).getCodigo()));
				root.addContent(producto);
			}
		}
		Document doc = new Document(root);//Creamos el documento

		try {


			XMLOutputter salida = new XMLOutputter(Format.getPrettyFormat());
			FileOutputStream file = new FileOutputStream(directorioActual+"productosNoAsociados.xml");
			salida.output(doc, file);

			file.flush();
			file.close();
			salida.output(doc, System.out);

		} catch (Exception e) {
			System.out.println("Error al crear XML de productos no asociados");
			e.printStackTrace();
		}


	}

	/***
	 * Método Buscar Producto Asociado:
	 * Devuelve si encuentra o no el producto pasado (codigo) por parámetro.
	 * @param codigo
	 * @return
	 */
	private boolean buscarProductoAsociado(int codigo){
		
		ArrayList<Tomo>tomos=candela.getCatalogoVigente().getTomos();
		//TODO [DAMIAN] preguntar, porque tomos viene null, esta implementado el getcatalogovigente
		boolean encontrado=false;
		int i = 0;
		//busco dentro de los tomos
		while (!encontrado && i < tomos.size() ){
			ArrayList<Producto> productosTomo = tomos.get(i).getProductos();
			int j = 0;
			i++;
			//busco dentro de los productos de los tomos
			while (!encontrado && j < productosTomo.size()){
				//si lo encuentro AVISO
				if (productosTomo.get(j).getCodigo()== codigo){
					encontrado= true;
				}
				j++;
			}
		}
		return encontrado;
		


	}
	

	public class Lista{
		int codigo;
		ArrayList<ColMeses> mes;
		public Lista(int codigo){
			this.codigo= codigo;
			this.mes=  new ArrayList<Lista.ColMeses>();
		}
		public int getCodigo() {
			return codigo;
		}

		public void setCodigo(int codigo) {
			this.codigo = codigo;
		}

		public ArrayList<ColMeses> getMes() {
			return mes;
		}

		public void setMes(ArrayList<ColMeses> mes) {
			this.mes = mes;
		}

		public class ColMeses{
			int mes;
			int cantVentas;
			public ColMeses(int mes, int cantVentas){
				this.mes=mes;
				this.cantVentas=cantVentas;
			}
			public ColMeses() {
				// TODO Auto-generated constructor stub
			}
			public int getMes() {
				return mes;
			}
			public void setMes(int mes) {
				this.mes = mes;
			}
			public int getCantVentas() {
				return cantVentas;
			}
			public void setCantVentas(int cantVentas) {
				this.cantVentas = cantVentas;
			}

		}
	}


	public static void main (String args[]) throws SQLException{
		Candela candela= new Candela();
		candela.iniciar();
		GeneradorXML generador= new GeneradorXML(candela);
		generador.generarTodo();
	
		
		

	}
	
}

