package utilidades;

import java.sql.SQLException;
import java.util.Date;

import net.java.ao.EntityManager;
import persistencia.*;
public class CargadorBD {

	/**
	 * @param args
	 */
	
	private usuarioBD usr1=null; //Damian es OPDatos
	private usuarioBD usr2=null; //Rodrigo es Ejecutivo
	private usuarioBD usr3=null; //Vader es Director
	private usuarioBD usr4=null; //Luke es Vendedor
	
	
	private TomoBD tom1=null;
	private TomoBD tom2=null;	
	private TomoBD tom3=null;
	
	
	/*
	 * TOMO 2--> COSMETICOS
	 * TOMO 3--> ROPAS
	 * Se cargan los productos. Los productos del 200-600 estan asociados al TOMO 2 y del 700-1100 al TOMO 3 del catalogo,
	* El producto 100, esta asociado al tomo 1 del CatalogoViejo, y los productos del 1200 al 1300 estan disponibles para asociar 
	* a un tomo.
	*/
	
	private ProductoBD prod1=null;	//codigo:100	
	private ProductoBD prod2=null;  //codigo:200
	private ProductoBD prod3=null;	//codigo:300
	private ProductoBD prod4=null;  //codigo:400
	private ProductoBD prod5=null;  //codigo:500
	private 	ProductoBD prod6=null; //codigo:600
	private ProductoBD prod7=null; //codigo.700
	private ProductoBD prod8=null; //codigo: 800
	private ProductoBD prod9=null; //Codigo : 900
	private ProductoBD prod10=null;//Codigo : 1000
	private ProductoBD prod11=null;//Codigo : 1100
	//PRODUCTOS LIBRES
	

	private ProductoBD prod12=null;//Codigo : 1200
	private ProductoBD prod13=null; //Codigo: 1300
	
	
	
	public static void main(String[] args) {
		EntityManager em= new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);
		
		
		CargadorBD carg= new CargadorBD();
		carg.cargarUsuarios(em);
		
		carg.cargarCatalogosTomosProds(em);
		
		
					

 
				
				
				System.out.println("Se cargaron datos correctamente los Datos en la BD!");
		
	}
	
	/**
	 * Este metodo carga los usuarios en la BD.
	 * @param em
	 */
	private void cargarUsuarios(EntityManager em){
		
		
		TipoDeUsrBD tipo1=null;
		//NOTA: AÑADIR CODIGOS PARA LOS TIPOS DE USUARIOS.
		
		
		//Cargar usuariois y tipos de usuario
				for (int i = 0; i < 4; i++) {
					try {
						tipo1=em.create(TipoDeUsrBD.class);
						tipo1.setnroTipoUsr(i+1); //TODO MODIFICADO RODRIGO
						tipo1.save();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}
				
				//Arreglo con los tipos de usuarios del sistema
				/*TipoDeUsrBD [] tipos=null;
				try{
					tipos=em.find(TipoDeUsrBD.class);
					
				}catch(SQLException e){
					
					System.out.println("Error al mostrar los tipos de usuarios");
					System.exit(Constantes.EXCEPCION_SQL);
				}*/
				
			
				
				TipoDeUsrBD [] tipoEjecutivo= null;
				try{
					tipoEjecutivo=em.find(TipoDeUsrBD.class,"nroTipoUsr=?",3);
					
				}catch(SQLException e){
					
					System.out.println("Error al mostrar los tipos de usuarios");
					System.exit(Constantes.EXCEPCION_SQL);
				}
				
				
				TipoDeUsrBD [] tipoOpDatos= null;
				try{
					tipoOpDatos=em.find(TipoDeUsrBD.class,"nroTipoUsr=?",4);
					
				}catch(SQLException e){
					
					System.out.println("Error al mostrar los tipos de usuarios");
					System.exit(Constantes.EXCEPCION_SQL);
				}
				
				TipoDeUsrBD [] tipoDirector= null;
				try{
					tipoDirector=em.find(TipoDeUsrBD.class,"nroTipoUsr=?",2);
					
				}catch(SQLException e){
					
					System.out.println("Error al mostrar los tipos de usuarios");
					System.exit(Constantes.EXCEPCION_SQL);
				}
				
				TipoDeUsrBD [] tipoVendedor= null;
				try{
					tipoVendedor=em.find(TipoDeUsrBD.class,"nroTipoUsr=?",1);
					
				}catch(SQLException e){
					
					System.out.println("Error al mostrar los tipos de usuarios");
					System.exit(Constantes.EXCEPCION_SQL);
				}
				

				try {
					 usr1= em.create(usuarioBD.class);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					usr1.setNombreUsr("Rodrigo");
					usr1.setContrasenia("1234");
					usr1.setDni(35887888);
					usr1.setNombre("Rodrigo");
					usr1.setApellido("Huincalef");
					usr1.setFecha_nacimiento(new Date());
					usr1.setTipoDeUsrBD(tipoEjecutivo[0]);
					usr1.save();
				
					try {
						 usr2= em.create(usuarioBD.class);
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						usr2.setNombreUsr("Damian");
						usr2.setContrasenia("1234");
						usr2.setDni(33345146);
						usr2.setApellido("Mansilla");
						usr2.setFecha_nacimiento(new Date());
						usr2.setNombre("Damian");
						usr2.setTipoDeUsrBD(tipoOpDatos[0]);
						usr2.save();
					
						try {
							 usr3= em.create(usuarioBD.class);
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

								usr3.setNombreUsr("Vader");
								usr3.setContrasenia("1234");
								usr3.setDni(32000111);
								usr3.setApellido("Darth");
								usr3.setFecha_nacimiento(new Date());
								usr3.setNombre("Vader");
								usr3.setTipoDeUsrBD(tipoDirector[0]);
								usr3.save();
								
								
								try {
									 usr4= em.create(usuarioBD.class);
									
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

										usr4.setNombreUsr("Luke");
										usr4.setContrasenia("1234");
										usr4.setDni(32222111);
										usr4.setApellido("SkyWalker");
										usr4.setFecha_nacimiento(new Date());
										usr4.setNombre("Luke");
										usr4.setTipoDeUsrBD(tipoVendedor[0]);
										usr4.save();
		
		
	}
	
	/**
	 * Este metodo carga los catalogos, tomos y productos en la BD.
	 * @param em
	 */
	private void cargarCatalogosTomosProds(EntityManager em){
		
		
		//Se cargan los catalogos
		//Catalogo viejo
		CatalogoBD cat1=null;
		
		try {
			cat1= em.create(CatalogoBD.class);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		cat1.setAnioVigencia(1999);
		cat1.save();
		
		//Catalogo actual
		CatalogoBD cat2=null;
		
		try {
			cat2= em.create(CatalogoBD.class);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		cat2.setAnioVigencia(2011);
		cat2.save();
		
		//Se cargan los tomos en la BD		
		//Tomo viejo
		try {
			tom1 = em.create(TomoBD.class);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tom1.setCodigoTomo(1);
		tom1.setDescripcion("Este es un tomo viejo");
		tom1.setCatalogoBD(cat1);
		tom1.save();
				
		// El catalogo actual tiene los tomos 2  y 3 vigentes. El TOMO 2 tiene 5 productos y son todos tipos de productos "COSMETICOS".
		// El TOMO 3 tiene 5 productos, y son tipos de Producto "ROPA".
		
			try {
			tom2 = em.create(TomoBD.class);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tom2.setCodigoTomo(2);
		tom2.setDescripcion("Tomo de Cosmeticos");
		tom2.setCatalogoBD(cat2);
		tom2.save();
		
		
		
	
		try {
			tom3 = em.create(TomoBD.class);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tom3.setCodigoTomo(3);
		tom3.setDescripcion("Tomo de Ropas");
		tom3.setCatalogoBD(cat2);
		tom3.save();		
		
		
				
	
		
		
		//PRODUCTO 1
		try {
			prod1= em.create(ProductoBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prod1.setCodigo(100);
		prod1.setCantidadEnStock(5);
		prod1.setDescripcion("Producto Obsoleto :( ");
		prod1.setPrecio(21.99);
		prod1.setTomoBD(tom1);
		prod1.save();

		//INICIO DE CARGA DE PRODUCTOS DEL TOMO 2
		try {
			prod2= em.create(ProductoBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prod2.setCodigo(200);
		prod2.setCantidadEnStock(5);
		prod2.setDescripcion("Pintalabios");
		prod2.setPrecio(21);
		prod2.setTomoBD(tom2);
		prod2.save();

		try {
			prod3= em.create(ProductoBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prod3.setCodigo(300);
		prod3.setCantidadEnStock(0);
		prod3.setDescripcion("Perfume LaCloaca");
		prod3.setPrecio(600.99);
		prod3.setTomoBD(tom2);
		prod3.save();

		try {
			prod4= em.create(ProductoBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prod4.setCodigo(400);
		prod4.setCantidadEnStock(23);
		prod4.setDescripcion(" Crema para manos");
		prod4.setPrecio(43.21);
		prod4.setTomoBD(tom2);
		prod4.save();

		try {
			prod5= em.create(ProductoBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prod5.setCodigo(500);
		prod5.setCantidadEnStock(10);
		prod5.setDescripcion(" Crema exfoliante");
		prod5.setPrecio(19.99); 
		prod5.setTomoBD(tom2);
		prod5.save();
		
		
		try {
			prod6= em.create(ProductoBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prod6.setCodigo(600);
		prod6.setCantidadEnStock(11);
		prod6.setDescripcion("Esmalte para uñas");
		prod6.setPrecio(18.35); 
		prod6.setTomoBD(tom2);
		prod6.save();
		
		//FIN DE PRODUCTOS DEL TOMO 2
		
		
		
		//INICIO CARGA DE PRODUCTOS TOMO 3
		
		try {
			prod7= em.create(ProductoBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prod7.setCodigo(700);
		prod7.setCantidadEnStock(1);
		prod7.setDescripcion("Remera Gastada");
		prod7.setPrecio(21); 
		prod7.setTomoBD(tom3);
		prod7.save();
		
		
		try {
			prod8= em.create(ProductoBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prod8.setCodigo(800);
		prod8.setCantidadEnStock(0);
		prod8.setDescripcion("Medias de Nailon");
		prod8.setPrecio(13.24); 
		prod8.setTomoBD(tom3);
		prod8.save();
		
		
		try {
			prod9= em.create(ProductoBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prod9.setCodigo(900);
		prod9.setCantidadEnStock(0);
		prod9.setDescripcion("Remera manga corta");
		prod9.setPrecio(67.88); 
		prod9.setTomoBD(tom3);
		prod9.save();
		

		try {
			prod10= em.create(ProductoBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prod10.setCodigo(1000);
		prod10.setCantidadEnStock(0);
		prod10.setDescripcion(" Swetter");
		prod10.setPrecio(69.88); 
		prod10.setTomoBD(tom3);
		prod10.save();
		
		
		try {
			prod11= em.create(ProductoBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prod11.setCodigo(1100);
		prod11.setCantidadEnStock(0);
		prod11.setDescripcion(" Medias");
		prod11.setPrecio(77.9); 
		prod11.setTomoBD(tom3);
		prod11.save();
		//FIN DE CARGA DE PRODUCTOS DEL TOMO 3
		
		//INICIO DE CARGA DE PRODUCTOS LIBRES
		//NOTA: AÑADIR  que solamente se puedan añadir los tipos de producto del mismo tipo que contenga el TOMO.
				
		try {
			prod12= em.create(ProductoBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prod12.setCodigo(1200);
		prod12.setCantidadEnStock(0);
		prod12.setDescripcion("crema para cuerpo");
		prod12.setPrecio(69.88); 
		prod12.save();
		
		
		
		try {
			prod13= em.create(ProductoBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prod13.setCodigo(1200);
		prod13.setCantidadEnStock(0);
		prod13.setDescripcion("Jeans");
		prod13.setPrecio(69.88); 
		prod13.save();
		
		//FIN DE CARGA DE PRODUCTOS LIBRES
		
		
		
		cargarTiposDeProducto(em);
		
		
		//Se llama a la carga de PedidosFacturas
		cargarPedidosFacturas(em);
		
		
	}
	
	/*
	 * Este metodo realiza las asociaciones entre: PedidoPersonalBD, FacturaPersonalBD, FacturaFabricaBD y PedidoFabricaBD
	 * 
	 */
	
	private void cargarTiposDeProducto(EntityManager em) {
		
		
		
		//NOTA: El tipo de Producto con codigo 1 es para los cosmeticos, mientras que el 2 es para las ropas.
		
		TipoDeProductoBD tipoProd1=null;
		try {
			 tipoProd1= em.create(TipoDeProductoBD.class);
		} catch (SQLException e) {
			System.out.println("Error al crear el tipo de Producto!");
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(Constantes.ERROR);
		}
		
		tipoProd1.setCodTipoProd(1);
		tipoProd1.setDescripcion("Cosmetico"); //Se guarda con codigo 1 para el tipo de producto "Cosmetico"
		tipoProd1.save();
		
		
		TipoDeProductoBD tipoProd2= null;
		try {
			 tipoProd2= em.create(TipoDeProductoBD.class);
		} catch (SQLException e) {
			System.out.println("Error al crear el tipo de Producto!");
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(Constantes.ERROR);
		}
		
		tipoProd2.setCodTipoProd(2);
		tipoProd2.setDescripcion("Ropa"); //Se guarda con codigo 2 para  el tipo de producto "Ropa"
		tipoProd2.save();
		
		
		
		//Se asocian productos viejos y productos libres 
	
		prod1.setTipoDeProductoBD(tipoProd1);
		prod1.save();
		
		prod12.setTipoDeProductoBD(tipoProd1);
		prod12.save();
		
		prod13.setTipoDeProductoBD(tipoProd2);
		prod13.save();
		
		//Se asocian los productos del tomo 2 (Cosmeticos)
		
		prod2.setTipoDeProductoBD(tipoProd1);
		prod2.save();
		prod3.setTipoDeProductoBD(tipoProd1);
		prod3.save();
		prod4.setTipoDeProductoBD(tipoProd1);
		prod4.save();
		prod5.setTipoDeProductoBD(tipoProd1);
		prod5.save();
		prod6.setTipoDeProductoBD(tipoProd1);
		prod6.save();
		//Se asocian los productos del tomo 3(ROPA)
	
		prod7.setTipoDeProductoBD(tipoProd2);
		prod7.save();
		prod8.setTipoDeProductoBD(tipoProd2);
		prod8.save();
		prod9.setTipoDeProductoBD(tipoProd2);
		prod9.save();
		prod10.setTipoDeProductoBD(tipoProd2);
		prod10.save();
		prod11.setTipoDeProductoBD(tipoProd2);
		prod11.save();
		
		
		
	}

	/*
	 * Este metood carga un pedido personal viejo  (pagado) con su factura personal y con sus pedidosFabrica y facturas fabrica correspondientes.
	 */
	
	private void cargarPedidoAFabricaViejoBD(EntityManager em){
		
		//Carga de PedidoFabrica Viejo
				PedidoFabricaBD pedviejo=null;
				
				try {
					pedviejo= em.create(PedidoFabricaBD.class);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Error al crear el pedido Viejo!");
					System.exit(1);
				}
				pedviejo.setNumeroPedido(1000);
				pedviejo.setFechaEmision(new Date("20/05/2012"));
				pedviejo.setFechaRecepcion(new Date("01/06/2012"));
				pedviejo.setRecibido(true);
				pedviejo.save();

				//PedidoPersonal viejo
				PedidoPersonalBD ped1=null;
				try {
					ped1= em.create(PedidoPersonalBD.class);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Error al crear el pedido de personal!");
					System.exit(1);
				}
				ped1.setNumeroPedido(50);
				ped1.setFechaEmision(new Date("10/05/2012"));
				ped1.setFechaRecepcion(new Date("01/06/2012"));
				ped1.setPedidoFabricaBD(pedviejo);
				ped1.save();
				
				//Detalle de PedidoPersonalviejo
				DetallePedidoPersonalBD detPedPers=null;
				try {
					detPedPers= em.create(DetallePedidoPersonalBD.class);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Error al crear el Detalle");
					System.exit(1);
				}
				detPedPers.setCantidad(13);
				detPedPers.setProductoBD(prod1);
				detPedPers.setPedidoPersonalBD(ped1);
				detPedPers.save();
				
				
				//Setear la FacturaPersonal para un PedidoPersonalBDViejo
				FacturaPersonalBD factpers=null;
			 
				try {
					factpers= em.create(FacturaPersonalBD.class);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Error al crear la Factura a Fabrica");
					System.exit(1);
				}
				factpers.setFecha(new Date("01/06/2012"));
				factpers.setPedidoPersonalBD(ped1);
				factpers.setNumero(pedviejo.getNumeroPedido());
				factpers.setPagada(true);
				factpers.setTipo(1);
				
				// SE setea a la factura personalBD el usuario Rodrigo
				usuarioBD[] usr=null;
				try {
					usr= em.find(usuarioBD.class,"nombreusr=?","Rodrigo");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Error al leer el usuario Rodrigo");
					System.exit(1);
				}				
				factpers.setUsuario(usr[0]);
				factpers.save();
			
								
				//Setear la factura Fabrica para un pedidoFabrica viejo
				FacturaFabricaBD  factfab=null;
				try {
					factfab= em.create(FacturaFabricaBD.class);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Error al crear la Factura a Fabrica");
					System.exit(1);
				}
				
				factfab.setFecha(new Date("02/06/2012"));
				factfab.setNumero(pedviejo.getNumeroPedido()); //El nro FacturaFabrica es el mismo que el numero de PeidodFabrica.
				factfab.setPedidoFabricaBD(pedviejo);
				factfab.setTipo(1);
				factfab.setPagada(true);
				factfab.save();
				
				//FIN DE CARGA DE FACTURA FABRICA, PEDIDO FABRICA, FACTURA PERSONAL Y PEDIDO PERSONAL VIEJO EN LA BD.
		
	}
	
	
/*
 * Este metodo carga los pedidos que se realizaron  en base a los productos del catalogoVigente.
 */
	private void cargarPedidosFacturas(EntityManager em ){
		
		//INICIO DE CARGA de Facturas y el PedidoViejo en la BD
		
		this.cargarPedidoAFabricaViejoBD(em);
		//FIN DE CARGA DE FACTURAS Y EL PEDVIEJO EN LA BD.
		
		
		//Carga de pedidoFabrica No Facturado nro: 2000.
		PedidoFabricaBD pedNuevo=null;
		
		try {
			 pedNuevo= em.create(PedidoFabricaBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al crear el pedidoFabrica nro: 2000");
			System.exit(1);
		}
		pedNuevo.setNumeroPedido(2000);
		pedNuevo.setFechaEmision(new Date("20/06/2012"));
		pedNuevo.setFechaRecepcion(new Date("01/07/2012"));
		pedNuevo.setRecibido(true);
		pedNuevo.save();
		
		
		
		
		//Carga de pedidoPersonal no facturado nro: 250
		PedidoPersonalBD	pnew1=null;
		try {
			 pnew1= em.create(PedidoPersonalBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al crear el pedidoPersonal nro: 250 ");
			System.exit(1);
		}
		pnew1.setNumeroPedido(250);
		pnew1.setFechaEmision(new Date("03/06/2012"));
		pnew1.setFechaRecepcion(new Date("01/07/2012"));
		pnew1.setPedidoFabricaBD(pedNuevo);
		pnew1.save();
		
		
		
		//Carga de Detalle 1 del pedidoPersonal nro: 250
		DetallePedidoPersonalBD detNuevo1=null;
		try {
			 detNuevo1= em.create(DetallePedidoPersonalBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al crear el detalle 1 pedidoPersonal nro: 250 ");
			System.exit(1);
		}
		detNuevo1.setCantidad(5);
		detNuevo1.setProductoBD(prod2);
		detNuevo1.setPedidoPersonalBD(pnew1);
		detNuevo1.save();
		
		//Carga de Detalle 2 del pedidoPersonal nro: 250
		DetallePedidoPersonalBD detNuevo2=null;
		try {
			 detNuevo2= em.create(DetallePedidoPersonalBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al crear el detalle 2 pedidoPersonal nro: 250 ");
			System.exit(1);
		}
		detNuevo2.setCantidad(22);
		detNuevo2.setProductoBD(prod3);
		detNuevo2.setPedidoPersonalBD(pnew1);
		detNuevo2.save();
		
		
		
		
		//Carga de pedidoPersonal no facturado nro: 350
		PedidoPersonalBD pnew2=null;
 
		try {
			 pnew2= em.create(PedidoPersonalBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al crear el pedidoPersonal nro: 350");
			System.exit(1);
		}
		pnew2.setNumeroPedido(350);
		pnew2.setFechaEmision(new Date("15/06/2012"));
		pnew2.setFechaRecepcion(new Date("01/07/2012"));
		pnew2.setPedidoFabricaBD(pedNuevo);
		pnew2.save();
		
		//Carga de detalle 1 del PedidoPersonal nro: 350
		
		DetallePedidoPersonalBD detNuevo3=null;
		try {
			 detNuevo3= em.create(DetallePedidoPersonalBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al crear el detalle 1 pedidoPersonal nro: 350 ");
			System.exit(1);
		}
		detNuevo3.setCantidad(23);
		detNuevo3.setProductoBD(prod7);
		detNuevo3.setPedidoPersonalBD(pnew2);
		detNuevo3.setColor("Rojo");
		detNuevo3.setTalle("XL");
		detNuevo3.save();
		
		
		//Carga de detalle 2 del PedidoPersonal nro: 350
		
		DetallePedidoPersonalBD detNuevo4=null;
		try {
			 detNuevo4= em.create(DetallePedidoPersonalBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al crear el detalle 2 pedidoPersonal nro: 350 ");
			System.exit(1);
		}
		detNuevo4.setCantidad(10);
		detNuevo4.setProductoBD(prod8);
		detNuevo4.setPedidoPersonalBD(pnew2);
		detNuevo4.setColor("Amarillo");
		detNuevo4.setTalle("S");
		detNuevo4.save();
		
		
		//Inicio de carga de Factura FACTURA FABRICA Y Facturas Personal correspondientes al PedidoFabrica nro: 2000
	
		
		//Setear la FacturaPersonal para un PedidoPersonalBD nro: 250
		FacturaPersonalBD factpers=null;
	 
		try {
			factpers= em.create(FacturaPersonalBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al crear la factura personal nro: 250");
			System.exit(1);
		}
		factpers.setFecha(new Date("01/09/2012"));
		factpers.setPedidoPersonalBD(pnew1); //Se asocia la facturaPersonal con el PedidoPersonal
		factpers.setNumero(pedNuevo.getNumeroPedido()); //Se obtinee el numero de pedido Fabrica al que esta asociado la facturaPersonal
		factpers.setPagada(false);
		factpers.setTipo(1);
		factpers.save();
		
		// SE setea a la factura personalBD el usuario Vader
		usuarioBD[] usr=null;
		try {
			usr= em.find(usuarioBD.class,"nombreusr=?","Vader");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al leer el usuario Vader");
			System.exit(1);
		}				
		factpers.setUsuario(usr[0]);
		factpers.save();
		
		
		//Setear la factura Personal del pedido nro: 350
		FacturaPersonalBD factpers2=null;
		 
		try {
			factpers2= em.create(FacturaPersonalBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al crear la factura personal nro: 350");
			System.exit(1);
		}
		factpers2.setFecha(new Date("01/09/2012"));
		factpers2.setPedidoPersonalBD(pnew2); //Se asocia la facturaPersonal con el PedidoPersonal
		factpers2.setNumero(pedNuevo.getNumeroPedido()); //Se obtinee el numero de pedido Fabrica al que esta asociado la facturaPersonal
		factpers2.setPagada(false);
		factpers2.setTipo(1);
		
		
		// SE setea a la factura personalBD el usuario Luke
		usuarioBD[] usr2=null;
		try {
			usr2= em.find(usuarioBD.class,"nombreusr=?","Luke");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al leer el usuario Luke");
			System.exit(1);
		}				
		factpers2.setUsuario(usr2[0]);
		factpers2.save();
		
						
		//Setear la factura Fabrica para un pedidoFabrica nro: 2000
		FacturaFabricaBD  factfab=null;
		try {
			factfab= em.create(FacturaFabricaBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al crear la Factura a Fabrica");
			System.exit(1);
		}
		
		factfab.setFecha(new Date("01/09/2012")); 
		factfab.setNumero(pedNuevo.getNumeroPedido());
		factfab.setPedidoFabricaBD(pedNuevo);
		factfab.setTipo(1);
		factfab.setPagada(false);
		factfab.save();
		
		
		
		//FIN  DE CARGA
		
		
		//----------------------------------------------------------------------------------------------------------------------------------------------
		
		
		//Carga de pedidoPersonal no facturado nro: 450. 
		//NOTA IMPORTANTE: El pedido 450 no tiene fecha de Recepcion, por lo que cuando se marque el pedido de Fabrica al que esta asociado,
		// ademas de marcarse dicho pedidoFab  y setearse la fecha, LA FECHA DE RECEPCION DEL PEDIDOPersonal tambien debe setearse.
		
		
		PedidoPersonalBD pnew3=null;
 
		try {
			 pnew3= em.create(PedidoPersonalBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al crear el pedidoPersonal Nuevo 3");
			System.exit(1);
		}
		pnew3.setNumeroPedido(450);
		pnew3.setFechaEmision(new Date("03/09/2012"));
		pnew3.setPedidoFabricaBD(pedNuevo);
		pnew3.save();
		
		//Carga de detalle 1 del PedidoPersonal nro: 450
		
		DetallePedidoPersonalBD detNuevo5=null;
		try {
			 detNuevo5= em.create(DetallePedidoPersonalBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al crear el detalle 1 pedidoPersonal nro: 450 ");
			System.exit(1);
		}
		detNuevo5.setCantidad(23);
		detNuevo5.setProductoBD(prod9);
		detNuevo5.setPedidoPersonalBD(pnew3);
		detNuevo5.setColor("Magenta");
		detNuevo5.setTalle("XS");
		detNuevo5.save();
		
		
		//Carga de detalle 2 del PedidoPersonal nro: 450
		
		DetallePedidoPersonalBD detNuevo6=null;
		try {
			 detNuevo6= em.create(DetallePedidoPersonalBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al crear el detalle 2 pedidoPersonal Nuevo nro: 450 ");
			System.exit(1);
		}
		detNuevo6.setCantidad(10);
		detNuevo6.setProductoBD(prod10);
		detNuevo6.setPedidoPersonalBD(pnew3);
		detNuevo6.setColor("Lila");
		detNuevo6.setTalle("S");
		detNuevo6.save();
		
		
		
		//Carga del pedidoFabrica nro: 3000  y asociacion con el pedidoPersonal 450
		
		PedidoFabricaBD pedNuevo2=null;
		
		try {
			 pedNuevo2= em.create(PedidoFabricaBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al crear el pedidoFabrica nro: 2000");
			System.exit(1);
		}
		pedNuevo2.setNumeroPedido(3000);
		pedNuevo2.setFechaEmision(new Date("03/09/2012"));
		//pedNuevo2.setFechaRecepcion(new Date("01/07/2012"));
		pedNuevo2.setRecibido(false);
		pedNuevo2.save();
		//TODO MODICADO RODRIGO
		//SE asocia el pedido nro: 3000, con el pedidoPersonal nro: 450
		pnew3.setPedidoFabricaBD(pedNuevo2);
		pnew3.save();
		
	}
	
	

}
