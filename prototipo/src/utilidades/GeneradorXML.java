package utilidades;


import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
import negocio.Tomo;
import negocio.Usuario;

public class GeneradorXML {
	private Candela candela;
	private String directorioActual;


	public GeneradorXML(Candela candela){
		this.candela=candela;
		this.directorioActual= candela.getDirectorio();
	}



	/**
	 * Este metodo genera un xml llamado: dniVendDirect.xml con los Dnis
	 * de los vendedores y directores, que son usados en una busqueda
	 * sensitiva en el caso de uso PagoDeFactImpaga.
	 * Luego desde AS2 se realiza el filtrado segun lo que ingresa el usuario
	 * en el textfield. 
	 */
	synchronized public void generarDnisVendDir(int tiempo){
		ArrayList<usuarioBD> usuarios= candela.getColUsuarios();

		Element root= new Element("DNIS-vend-directores");

		for (int i = 0; i < usuarios.size(); i++) {
			if(usuarios.get(i).getTipoDeUsrBD().getnroTipoUsr()==Constantes.VENDEDOR
					|| usuarios.get(i).getTipoDeUsrBD().getnroTipoUsr()==Constantes.DIRECTOR){
				//Si es un usuario vendedor o director, se agrega al XML
				Element dni= new Element("dni");
				dni.setAttribute("nroDni",Integer.toString(usuarios.get(i).getDni()));
				dni.setAttribute("nombre",(usuarios.get(i).getApellido()+" "+usuarios.get(i).getNombre()) );
				root.addContent(dni);
				
			}
		}//FIn del for de usuarios

		Document doc = new Document(root);//Creamos el documento

		try {

			System.out.println("El directorio actual es: "+directorioActual);
			XMLOutputter salida = new XMLOutputter(Format.getPrettyFormat());
			FileOutputStream file = new FileOutputStream(directorioActual+"dniVendDirect.xml");
			salida.output(doc, file);
			file.flush();
			file.close();
			salida.output(doc, System.out);
			Thread.sleep(tiempo);


		} catch (Exception e) {
			System.out.println("Error al crear XML de Dnis de Vendedores/Directores- Sentitive Search");

			e.printStackTrace();
		}
	}





	//TODO el filtrado de los xmls se debe realizar desde AS2
	synchronized public  void generarXMLProductos(int tiempo){
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
			Thread.sleep(tiempo);


		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto");

			e.printStackTrace();
		}
	}
	synchronized public void generarXMLTipoDeProductos(int tiempo){
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
			Thread.sleep(tiempo);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto");
			e.printStackTrace();
		}
	}
	synchronized public void generarXMLTipoDeUsr(int tiempo){
		@SuppressWarnings("static-access")
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
			Thread.sleep(tiempo);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto");
			e.printStackTrace();
		}
	}
	synchronized public void generarXMLUsuarios(int tiempo){
		ArrayList<Usuario> colUsuarios = candela.getcolUSRSOFTWARE();
		Element root = new Element("Usuarios");
		for (int i = 0; i < colUsuarios.size(); i++) {
			Element usuario = new Element("usuario");
			usuario.setAttribute("nombre",
					colUsuarios.get(i).getNombre());
			usuario.setAttribute("apellido",colUsuarios.get(i).getApellido());
			usuario.setAttribute("dni",Integer.toString(colUsuarios.get(i).getDni()));
			usuario.setAttribute("userName",colUsuarios.get(i).getNombreUsuario());
			usuario.setAttribute("tipoDeUsuario",Integer.toString(colUsuarios.get(i).getTipoDeUsuario()));			
			for (int j = 0; j < colUsuarios.get(i).getFacturaPers().size(); j++) {
				if (!colUsuarios.get(i).getFacturaPers().get(j).getPagada()){
					//NOTA el atributo factura = pedido, no se cambia por motivos de reestructuraciòn del código
					Element facturas=new Element("facturasImpagas");
					facturas.setAttribute("factura",Integer.toString(colUsuarios.get(i).getFacturaPers().get(j).getPedidoPers().getNumeroPedido()));
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
			Thread.sleep(tiempo);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto");
			e.printStackTrace();
		}
	}
	synchronized public void generarXMLTomos(int tiempo){
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
			Thread.sleep(tiempo);


		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto");
			e.printStackTrace();
		}
	}
	synchronized public void generarXMLFacturasFabrica(int tiempo){
		ArrayList<FacturaFabrica>facturaFabrica=candela.getFacturasFabrica();



		Element root = new Element("Facturas_A_Fabrica");
		for (int i = 0; i < facturaFabrica.size(); i++) {
			if (!facturaFabrica.get(i).esPagada()){
				Element factura = new Element("Factura");
				//factura.setAttribute("numero",Integer.toString(facturaFabrica.get(i).getNumero()));
				factura.setAttribute("numero",Integer.toString(facturaFabrica.get(i).getCodigo()));
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
			Thread.sleep(tiempo);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto");
			e.printStackTrace();
		}
	}
	synchronized public void generarXMLFacturasPersonal(int tiempo){
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
			Thread.sleep(tiempo);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto");
			e.printStackTrace();
		}
	}
	synchronized	public void generarXMLPedidoFabrica(int tiempo){
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
			Thread.sleep(tiempo);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto");
			e.printStackTrace();
		}
	}


	synchronized public void generarXMLPedidoPersonal(int tiempo){
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
			Thread.sleep(tiempo);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto");
			e.printStackTrace();
		}
	}
	synchronized public void generarXMLProductosEnStock(int tiempo){
		ArrayList<Producto>productos=candela.getColProductos();
		Element root = new Element("ProductosEnStock");
		for (int i = 0; i < productos.size(); i++) {

			if (productos.get(i).getCantidadEnStock() > 0 && productos.get(i).getTipoProducto()>0){
				Element producto = new Element("Producto");
				producto.setAttribute("codigo",
						Integer.toString(productos.get(i).getCodigo()));
				producto.setAttribute("descripcion", productos.get(i)
						.getDescripcion());
				producto.setAttribute("precio",
						Double.toString(productos.get(i).getPrecio()));
				producto.setAttribute("cantidad", Integer.toString(productos
						.get(i).getCantidadEnStock()));
				producto.setAttribute("tipo",Integer.toString(productos.get(i).getTipoProducto()));

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
			Thread.sleep(tiempo);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto en Stock");
			e.printStackTrace();
		}
	}



	@SuppressWarnings("deprecation")
	synchronized public void generarXMLVentas(int tiempo){

		ArrayList<Lista> tipos= new ArrayList<Lista>();

		//obtengo las facturas a fabrica
		ArrayList<FacturaFabrica> facturas = this.candela.getFacturasFabrica();
		Date hoy= new Date();
		for (int i = 0; i < facturas.size(); i++) {
			//si la fecha de la factura supera a la de 3 meses se incluye en el trimestre
			if ((facturas.get(i).getFecha().getMonth()>=(hoy.getMonth()-3)) && (facturas.get(i).esPagada())){
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
									ColMeses mes= new GeneradorXML.Lista(codigo,tipos.get(l).getDescripcion()).new ColMeses(mesFactura,1);
									meses.add(mes);
									tipos.get(l).setMes(meses);
								}


							}
						}
						if (!encontrado){

							//si no encuentro el tipo de codigo
							//tengo que crear la tupla lista
							for (int l = 0; l < candela.getColTipoDeProducto().size(); l++) {
								if (candela.getColTipoDeProducto().get(l).getCodTipoProd()== codigo){
									Lista lista = new Lista(codigo,candela.getColTipoDeProducto().get(l).getDescripcion());
									ArrayList<ColMeses> meses= new ArrayList<GeneradorXML.Lista.ColMeses>();
									ColMeses mes = new GeneradorXML.Lista(codigo,candela.getColTipoDeProducto().get(l).getDescripcion()).new ColMeses(mesFactura,1);
									meses.add(mes);
									lista.setMes(meses);
									tipos.add(lista);
								}
							}


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
				tipoProd.setAttribute("descripcion",tipos.get(i).getDescripcion());
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
			FileOutputStream file = new FileOutputStream(candela.getDirectorio()+"flex/ventas.xml");
			salida.output(doc, file);
			System.out.println(candela.getDirectorio()+"flex/ventas.xml");
			file.flush();
			file.close();
			salida.output(doc, System.out);
			Thread.sleep(tiempo);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto en Stock");
			e.printStackTrace();
		}
	}

	synchronized public void generarFacturasImpagasUsuario(int dni, int tiempo){

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
					factImp.setAttribute("total", Float.toString(factsImpUsr.get(j).total()));

					GregorianCalendar gc= (GregorianCalendar) Calendar.getInstance();
					gc.setGregorianChange(factsImpUsr.get(j).getFecha());

					String fecha= gc.get(Calendar.YEAR)+"-"+gc.get(Calendar.MONTH)+"-"+gc.get(Calendar.DAY_OF_MONTH);
					factImp.setAttribute("fechaFact", fecha);

					//Se crea el subnodo detalle de Factura
					//Element detalle= new Element("detalle");
					ArrayList<DetallePedidoPersonal> detsFacts= factsImpUsr.get(j).getPedidoPers().getDetalles();
					for (int k = 0; k < detsFacts.size(); k++) {
						Element detalle= new Element("detalle");
						detalle.setAttribute("codigoProd",Integer.toString(detsFacts.get(k).getProd().getCodigo()));
						detalle.setAttribute("descrip",detsFacts.get(k).getProd().getDescripcion());
						detalle.setAttribute("cantProd",Integer.toString(detsFacts.get(k).getCantidad()));
						detalle.setAttribute("precio", Double.toString(detsFacts.get(k).getProd().getPrecio()));

						//Se a�ade detalle al nodo superior factImp
						factImp.addContent(detalle);	
					}
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
				Thread.sleep(tiempo);

			} catch (Exception e) {
				System.out.println("Error al crear XML de Producto en Stock");
				e.printStackTrace();
			}

		}


	}




	synchronized	public  void generarTodo(){
		long time_start = System.currentTimeMillis();

		this.generarXMLProductos(0);
		this.generarXMLFacturasFabrica(0);
		this.generarXMLFacturasPersonal(0);
		this.generarXMLPedidoPersonal(0);
		this.generarXMLTipoDeProductos(0);
		this.generarXMLTipoDeUsr(0);
		this.generarXMLTomos(0);
		this.generarXMLUsuarios(0);
		this.generarXMLProductosEnStock(0);
		this.generarXMLVentas(0);
		this.generarXMLPedidoFabrica(0);//Son pedidos Fabrica no recibidos.
		this.generarProductosNoAsociados(0);
		this.generarTomosVigentes(0);
		//nuevos xml 
		this.generarDnisVendDir(0);
		this.generarXMLPedidoFabrica(0);
		long time_end = System.currentTimeMillis();
		System.out.println("TIEMPO TOTAL:"+(time_end - time_start));

		System.out.println("Directorio:"+this.directorioActual);

	}




	synchronized public void generarFacturasFabricaPagadas(ArrayList<FacturaFabrica>facturas, int tiempo){

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
			Thread.sleep(tiempo);

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
	synchronized public  void generarDatosProdAsocTomo(int nroTomo, int tiempo){


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


					//Si el tipo de producto es null, el atributo tipoDeProducto se setea en N/A,
					//sino se recorre la coleccion de tipos de producto de candela buscando ese tipo de producto.
					if(prodsTomo.get(j).getTipoProducto()==-1){
						producto.setAttribute("tipoDeProducto", "N/A");

					}else{
						ArrayList<TipoDeProductoBD> tiposDeProducto = candela.getColTipoDeProducto();
						for (int k = 0; k < tiposDeProducto.size(); k++) {
							if(tiposDeProducto.get(k).getCodTipoProd()==prodsTomo.get(j).getTipoProducto()){
								String descripcion=tiposDeProducto.get(k).getDescripcion();
								producto.setAttribute("tipoDeProducto", descripcion);

							}
						}
					}


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
			Thread.sleep(tiempo);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto en Stock");
			e.printStackTrace();
		}



	}
	/**
	 * Este metodo genera los codigos de los tomos vigentes en un catalogo.
	 * 
	 */
	synchronized public void generarTomosVigentes(int tiempo){

		Catalogo catalogo= candela.getCatalogoVigente();
		ArrayList<Tomo> colTomos= catalogo.getTomos();


		Element root = new Element("Tomos");
		for (int i = 0; i < colTomos.size(); i++) {
			Element tomo = new Element("tomo");
			tomo.setAttribute("codigo", Integer.toString(colTomos.get(i).getCodigoTomo()));
			tomo.setAttribute("descripcion",colTomos.get(i).getDescripcion());
			ArrayList<Producto> productos = colTomos.get(i).getProductos();
			for (int j = 0; j < productos.size(); j++) {
				Element producto = new Element("producto");
				producto.setAttribute("codigoProducto",Integer.toString((productos.get(j).getCodigo())));
				producto.setAttribute("descripcion",productos.get(j).getDescripcion());
				producto.setAttribute("precio",Double.toString(productos.get(j).getPrecio()));
				tomo.addContent(producto);
			}
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
			Thread.sleep(tiempo);

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

	synchronized public void generarProductosVigentes(int nroTomo, int tiempo){




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
			Thread.sleep(tiempo);

		} catch (Exception e) {
			System.out.println("Error al crear XML de Producto en Stock");
			e.printStackTrace();
		}


	}
	//TODO DAMIAN agregue metodo 5-9-12
	/***
	 * Generar Productos no asociados
	 */
	synchronized public void generarProductosNoAsociados(int tiempo){

		Element root = new Element("ProductosNoAsociados");
		ArrayList<Producto> productosMemoria = candela.getColProductos();
		//Recorro toda la memoria con todos los productos
		for (int i = 0; i < productosMemoria.size(); i++) {
			System.out.println("Codigo de producto:"+productosMemoria.get(i).getCodigo());
			if (!buscarProductoAsociado(productosMemoria.get(i).getCodigo())){
				Element producto = new Element("Producto");
				producto.setAttribute("codigo", Integer.toString(productosMemoria.get(i).getCodigo()));
				producto.setAttribute("descripcion",productosMemoria.get(i).getDescripcion());
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
			Thread.sleep(tiempo);

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
	public boolean buscarProductoAsociado(int codigo){

		ArrayList<Tomo>tomos=null;
		tomos=candela.getCatalogoVigente().getTomos();


		//TODO [DAMIAN] preguntar, porque tomos viene null, esta implementado el getcatalogovigente
		boolean encontrado=false;
		int i = 0;
		//busco dentro de los tomos
		while (!encontrado && (i < tomos.size() )){
			ArrayList<Producto> productosTomo = tomos.get(i).getProductos();
			int j = 0;
			i++;
			//busco dentro de los productos de los tomos
			while (!encontrado && (j < productosTomo.size())){
				//si lo encuentro AVISO
				if (productosTomo.get(j).getCodigo()== codigo){
					encontrado= true;
				}
				j++;
			}
		}
		if (!encontrado && candela.getCatalogoNuevo()!=null && candela.esCatalogoNuevo()){


			tomos=candela.getCatalogoNuevo().getTomos();


			//TODO [DAMIAN] preguntar, porque tomos viene null, esta implementado el getcatalogovigente

			i=0;
			//busco dentro de los tomos
			while (!encontrado && (i < tomos.size() )){
				ArrayList<Producto> productosTomo = tomos.get(i).getProductos();
				int j = 0;
				i++;
				//busco dentro de los productos de los tomos
				while (!encontrado && (j < productosTomo.size())){
					//si lo encuentro AVISO
					if (productosTomo.get(j).getCodigo()== codigo){
						encontrado= true;
					}
					j++;
				}
			}

		}
		return encontrado;



	}


	public class Lista{
		int codigo;
		String descripcion;
		ArrayList<ColMeses> mes;
		public Lista(int codigo, String descripcion){
			this.codigo= codigo;
			this.mes=  new ArrayList<Lista.ColMeses>();
			this.descripcion=descripcion;
		}
		public int getCodigo() {
			return codigo;
		}

		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
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

	/***
	 * Este método genera un xml con un snapshot de la tabla catalogovigente-->tomo-->producto que
	 * se emplean para las estadisticas y es llamado cuando se crea un nuevo catalogo
	 */
	synchronized public void generarHistorico(int tiempo){


		Catalogo catalogo = candela.getCatalogoVigente();
		Element root = new Element("Catalogo");
		root.setAttribute("anio",Integer.toString(catalogo.getAnioVigencia()));
		ArrayList<TipoDeProductoBD> tiposProducto = candela.getColTipoDeProducto();
		ArrayList<Tomo> tomos = catalogo.getTomos();
		//Recorro toda la memoria con todos los productos
		for (int i = 0; i < tomos.size(); i++) {
			Element tomo= new Element ("Tomo");
			tomo.setAttribute("codigoTomo",Integer.toString(tomos.get(i).getCodigoTomo()));
			tomo.setAttribute("descripcion",tomos.get(i).getDescripcion());

			ArrayList<Producto> prod = tomos.get(i).getProductos();

			for (int j = 0; j < prod.size(); j++) {
				Element producto= new Element("Producto");
				producto.setAttribute("codigo",
						Integer.toString(prod.get(j).getCodigo()));
				producto.setAttribute("descripcion", prod.get(j).getDescripcion());
				producto.setAttribute("precio", Double.toString(prod.get(j).getPrecio()));
				producto.setAttribute("cantidad", Integer.toString(prod.get(j).getCantidadEnStock()));
				producto.setAttribute("tipoDeProducto", Integer.toString(prod.get(j).getTipoProducto()));
				for (int k = 0; k < tiposProducto.size(); k++) {
					if (tiposProducto.get(k).getCodTipoProd() == prod.get(j).getTipoProducto()){
						producto.setAttribute("descripcionTipoProd",tiposProducto.get(k).getDescripcion());

					}

				}	

				tomo.addContent(producto);

			}
			root.addContent(tomo);
		}
		Document doc = new Document(root);//Creamos el documento

		try {


			XMLOutputter salida = new XMLOutputter(Format.getPrettyFormat());
			Calendar cal =  GregorianCalendar.getInstance();

			FileOutputStream file = new FileOutputStream(directorioActual+"historico/historico-"+cal.getTime()+".xml");
			salida.output(doc, file);
			

			file.flush();
			file.close();
			
			salida.output(doc, System.out);
			Thread.sleep(tiempo);

		} catch (Exception e) {
			System.out.println("Error al crear XML historico");
			e.printStackTrace();
		}


	}


	public static void main (String args[]) throws SQLException{
		Candela candela= new Candela();
		candela.iniciar();
		GeneradorXML generador= new GeneradorXML(candela);
		generador.generarTodo();




	}

	

}

