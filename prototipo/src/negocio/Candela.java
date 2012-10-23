package negocio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import excepciones.FacturaPagadaExcepcion;
import excepciones.FacturaVencidaExcepcion;
import excepciones.ProductoExisteExcepcion;
import excepciones.ProductoNoEncontradoException;
import excepciones.ProductoNoExisteExcepcion;
import excepciones.TipoProductoExcepcion;
import excepciones.TomoAsignadoExcepcion;
import excepciones.TomoNoEncontradoException;

import persistencia.CatalogoBD;
import persistencia.DetallePedidoPersonalBD;
import persistencia.FacturaFabricaBD;
import persistencia.FacturaPersonalBD;
import persistencia.PedidoFabricaBD;
import persistencia.PedidoPersonalBD;
import persistencia.ProductoBD;
import persistencia.TipoDeProductoBD;
import persistencia.TipoDeUsrBD;
import persistencia.TomoBD;
import persistencia.usuarioBD;

import utilidades.Constantes;
import utilidades.Errores;
import utilidades.GeneradorXML;

import net.java.ao.EntityManager;
/***
 * Es la clase que Controla toda la lógica de negocio funcionando como nexo entre todas las clases y la base de datos, 
 * y quienes la llaman (JSP)
 * @author "Huincalef Rodrigo-Mansilla Damián"
 */
public class Candela {


	public static ArrayList <TipoDeUsrBD> colTipoUsr=null;
	private ArrayList <usuarioBD> colUsuarios=null;
	private ArrayList <TipoDeProductoBD> colTipoproducto=null;
	private ArrayList<Producto> colProductos=null;
	private Connection conexion=null;
	private Catalogo catalogoVigente=null;
	private Catalogo catalogoNuevo=null; //This is the Catalogo that 'll be created for the USR in formAltaCatalogo.jsp
	private ArrayList<PedidoPersonal> pedidosPersonal;
	private ArrayList<PedidoFabrica> pedidosFabricaNoFacturados;
	private ArrayList<FacturaFabrica> facturasFabrica;
	private ArrayList<FacturaPersonal> facturasPersonal;
	//TODO [DAMIAN] 11-9-12 agrege nueva variable
	private String directorio;
	//TODO [DAMIAN] 11-9-12 fin

	//TODO: Cambiar esto por una coleccioon de usuarios reales que no sean BD!!
	private ArrayList<Usuario> colUSRSOFTWARE;

	
	
	
	

	/**
	 * getColProductos
	 * Este metodo retorna la lista de productos que posee Candela.
	 * @return: una coleccion de todos los productos de Candela.
	 */
	public ArrayList<Producto> getColProductos() {
		return colProductos;
	}
	/**
	 * getColPedidosPersonal
	 * Este metodo retorna una coleccion de todos los pedidos realizados por el personal.
	 * @return: una coleccion de todos los pedidos personal de Candela.
	 */
	public ArrayList<PedidoPersonal> getPedidosPersonal() {
		return pedidosPersonal;
	}
	/**
	 * getPedidosFabricaNoFacturados
	 * Este metodo retorna una coleccion de todos los pedidos no facturados de Candela.
	 * @return: una coleccion de todos los pedidos no factuados de Candela.
	 */
	public ArrayList<PedidoFabrica> getPedidosFabricaNoFacturados() {
		return pedidosFabricaNoFacturados;
	}
	/**
	 * getFacturasFabrica
	 * Este metodo retorna una coleccion de las facturas Fabrica de Candela.
	 * @return: una coleccion de todas las facturas fabricas de Candela.
	 */
	public ArrayList<FacturaFabrica> getFacturasFabrica() {
		return facturasFabrica;
	}
	/**
	 * getFacturasPersonal
	 * Este metodo retorna una coleccion de todas las facturas de Personal
	 * @return: una coleccion de todas las Facturas de Personal.
	 */
	public ArrayList<FacturaPersonal> getFacturasPersonal() {
		return facturasPersonal;
	}

	/***
	 * Constructor de Candela
	 * Inicia todos los valores de las colecciones y el manejador de entidades
	 * @throws SQLException 
	 */
	public Candela() throws SQLException{
		colTipoUsr= new ArrayList<TipoDeUsrBD>();
		colUsuarios= new ArrayList<usuarioBD>();
		colTipoproducto= new ArrayList<TipoDeProductoBD>();
		colProductos= new ArrayList<Producto>();
		pedidosPersonal= new ArrayList<PedidoPersonal>();
		pedidosFabricaNoFacturados= new ArrayList<PedidoFabrica>();
		facturasFabrica= new ArrayList<FacturaFabrica>();
		facturasPersonal= new ArrayList<FacturaPersonal>();
		colUSRSOFTWARE=new ArrayList<Usuario>();//TODO: CAMBIAR

		//Se crea la conexion entre BD y la clase
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		conexion = DriverManager.getConnection(Constantes.URL, Constantes.USUARIO, Constantes.PASS);

	}
	
	
	public Connection getConexion() {
		return conexion;
	}
	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}
	/**
	 * Obtiene la coleccion de usuarios de Candela.
	 * @return
	 */
	//TODO: ELIMINAR ESTO!! LUEGO DEL INTERCAMBIO DE COLECCIONES.
	public ArrayList<Usuario> getcolUSRSOFTWARE(){
		return this.colUSRSOFTWARE;	
	}
	/**
	 * Este metodo verifica si un usuario esta cargado en el sistema y retorna un valor que especifica
	 * la posicion en la coleccion de usuarios del sistema.
	 * @return: Este metodo retorna la posicion del usuario en la coleccion de usuarios del sistema, o -1
	 * si no encuentra el usaurio en el sistema.
	 */
	public int verificarUsuario(int dni){
		int resultado=Constantes.ERROR;

		//Buscar si el usuario existe en la coleccion de usuarios de Candela
		//ArrayList<usuarioBD> usuarios=candela.getColUsuarios();
		int posUsuario;
		for(posUsuario=0; posUsuario<colUsuarios.size(); posUsuario++){
			if(dni== colUsuarios.get(posUsuario).getDni()){
				resultado=posUsuario;
				break;
			}
		}

		return resultado;
	}


	/***
	 * iniciar()
	 * Es el método que inicia Candela inicializando las colecciones:
	 * Tipo de Producto
	 * Producto
	 * Usuarios
	 * TipoDeUsuarios
	 * @throws SQLException 
	 */
	public void  iniciar() throws SQLException{

		TipoDeUsrBD tiposDeUsuarios []= null;

		tiposDeUsuarios=  conexion.find(TipoDeUsrBD.class);



		for (int i = 0; i < tiposDeUsuarios.length; i++) {
			colTipoUsr.add(tiposDeUsuarios[i]);
		}



		ProductoBD [] prods=null;

		prods= conexion.find(ProductoBD.class);

		if (prods.length > 0){
			for (int i = 0; i < prods.length; i++) {
				Producto prod1= new Producto(this.conexion);
				prod1.setCodigo(prods[i].getCodigo());
				prod1.setCantidadEnStock(prods[i].getCantidadEnStock());
				prod1.setDescripcion(prods[i].getDescripcion());
				prod1.setPrecio(prods[i].getPrecio());

				//Se lee el codigo del tipo de producto y se busca ese codigo en la coleccion de tipos
				// de producto de Candela (colTipoproducto) y se lo setea en memoria.
				int tipoProd1;
				try {
					tipoProd1= prods[i].getTipoDeProductoBD().getCodTipoProd();
				}catch(NullPointerException nul){
					tipoProd1 = -1;;//TODO DAMIAN 11-9-2012 significa que no se encuentra asociado a nada
				}
				prod1.setTipoProducto(tipoProd1);
				colProductos.add(prod1);
			}
		}

		TipoDeProductoBD [] tiposprod=null;


		tiposprod = conexion.find(TipoDeProductoBD.class);
		if (tiposprod.length > 0){
			for (int i = 0; i < tiposprod.length; i++) {
				colTipoproducto.add(tiposprod[i]);
			}
		}


		//cargar catalogo vigente
		this.cargarCatalogoVigente();

		//Cargar PedidosPersonal
		this.cargarPedidosPersonal();

		//Cargar FacturasPersonal
		this.cargarFacturasPersonal();
		//Cargar PedidoFabrica
		this.cargarPedidoFabrica();
		//Cargar FacturaFabrica
		this.cargarFacturasFabrica();

		//cargar usuarios
		this.cargarUsuarios();

		//Se generan los XML, por medio del generador de XML
		GeneradorXML xml= new GeneradorXML(this);
		xml.generarTodo();

	}


	public int cargarUsuarios() throws SQLException{

		//TODO: Agregar al CARGADORBD.java, un usuario que tenga facturasPersonal Impagas.
		//TODO: Modularizar esta  parte donde se realiza la carga de : 
		//productosBD, TiposProductoBD, usuariosBD y tiposDeUsuariosBD.

		//TODO: Cambiar las colecciones de Entidades que posee candela (usuariosBD,productosBD) por
		//objetos sofware. Esto va a influir sobre CU: pagoFactImpaga, que es donde se leen las facturas de
		//los usuarios.
		int resultado=0;

		//Se cargan los tipos de usuario Primero
		this.cargarTiposUsr(); //TODO MODIFICADO RODRIGO.

		usuarioBD usuarios[]= null;

		try{
			usuarios=  conexion.find(usuarioBD.class);
		}catch(SQLException sql){

			System.out.println("Ocurrio un error al leer los  usuarios \n");
			sql.printStackTrace();
			return Errores.CARGARUSUARIOS;

		}
		if(usuarios.length>0){

			ArrayList<FacturaPersonal> factsPers=null;// Coleccion de facturas que alamacena las facturas de un usuario en especial.

			//TODO: CORREGIR LA FECHA DE NACIMIENTO y CAMBIAR LA colUSRSOFTWARE por colUsuarios.
			for (int i = 0; i < usuarios.length; i++) {
				colUsuarios.add(usuarios[i]);

				//Obtener facturas PersonalBD del usuario, solicitar el numero de factura y ubicar el objeto Factura Personal
				// en la coleccion de facturas personal de Candela. Y aï¿œadir cada una de las facturas persoanl de Candela encontradas,
				// a una coleccion propia del usuario.
				FacturaPersonalBD[] factUsr= usuarios[i].getColFacturas();

				factsPers=new ArrayList<FacturaPersonal>();

				if(factUsr.length>0){// Si el usuario tiene facturasPersonalBD asociadas, se recorre la coleccion
					// y se crea una coleccion de FacturasPersonal para asociar al usuario.
					//En el caso de que el usuario no tenga ninguna coleccion de FacturasPersonalBD asociadas
					// simplemente se crea el usuario y se le pasa una coleccion vacia de FacturasPersonal
					for (FacturaPersonalBD facturaPersonalBD : factUsr) {
						int codigoFact=facturaPersonalBD.getNumero();
						//Busco la factura que le corresponde en la coleccion de facturas de Candela
						for (FacturaPersonal fp : facturasPersonal) {
							if(codigoFact==fp.getNumero()){
								factsPers.add(fp);
								break;
							}
						}

					}
				}

				//Aï¿œado el usaurio a la coleccion de usuarios de Candela, junto con todas sus facturas.
				try{	colUSRSOFTWARE.add(new Usuario(usuarios[i].getNombreUsr(), usuarios[i].getContrasenia(),
						usuarios[i].getDni(), usuarios[i].getNombre(), usuarios[i].getApellido(), new Date(),factsPers) );
				}catch (NullPointerException e){

				}
			}//Fin del For

		}else{ //TODO MODIFICADO RODRIGO
			//SI no existen usuarios, se carga un usuario Operador de Datos por defecto
			//Con nombre: Admin y password: admin. Este usuario no tiene facturas asociadas,
			//por lo que solamente se crea una coleccion vacia de facturas para el mismo.
			System.out.println("No se encontraron usuarios cargados en el sistema!");
			Usuario administrador= new Usuario("Admin", "admin",000000," administrador", "administrador", new Date(),new ArrayList<FacturaPersonal>());
			colUSRSOFTWARE.add(administrador);

			//Se carga el usuario en la BD. De esta forma, la proxima vez que se inicie el sistema, y no tengan usuarios cargados,
			//se leera desde memoria.
			usuarioBD administradorBD= conexion.create(usuarioBD.class);
			administradorBD.setApellido("administrador");
			administradorBD.setNombreUsr("Admin");
			administradorBD.setContrasenia("admin");
			administradorBD.setDni(000000);
			administradorBD.setFecha_nacimiento(new Date());
			administradorBD.setNombre("administrador");
			//Se busca el nroTipoUsr en la BD, y se crea una instancia de un tipo operador de datos con el EM.

			TipoDeUsrBD[] tipoOpDatos= null;
			tipoOpDatos= conexion.find(TipoDeUsrBD.class,"nroTipoUsr=?",4);

			//Se asocia el tipoOpDatos con el administradorBD
			administradorBD.setTipoDeUsrBD(tipoOpDatos[0]);
			administradorBD.save();			

			//Por ultimo se añade a administradorBD en la coleccion de usuariosBD de Candela.
			colUsuarios.add(administradorBD);


		}
		return resultado;

	}

	/**
	 * Este metodo realiza la carga de los usuarios en memoria desde la BD. En el caso de no existir ningun,
	 * tipodeUsr en el sistema se crean los tipos de usuarios, y se los carga en el sistema.
	 */

	private void cargarTiposUsr() throws SQLException{ //TODO MODIFICADO RODRIGO.

		TipoDeUsrBD[] tipoUsr=null;

		tipoUsr= conexion.find(TipoDeUsrBD.class);

		if(tipoUsr.length>0){// Si existen usuarios cargados en el sistema.

			for (TipoDeUsrBD t1 : tipoUsr) { //Se agregan a la coleccion de tiposUsuarios de Candela
				colTipoUsr.add(t1);
			}

		}else{// Si no existen, se crean los 4 tipos  de usuarios basicos (4-Operador de Datos, 3- Ejecutivo, 2- Director y 1-Vendedor)

			for (int i = 0; i < 4; i++) {
				TipoDeUsrBD t1= conexion.create(TipoDeUsrBD.class);
				t1.setnroTipoUsr(i+1);
				t1.save();
				colTipoUsr.add(t1);
			}
		}//fin if-else		
	} // fin cargarTiposUsr()

	private void cargarPedidosPersonal() {
		//Buscar pedidos en la BD y luego volcarlos a una coleccion en memoria.
		PedidoPersonalBD [] pedPers=null;
		try {
			pedPers=conexion.find(PedidoPersonalBD.class);
		} catch (SQLException e) {
			System.out.println("Error al cargar los pedidos del Personal en la BD");
			e.printStackTrace();
			System.exit(Constantes.EXCEPCION_SQL);
		}
		if(pedPers.length>0){//Si existen pedidos Personal Cargados en la BD

			//Obtener por cada pedido personalBD sus detalles persistentes y luego crear un objeto Pedido para cargarlo en memoria

			ArrayList<DetallePedidoPersonal> detallesRAM= new ArrayList<DetallePedidoPersonal>();

			for(PedidoPersonalBD p1:pedPers){
				DetallePedidoPersonalBD [] dets= p1.getColDetallesPedidoPersonalBD();


				for (int i = 0; i < dets.length; i++) {
					//Por cada detalleBD se obtiene el codigo del producto asociado con este detalle,
					// y se busca en la coleccion de productos en memoria, de Candela.


					int codigoProd = dets[i].getProductoBD().getCodigo();
					//Se busca el producto de memoria. No se realiza una comprobacion del producto al final del while,
					//ya que se supone que el producto existe si esta en un detalle.
					int posProducto=0;
					while(posProducto < colProductos.size() && codigoProd!=colProductos.get(posProducto).getCodigo()){ 
						posProducto++;
					}					
					//Se crea el detalle en memoria con todos los datos de su objeto persistente
					DetallePedidoPersonal d1=new DetallePedidoPersonal(dets[i].getEntityManager(), colProductos.get(posProducto));
					d1.setCantidad(dets[i].getCantidad());

					//Se guarda el detalle la coleccion de detalles de memoria
					detallesRAM.add(d1);

				}//Fin de busqueda y carga de detalles en memoria			

				//Una vez que se levantaron los detalles del pedido en Memoria, se carga el pedido
				pedidosPersonal.add(new PedidoPersonal(p1.getEntityManager(),detallesRAM,p1.getNumeroPedido(),p1.getFechaEmision(),p1.getFechaRecepcion()));


				//Crear otra coleccion con la misma referencia de detallesRAM, que servira para representar los detalles
				// de otro pedido.
				detallesRAM= new ArrayList<DetallePedidoPersonal>();

			}//Fin de busqueda de Pedidos

		}else{
			System.out.println("No se detecto nigun pedido Personal cargado en la BD! ");
		}
	}

	private void cargarFacturasPersonal() {
		//Buscar las FacturasPersonal en la BD
		FacturaPersonalBD [] factPers=null;
		try {
			factPers=conexion.find(FacturaPersonalBD.class);
		} catch (SQLException e) {
			System.out.println("Error al cargar las Facturas del personal desde la BD");
			e.printStackTrace();
			System.exit(Constantes.EXCEPCION_SQL);
		}
		if(factPers.length>0){//Si existen facturas Personal


			System.out.println("Longitud de Factpers: "+factPers.length);

			//ArrayList<DetalleDeFacturaPersonal> detallesRAM= new ArrayList<DetalleDeFacturaPersonal>();

			for(FacturaPersonalBD f1:factPers){
				//Se leen los detalles de las facturas y se crea una coleccion que almacena facturas en RAM
				/*	DetalleFacturaPersonalBD [] dets= f1.getColDetalleFacturaPersonalBD();
			System.out.println("Longitud de Detalles : "+dets.length);
			for (int i = 0; i < dets.length; i++) {
				//Por cada detalleBDFacturaPersonal, se buscan sus atributos y se los a�ade a una coleccion de memoria
				//BUSCAR EL DETALLE DE FACTURA A PERSONAL QUE DATOS CONTIENE????
				DetalleDeFacturaPersonal d1= new DetalleDeFacturaPersonal(dets[i].getEntityManager());
				d1.setCantidadProd(dets[i].getCantidad());
				d1.setPrecioUnitario((float)dets[i].getPrecioUnitario());
				detallesRAM.add(d1);

			}//Fin de busqueda y carga de detalles en memoria	
				 */			
				//Se busca el nroPedido asociado con la facturaPerosnal

				int codigoPedido=f1.getPedidoPersonalBD().getNumeroPedido();
				int posPedidoPersonal=0;
				while(posPedidoPersonal < pedidosPersonal.size() && codigoPedido!=pedidosPersonal.get(posPedidoPersonal).getNumeroPedido()){
					posPedidoPersonal++;
				}

				//Se a�ade la factura a la coleccion de facturas de Candela

				facturasPersonal.add(new FacturaPersonal(f1.getEntityManager(), pedidosPersonal.get(posPedidoPersonal),f1.getNumero(),f1.getTipo(),f1.getFecha(),f1.getPagada()));

			}
		}else{
			System.out.println("No se detectaron facturas personal cargadas en la BD! ");
		} 

	}

	private void cargarPedidoFabrica() {

		PedidoFabricaBD  [] pedFab=null;
		try {
			pedFab=conexion.find(PedidoFabricaBD.class);
		} catch (SQLException e) {
			System.out.println("Error al cargar los pedido de Fabrica desde la BD");
			e.printStackTrace();
			System.exit(Constantes.EXCEPCION_SQL);
		}

		if(pedFab.length>0){//Si existen pedidos a fabrica cargados

			//La coleccion de pedidoPersonal1, representa los pedidos personal que estan asociados con el pedidoFabricaBD, en memoria

			ArrayList<PedidoPersonal> pedidosPersonal1= new ArrayList<PedidoPersonal>();

			for(PedidoFabricaBD  pf1: pedFab){
				//Se debe leer por cada pedido a FabricaBD, el pedidoPersonalBD y obtener su numero, para 
				//buscarlo en la coleccion de pedidosPersonal de Candela, que ya esta cargada

				PedidoPersonalBD [] pedsPersonal= pf1.getColPedidoPersonalBD();


				for (int i = 0; i < pedsPersonal.length; i++) {

					int nroPedidoPersonal= pedsPersonal[i].getNumeroPedido();

					//Se busca la posicion del pedido en la coleccion de Candela
					int posPedidoPers=0;
					while( posPedidoPers< pedidosPersonal.size() && nroPedidoPersonal!= pedidosPersonal.get(posPedidoPers).getNumeroPedido()){
						posPedidoPers++;
					}//Fin de busqueda de PedidoPersonal en Candela
					pedidosPersonal1.add(pedidosPersonal.get(posPedidoPers));


				}//Fin  de iteracion de PedidoFabricaBDs

				pedidosFabricaNoFacturados.add(new PedidoFabrica(pf1.getEntityManager(),pedidosPersonal1,pf1.getNumeroPedido(),pf1.getFechaEmision(),pf1.getFechaRecepcion(),pf1.getRecibido()));

				//Se crea una nueva coleccion para otro pedidoPersonal.
				pedidosPersonal1=new ArrayList<PedidoPersonal>();

			}//Fin de Busqueda de PedidosFabrica

		}else{
			System.out.println("No se dectaron pedidos a fabrica cargados");
		}

	}//Fin de Carga de PedidoFabrica

	private void cargarFacturasFabrica() {

		//Encontrar todas las facturas a Fabrica
		FacturaFabricaBD [] facturasFab=null;
		try {
			facturasFab=conexion.find(FacturaFabricaBD.class);
		} catch (SQLException e) {
			System.out.println("Error al cargar las Facturas del Fabrica desde la BD");
			e.printStackTrace();
			System.exit(Constantes.EXCEPCION_SQL);
		}

		if(facturasFab.length>0){

			//Por cada factura a Fabrica obtener el numero de PedidoFabricaBD que corresponda, y utilizarlo para
			//buscar en la coleccion de pedidosFabrica de Candela. Luego a�adirlo a una segunda coleccion de PeidosFabrica y
			// al final agregar la nueva factura a fabrica con su pedido a la coleccion de Candela
			for (int i = 0; i < facturasFab.length; i++) {
				int nroPedFab= facturasFab[i].getPedidoFabricaBD().getNumeroPedido();

				//Busco el pedidoFabrica en la coleccion en memoria de Candela
				int posPedidoFab=0;
				while( posPedidoFab<pedidosFabricaNoFacturados.size() && nroPedFab!=pedidosFabricaNoFacturados.get(posPedidoFab).getNumeroPedido()){
					posPedidoFab++;
				}

				facturasFabrica.add(new FacturaFabrica(facturasFab[i].getEntityManager(),pedidosFabricaNoFacturados.get(posPedidoFab),facturasFab[i].getNumero(),facturasFab[i].getTipo(),facturasFab[i].getFecha()));


			}//Fin de recorrido FacturasFabBD

		}else{
			System.out.println("No se detectaron facturas a fabrica cargadas en la BD! ");
		}
	}//Fin de Carga de Facturas a Fabrica

	/***
	 * consPrecioProd
	 * Consulta el precio de un producto dado un codigo
	 * @param codigo:entero es el identificador de codigo
	 * @return:real devuelve el precio de un producto
	 * @throws ProductoNoExisteExcepcion 
	 */
	public double consPrecioProd(int codigo) throws ProductoNoExisteExcepcion {

		Producto consultaprecio= new Producto(this.conexion);
		return consultaprecio.consPrecioProd(codigo, getProductos());

	}



	/***
	 * altaDeProducto
	 * Realiza una alta de producto, crea un producto, con los parametros
	 * @param codigo: entero identificador del producto
	 * @param descripcion: cadena, es la descripcion o comentario del producto
	 * @param precio: real, es el precio del producto
	 * @param cantidad: entero, es la cantidad de productos en stock
	 * @return un error dependiendo el problema
	 * @throws SQLException 
	 * @throws ProductoExisteExcepcion 
	 */
	public void altaDeProducto(int codigo, String descripcion, double precio, int cantidad) throws ProductoExisteExcepcion, SQLException{

		Producto producto= new Producto(this.conexion);

		producto.altaDeProducto(codigo, descripcion, precio, cantidad, getProductos());


	}
	/***
	 * bajaDeProducto
	 * Realiza una baja de un producto dependiendo del codigo pasado por parametro
	 * @param codigo
	 * @return
	 * @throws SQLException 
	 * @throws ProductoNoExisteExcepcion 
	 * @throws TomoAsignadoExcepcion 
	 */
	public void bajaDeProducto(int codigo) throws TomoAsignadoExcepcion, ProductoNoExisteExcepcion, SQLException{

		Producto productoBaja= new Producto(this.conexion);

		productoBaja.bajaDeProducto(codigo, getProductos());


	}
	/***
	 * modDeProducto
	 * Realiza la modificacion del preco y cantidad en stock de un producto
	 * @param codigo: entero
	 * @param precio: real
	 * @param cantidad: entero
	 * @return
	 * @throws ProductoNoExisteExcepcion 
	 * @throws SQLException 
	 */
	public void modDeProducto(int codigo, double precio, int cantidad) throws ProductoNoExisteExcepcion, SQLException {
		Producto prodmod=new Producto(this.conexion);

		try {
			prodmod.modDeProducto(codigo, precio, cantidad, getProductos());
		}catch(SQLException error){

			//si hay error lo libero y no modifico la memoria
			throw  new SQLException();
		}


		//TODO [Damian] 11-9-2012
		for (int i = 0; i < getColProductos().size(); i++) {
			if (getColProductos().get(i).getCodigo() == codigo){
				String descripcion= getColProductos().get(i).getDescripcion();
				getColProductos().remove(i);//lo remuevo de memoria

				Producto producto= new Producto(conexion);
				producto.setCantidadEnStock(cantidad);
				producto.setCodigo(codigo);
				producto.setPrecio(precio);
				producto.setDescripcion(descripcion);
				getColProductos().add(producto);
			}

		}


	}
	/***
	 * asignarATipoDeProducto
	 * Asigna un tipo de producto a un producto determinado pasado por argumento
	 * @param tipoProducto
	 * @param codigoproducto
	 * @return
	 * @throws TipoProductoExcepcion 
	 * @throws ProductoNoExisteExcepcion 
	 * @throws SQLException 
	 */
	public void asignarATipoDeProducto(int tipoProducto, int codigoproducto) throws ProductoNoExisteExcepcion, TipoProductoExcepcion, SQLException{

		Producto productoasociar =new Producto(conexion);
		//El siguiente método se encarga de JAVA<-->BD
		productoasociar.asignarATipoProducto(tipoProducto, codigoproducto, getProductos(), getColTipoDeProducto());
		//TODO [DAMIAN] falta actualizar a memoria rev 15-9-12
		for (int i = 0; i < colProductos.size(); i++) {
			if (colProductos.get(i).getCodigo()==codigoproducto){
				//si lo encuentro lo agrego a memoria
				//primero hago un resguardo
				Producto prod= new Producto(getEm());
				prod.setCantidadEnStock(colProductos.get(i).getCantidadEnStock());
				prod.setCodigo(codigoproducto);
				prod.setDescripcion(colProductos.get(i).getDescripcion());
				prod.setPrecio(colProductos.get(i).getPrecio());
				prod.setTipoProducto(tipoProducto);

				//saco el producto
				colProductos.remove(i);
				colProductos.add(prod);
			}
		}
	}
	/***
	 * modificarDescripcionTipoProducto
	 * Modifica la descripcion o comentario de un tipo de producto
	 * @param codigoTipoDeProducto
	 * @param descripcion
	 * @return
	 * @throws TipoProductoExcepcion 
	 */
	public void modificarDescripcionTipoProducto(int codigoTipoDeProducto, String descripcion) throws TipoProductoExcepcion{
		TipoDeProducto tipo= new TipoDeProducto(this.conexion);

		tipo.modificarDescripcionTipoProducto(codigoTipoDeProducto, descripcion, getColTipoDeProducto());


	}


	/**
	 * getColTipoUsr()
	 * Devuelve la coleccion de tipos de usuarios
	 * @return colTipoUsr
	 */
	public static ArrayList<TipoDeUsrBD> getColTipoUsr() {
		return colTipoUsr;
	}

	/***
	 * getColTipoDeProducto()
	 * @return devuelve la coleccion de tipos de productos
	 */
	public ArrayList<TipoDeProductoBD> getColTipoDeProducto(){
		return colTipoproducto;
	}
	/***
	 * setColTipoDeProducto
	 * setea una coleccion con el parametro pasado
	 * @param colTipoDeProducto
	 */
	public void setColTipoDeProducto(ArrayList<TipoDeProductoBD> colTipoDeProducto){
		this.colTipoproducto=colTipoDeProducto;
	}

	/***
	 * setColTipoUsr
	 * Setea la coleccion de tipo de usuario
	 * @param colTipoUsr
	 */
	public static void setColTipoUsr(ArrayList<TipoDeUsrBD> colTipoUsr) {
		Candela.colTipoUsr = colTipoUsr;
	}

	/***
	 * getColUsuarios
	 * Devuelve una coleccion de usuarios
	 * @return
	 */
	public ArrayList<usuarioBD> getColUsuarios() {
		return colUsuarios;
	}

	/***
	 * setColUsuarios
	 * Setea la coleccion de usuarios
	 * @param colUsuarios
	 */
	public void setColUsuarios(ArrayList<usuarioBD> colUsuarios) {
		this.colUsuarios = colUsuarios;
	}

	/***
	 * getColProductos
	 * Devuelve la coleccion de productos
	 * @return una colección de productos
	 */
	public  ArrayList<Producto> getProductos(){
		return this.colProductos;
	}
	/**
	 * setCatalogoVigente
	 * Setea el catalogo vigente en Candela.
	 * @param catalogoVigente: el catalogo que ser� seteado en Candela, como el catalogo en vigencia.
	 */
	public void setCatalogoVigente(Catalogo catalogoVigente) {
		this.catalogoVigente = catalogoVigente;
	}
	/**
	 * getCatalogoVigente
	 * Obtiene una referencia al catalogo vigente en Candela.
	 * @return: el catalogo que esta en vigencia.
	 */
	public Catalogo getCatalogoVigente() {
		return catalogoVigente;
	}
	/**
	 * getEm
	 * Obtiene el manejador de entidades, empleado para realizar consultas y escrituras en la base de datos.
	 * @return: una referencia al manejador de entidades.
	 */
	public EntityManager getEm() {
		return conexion;
	}
	/**
	 * Este metodo retorna el ultimo catalogo cargado en la BD.
	 * @return: Retorna el ID del ultimo catalogo cargado en la BD o -1 si no
	 * existen catalogos cargados en la BD.
	 */
	private int ObtenerUltimoCatalogo(){
		int resultado=-1;
		//Obtener todos los catalogos y cargar siempre el ultimo catalogo.
		//Se carga siempre el ultimo catalogo ya que el ID manejado por AO es incremental.
		CatalogoBD[] catalogos=null;
		try {
			catalogos = conexion.find(CatalogoBD.class);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println("Error al buscar el ultimo catalogo en la BD");
			e1.printStackTrace();
			System.exit(Constantes.EXCEPCION_SQL);
		}
		if(catalogos.length>0){
			resultado=catalogos[catalogos.length-1].getID();
		}		
		return resultado;
	}


	/**
	 * cargarCatalogoVigente
	 * Este metodo carga el catalogo vigente, con sus correspondientes tomos y productos en memoria.
	 */	
	private void cargarCatalogoVigente(){



		//Verificar si existe al menos un catalogo cargado en la BD y cargarlo en memoria.
		int id_ultimo_catalogo= this.ObtenerUltimoCatalogo();
		if(id_ultimo_catalogo!=-1){
			try {
				//1.Buscar el CatalogoVigente en la BD e instanciar el catalogoVigente
				CatalogoBD [] cats= conexion.find(CatalogoBD.class,"id= ?",id_ultimo_catalogo);
				//Se carga el catalogoVigente que es el ultimo catalogo cargado

				int anioVigencia= cats[0].getAnioVigencia();

				Calendar fecha_inicio= Calendar.getInstance();
				fecha_inicio.set(anioVigencia,fecha_inicio.get(Calendar.MONTH),fecha_inicio.get(Calendar.DAY_OF_WEEK));

				catalogoVigente= new Catalogo(this.conexion, fecha_inicio.getTime());

				//2.Obtener por medio de los getColTomos() los tomosBD y por cada tomoBD, crear un Tomo comun, aÃ±adirlo a la coleccion de tomos
				// de catalogoVigente.
				TomoBD tomos[]= cats[0].getColTomos();

				//Indice i: Tomo actual de la coleccion de tomos
				//Indice j: Producto actual del tomo actual de la coleccion de tomos
				for (int i = 0; i < tomos.length; i++) {
					Tomo tom1=new Tomo(this.conexion);
					tom1.setCodigoTomo(tomos[i].getCodigoTomo());
					tom1.setDescripcion(tomos[i].getDescripcion());
					catalogoVigente.getTomos().add(tom1);

					//3. Con el index (i) de tomosBD, acceder a tomosBD[i], y obtener los ProductosBD de ese tomo
					// y  recorrer el arreglo de ProductosBD y generar los productos comunes.
					// Luego con el mismo index "i", acceder al tomo recien aÃ±adido a catalogoVigente, y aÃ±adir el Producto
					// comun a dicho tomo.

					ProductoBD [] prods= tomos[i].getProductos();

					System.out.println("Mostrando los Productos del tomo 2");
					for (int s = 0; s < prods.length; s++) {
						System.out.println("Producto: "+prods[s].getCodigo());
					}

					//TODO MODIFICADO RODRIGO
					for (int j = 0; j < prods.length; j++) {
						Producto prod1= new Producto(this.conexion);
						prod1.setCodigo(prods[j].getCodigo());
						prod1.setDescripcion(prods[j].getDescripcion());
						prod1.setPrecio(prods[j].getPrecio());
						prod1.setCantidadEnStock(prods[j].getCantidadEnStock());
						int tipoProducto;
						try{
							tipoProducto= prods[j].getTipoDeProductoBD().getCodTipoProd(); //TODO MODIFICADO RODRIGO
						}catch (Exception e){
							tipoProducto=-1;//TODO [DAMIAN] al leer un producto que no tiene seteado su tipo genera un nullpointer
						}

						prod1.setTipoProducto(tipoProducto);							   //TODO MODIFICADO RODRIGO.
						catalogoVigente.getTomos().get(i).getProductos().add(prod1);
					}


				}

				System.out.println("Catalogo vigente ledio correctamenete");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error al leer el catalogo vigente de la BD");
				System.exit(1);
			}
		}else{//Si no existe ningun catalogo, se crea un nuevo catalogo con una coleccion de tomos vacia.

			//TODO NOTA: SI NO HAY CATALOGO CREADO EN LA BD: CREAR UN CATALOGO EN MEMORIA CON  FECHA DE INCIO COMO LA FECHA ACTUAL,
			// Y UNA FECHA DE FIN = FECHA_INICIO+30 DIAS.

			catalogoVigente= new Catalogo(this.conexion, new Date());
			/*catalogoVigente.setTomos(new ArrayList<Tomo>());
			ArrayList<Tomo> tom1= catalogoVigente.getTomos();*/
		}

	}

	/**
	 * altaCatalogo
	 * Este metodo retona una referencia a un nuevo catalogo.
	 * @param fecha_inicio: fecha a partir de la que el catalogo tiene vigencia
	 * @param fecha_fin: fecha hasta la que el catalogo tiene vigencia.
	 * @return: una nueva instancia de un objeto Catalogo.
	 */
	public void altaCatalogo(Date fecha_inicio){
		//catalogoNuevo=new Catalogo(em,fecha_inicio,fecha_fin); //CATALOGO TENTATIVO A SER CREADO
		//return new Catalogo(em,fecha_inicio,fecha_fin);
		this.catalogoNuevo= new Catalogo(this.conexion, fecha_inicio);
	}


	public Catalogo getCatalogoNuevo(){
		return this.catalogoNuevo;
	}

	public void setCatalogoNuevo(Catalogo cat){
		this.catalogoNuevo=cat;

	}

	/**
	 * Esta funcion determina si un producto esta cargado en el sistema.
	 * @return resultado: es true si el producto esta cargado en el sistema, o false si no esta cargado en el sistema. 
	 * 
	 */

	public boolean productoEstaCargado(int codigoProducto){
		boolean resultado=true;

		int i=0;
		while( i<colProductos.size() && (colProductos.get(i).getCodigo()!=codigoProducto)){
			i++;
		}
		if(i>=colProductos.size()){
			resultado=false;
		}
		return resultado;

	}



	/**
	 * Este metodo verifica si se tiene una referencia a CatalogoNuevo o si este es null.
	 * El resultado de este metodo se emplea para el AltaDeTomo().
	 * @return Este metodo retorna false, si no se esta creando un catalogoNuevo en memoria
	 *o true si se esta creando un catalogoNuevo en memoria.
	 */
	public boolean esCatalogoNuevo(){
		boolean resultado=false;
		if(catalogoNuevo!=null){
			resultado=true;
		}
		return resultado;
	}





	/**
	 * Este metodo determina si un producto existe en la coleccion de productos de Candela en base a su codigo. Si es asi,
	 * retorna la posicion del producto en la coleccion de Productos de Candela, sino retorna -1.
	 * @return resultado: retorna -1 si el producto no existe en la coleccion de productos de candela,
	 * y la posProducto si el producto existe.
	 */
	public int existeProductoCandela(int codigoProd){
		int resultado=-1;

		int posprod=0;
		while( posprod<colProductos.size() && (colProductos.get(posprod).getCodigo()!=codigoProd)){
			posprod++;
		}
		if(posprod<colProductos.size()){
			resultado=posprod;
		}				
		return resultado;
	}


	/**
	 * Este metodo determina si un producto existe en la coleccion de productos de Candela en base a su codigo. Si es asi,
	 * retorna la posicion del producto en la coleccion de Productos de Candela, sino retorna -1.
	 * @return resultado: retorna -1 si el producto no existe en la coleccion de productos de candela,
	 * y la posProducto si el producto existe.
	 */
	public int existeProductoCandela(String nombre){
		int resultado=-1;

		int posprod=0;
		while( posprod<colProductos.size() && (!colProductos.get(posprod).getDescripcion().equals(nombre))){
			posprod++;
		}
		if(posprod<colProductos.size()){
			resultado=posprod;
		}				
		return resultado;
	}



	/**
	 * Este metodo busca una factura Fabrica en la coleccion de facturas de Candela y retorna un valor
	 * que indica si la factura figura en la coleccion o no.
	 * @return: la posicion de la factura fabrica en la coleccion de facturas de Candela o -1 si no 
	 * encuentra la factura fabrica.
	 */
	public int verificarFacturaFabrica(int numeroFactFabrica){
		int resultado=-1;

		int posFacturaFab=0;
		while(posFacturaFab<facturasFabrica.size() && facturasFabrica.get(posFacturaFab).getNumero()!=numeroFactFabrica){
			posFacturaFab++;
		}

		if(posFacturaFab<facturasFabrica.size()){
			resultado=posFacturaFab;
		}

		return resultado;
	}

	/**
	 * Este metodo busca en la coleccion de pedidos no facturados de Candela un nroPedido ingresado.
	 * 
	 * @return: retorna la posicion del pedido en la coleccion de pedidosNoFacturados de Candela o -1 si no encuentra
	 * el pedido ingresado.
	 */
	public int verificarPedido(int nroPedido){
		int resultado=-1;
		//Buscar en los pedidos fabrica de candela el nro de pedido ingresado
		//ArrayList<PedidoFabrica> peds= candela.getPedidosFabricaNoFacturados();

		int posPedido=0;
		for(posPedido=0; posPedido<pedidosFabricaNoFacturados.size();posPedido++){
			if(nroPedido==pedidosFabricaNoFacturados.get(posPedido).getNumeroPedido()){//Si encuentro el pedido
				resultado=posPedido;
			}

		}
		return resultado;
	}

	/**
	 * Este metodo se encarga de a�adir a la coleccion de facturas Fabrica  de Candela una nueva factura impaga y realizar
	 * las modificaicones correspondientes en la BD.
	 * @param pedido
	 * @param nroPedido
	 * @param tipoFactura
	 * @param fechaFactura
	 */
	public void agregarFacturaImpaga(PedidoFabrica pedidofab, int tipoFactura, Date fechaFactura) throws SQLException{

		//Crear y a�adir la facturaImpaga del pedido seleccionado.
		facturasFabrica.add( new FacturaFabrica(this.conexion,pedidofab,pedidofab.getNumeroPedido(),tipoFactura,fechaFactura) );

		//Crear factura Fabrica Impaga en base de Datos


		FacturaFabricaBD factfab=this.conexion.create(FacturaFabricaBD.class);
		factfab.setNumero(pedidofab.getNumeroPedido());
		factfab.setTipo(tipoFactura);
		factfab.setFecha(fechaFactura);
		factfab.setPagada(false);
		//Buscar el pedido a fabrica de esa facturaFabrica
		PedidoFabricaBD [] pedd= this.conexion.find(PedidoFabricaBD.class,"numeropedido=?",pedidofab.getNumeroPedido());

		factfab.setPedidoFabricaBD(pedd[0]);
		factfab.save();

	}


	/**
	 * Este metodo se encarga de registrar las facturas Fabrica como pagadas en memoria y en BD.
	 * @return
	 */

	public void guardar_Facts_Fabrica(int posFactFab) throws SQLException{
		//Setear la facturaFabrica como factura paga en memoria 
		facturasFabrica.get(posFactFab).setPagada(true);
		this.almacenar_facts_fab(facturasFabrica.get(posFactFab).getNumero());
	}

	private void almacenar_facts_fab(int numeroFactFab)throws SQLException{

		//Setear la facturaFabrica como factura paga en la Base de Datos			
		FacturaFabricaBD[] fact=conexion.find(FacturaFabricaBD.class,"numero=?",numeroFactFab);
		fact[0].setPagada(true);
		fact[0].save();

	}


	//TODO Parte de Damian
	@SuppressWarnings("deprecation")
	public void anulacionPedido(int nroPedido, String userName) throws SQLException, FacturaVencidaExcepcion, FacturaPagadaExcepcion, ProductoNoExisteExcepcion{
		//Busco el usuario
		ArrayList<usuarioBD> usuarios=this.getColUsuarios();
		for (int i = 0; i < usuarios.size(); i++) {
			if (usuarios.get(i).getNombreUsr().equals(userName)){
				//si encuentro al usuario busco todas las facturas de ese usuario
				FacturaPersonalBD[] facturas=usuarios.get(i).getColFacturas();
				//busco la factura con el pedido
				for (int j = 0; j < facturas.length; j++) {
					if (facturas[j].getNumero() == nroPedido){
						//Obtengo primero el dia de hoy
						Date hoy= new Date();
						//reviso si a lo mucho la facturacion se realizo un mes antes
						if (facturas[j].getFecha().getMonth() + 1 >= hoy.getMonth()){
							//si la factura no se encuentra paga
							if (!facturas[j].getPagada()){
								for (int j2 = 0; j2 < this.getFacturasPersonal().size(); j2++) {
									if (this.getFacturasPersonal().get(j2).getNumero()==facturas[j].getNumero()){
										//Obtengo el pedido de la factura para eliminarlo
										PedidoPersonalBD pedido=facturas[j].getPedidoPersonalBD();
										//me fijo en memoria si se solicit� si integra el pedido a fabrica si fuera asi actualizo el stock de producto
										ArrayList<PedidoFabrica> pedidosFabrica = this.getPedidosFabricaNoFacturados();
										for (int k = 0; k < pedidosFabrica.size(); k++) {
											ArrayList<PedidoPersonal> pedidosMemoria = pedidosFabrica.get(k).getPedidos();
											for (int l = 0; l < pedidosMemoria.size(); l++) {
												if (pedidosMemoria.get(l).getNumeroPedido() == pedido.getNumeroPedido()){
													actualizarStock(pedido);
												}
											}
										}
										//busco el pedido dentro de la coleccion en memoria para eliminarlo
										for (int k = 0; k < getPedidosPersonal().size(); k++) {
											if (getPedidosPersonal().get(k).getNumeroPedido() == pedido.getNumeroPedido()){
												//si encuentro el pedido lo elimino de memoria
												this.pedidosPersonal.remove(k);
												//remuevo la factura
												this.facturasPersonal.remove(j2);
												//Las saco de base de datos a la factura y al pedido
												conexion.delete(facturas[j]);
												conexion.delete(pedido);
											}
										}
									}
								}
							}else{
								throw new FacturaPagadaExcepcion();
							}
						}else{
							throw new FacturaVencidaExcepcion();
						}
					}
				}
			}
		}
	}

	private void actualizarStock(PedidoPersonalBD pedido) throws SQLException, ProductoNoExisteExcepcion {
		//obtengo la coleccion de detalles
		DetallePedidoPersonalBD[] colDetalles = pedido.getColDetallesPedidoPersonalBD();




		for (int i = 0; i < colDetalles.length; i++) {
			//por cada detalle sumo la cantidad a producto en base de datos
			for (int j = 0; j < colProductos.size(); j++) {
				//por cada producto busco en el detalle el producto apuntado
				if (colProductos.get(j).getCodigo() == colDetalles[i].getProductoBD().getCodigo()){
					//actualizo en memoria
					colProductos.get(j).setCantidadEnStock(colProductos.get(j).getCantidadEnStock()+colDetalles[i].getCantidad());
					//modifico el producto agregando el nuevo valor de ColProductos
					modDeProducto(colProductos.get(j).getCodigo(), colProductos.get(j).getPrecio(), colProductos.get(j).getCantidadEnStock());
				}

			}
		}
		conexion.delete(colDetalles);
	}







	public void ventaEnLocal(usuarioBD usuario, ArrayList<DetallePedidoPersonal> colDetalles) throws SQLException{
		/*Este caso de uso tiene que crear la factura
		 * Muestra la disponibilidad de los productos seleccionados por el usuario, debo generar XML para que el usuario
		 * seleccione de una lista desplegable
		 * Agrega el/los productos al pedido
		 * Crea la factura 
		 * Si el vendedor no posee deudas puede adeudarlo*/

		//tengo que sacar las cantidades de memoria como lo hago en la Base de datos

		GeneradorXML xml= new GeneradorXML(this);


		//Solicito todos los pedidos 
		ArrayList<PedidoPersonal> pedidos = getPedidosPersonal();
		//me fijo cual es el �ltimo

		int nroPedido = pedidos.size()+1;
		//Creo un pedido con los detalles

		PedidoPersonal pedido= new PedidoPersonal(conexion, colDetalles, nroPedido, new Date(), new Date());
		
		//se crea la factura con el pedido
		//TODO ver el tema del tipo y de pagado
		
		//Se crea el pedido en BD con los detalles y los productos.
		pedido.crearPedido(colDetalles, getColProductos());
		
		
		//si no tuve problemas al crear el pedido con sus detalles
			FacturaPersonal facturaPersonal= new FacturaPersonal(conexion, pedido, nroPedido, 1, pedido.getFecha_emision(),true);
			//Se envia directamente el numero de pedido que se obtiene del pedido en memoria
		
			//Se guarda en factura en BD.
			facturaPersonal.crearFactura(facturaPersonal, usuario,pedido.getNumeroPedido());

			
			//facturaPersonal.crearFactura(facturaPersonal, usuario,pedidoBD);
			//actualizar en memoria los productos, el pedido, la factura de personal
			//primero actualizo los productos
			
			//actualizo los pedidos
			getPedidosPersonal().add(pedido);
			//actualizo las facturas
			getFacturasPersonal().add(facturaPersonal);
			//llamo a los xml generadores correspondientes
			
			//TODO ver si es necesario actualizar tambien los productos...
			//genero los pedidos
			xml.generarXMLPedidoPersonal();
			//genero las facturas
			xml.generarXMLFacturasPersonal();
			
		


	}
	/***
	 * Este metodo actualiza la coleccion de productos
	 * precisamente la cantidad para mantener actualizado ante varios pedidos simultáneos
	 * y poder reflejarlos en flash por medio de los xml
	 * @param colDetalles
	 */
	public void actualizarColProdConDetalle(ArrayList<DetallePedidoPersonal>colDetalles){
		for (int i = 0; i < colDetalles.size(); i++) {
			//obtengo un detalle y debo comparar en la coleccion ese detalle
			Producto productoEnDetalle = colDetalles.get(i).getProd();
			for (int j = 0; j < getColProductos().size(); j++) {
				if (getColProductos().get(j).getCodigo() == productoEnDetalle.getCodigo()){
					//hago coleccion producto cantidad - detalle cantidad
					
					getColProductos().get(j).setCantidadEnStock(getColProductos().get(j).getCantidadEnStock() - colDetalles.get(i).getCantidad());
					
					

				}

			}

		}
		GeneradorXML xml = new GeneradorXML(this);
		xml.generarXMLProductosEnStock();
	}
	//TODO [DAMIAN] 11-9-2012 agregue nuevos get a set
	public String getDirectorio() {
		return directorio;
	}
	public void setDirectorio(String directorio) {
		this.directorio = directorio;
	}
	//TODO [Damian] 11-9-12 hasta aca


}

