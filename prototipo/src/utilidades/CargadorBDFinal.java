package utilidades;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import com.sun.xml.internal.ws.api.pipe.NextAction;

import negocio.FacturaPersonal;
import net.java.ao.EntityManager;

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

public class CargadorBDFinal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		EntityManager em= new EntityManager("jdbc:postgresql://localhost/BackupDatos",Constantes.USUARIO,Constantes.PASS);
		GeneradorBD gd = new GeneradorBD("jdbc:postgresql://localhost/BackupDatos",Constantes.USUARIO,Constantes.PASS);
		gd.generarEsquema();
		CargadorBDFinal cargador= new CargadorBDFinal();
		
		try {
			cargador.cargar(em);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void cargar(EntityManager em) throws SQLException{
	CatalogoBD cat1=null;
		
		try {
			cat1= em.create(CatalogoBD.class);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		cat1.setAnioVigencia(2011);
		cat1.save();
		
		//Catalogo actual
		CatalogoBD cat2=null;
		
		try {
			cat2= em.create(CatalogoBD.class);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		cat2.setAnioVigencia(2012);
		cat2.save();
		
		//cargo los tomos
		TomoBD tom1, tom2, tom3=null;
		//creo los tomos
		tom1 = em.create(TomoBD.class);
		tom2 = em.create(TomoBD.class);
		tom3 = em.create(TomoBD.class);
		//seteo a los tomos el catalogo 2
		tom1.setCatalogoBD(cat2);
		tom2.setCatalogoBD(cat2);
		tom3.setCatalogoBD(cat2);
		//les seteo los atributos a los tomos
		tom1.setCodigoTomo(10);
		tom2.setCodigoTomo(20);
		tom3.setCodigoTomo(30);
		tom1.setDescripcion("Indumentaria");
		tom2.setDescripcion("Cosmeticos");
		tom3.setDescripcion("Articulos para el hogar");
		tom1.save();
		tom2.save();
		tom3.save();
		//fin de carga de tomos
		//creacion de los tipos
		TipoDeProductoBD [] tipos = new TipoDeProductoBD[3];
		tipos[0]= em.create(TipoDeProductoBD.class);
		tipos[1]= em.create(TipoDeProductoBD.class);
		tipos[2]= em.create(TipoDeProductoBD.class);
		
		tipos[0].setCodTipoProd(10);
		tipos[1].setCodTipoProd(20);
		tipos[2].setCodTipoProd(30);
		
		tipos[0].setDescripcion("Indumentaria");
		tipos[1].setDescripcion("Cosmeticos");
		tipos[2].setDescripcion("Articulos para el hogar");
		
		tipos[0].save();
		tipos[1].save();
		tipos[2].save();

		//para generar aleatorios
		Random r = new Random();
		DecimalFormatSymbols decimal = new DecimalFormatSymbols();
		decimal.setDecimalSeparator('.');
		DecimalFormat formato = new DecimalFormat("######.##",decimal);
		
		
		//carga de productos
		
		String[] descripcion= {"Jean Taverniti","Jean Fox","Jean Store", "Chomba Prescott", "Chomba Taverniti",
								"Camisas Polo Ralph", "Camisa de vestir", "Sparks", "chombas tommy Hilfier", "blusas",
								"Camisa entallada", "campera canguro", "campera gabardina", "campera de cuero", "Abrigos trenchs",
								"Campera microfibra", "Campera tapado", "Saco", "Chaleco", "Short Rugby",
								"Calza chupin", "Traje de neoprene", "camisetas de futbol", "Remeras adidas", "Guantes wind",
								
										"shamps perfume", "bolsa de filesina", "fame lady gaga perfume", "perfume antonio banderas", "lady millions",
										"perfume nina", "alcohol de cereal","esmalte", "perfume carolina herrera", "kenzo flowers", 
										"gel de limpieza", "tonico facial", "gel hidratante", "corrector de arrugas", "maquillar",
										"jabon hipoalergenico", "mascara removedora", "Locion anti acne", "jabon secativo", "portacosmeticos",
										"aceite de argan", "demaquillante dual", "organoil crema dia", "organoil crema noche", "organoil crema contorno",
										
								"Holla essen grande", "Holla essen mediana", "Holla essen sarten", "Panquequera essen", "Holla horno essen", 
								"Holla bifera essen", "hervidor essen", "Holla essen chica", "Holla horno essen con teflon", "Rallador de metal",
								"cascanueces de metal", "picador de hielo", "colador de mano tamaño 1", "colador de fideos plastico", "colador de mano tamaño 2",
								"colador de fideos metal", "vasos de cocina", "copas semil cristal", "vasos para whisky", "fuente de vidrio tamaño 1 metal",
								"fuente de vidrio tamaño 2 metal", "fuente de vidrio tamaño 3 metal", "fuente de vidrio tamaño 1 plastico", "fuente de vidrio tamaño 2 plastico", "fuente de vidrio tamaño 3 plastico",
								"fuente para pescado", "Vaso para mesa", "Ensaladera de plastico", "Ensaladera de vidrio", "Juego de cubiertos acero inoxidable",
								"juego de cubiertos plata", "juego de cubiertos de bronce", "juego de cubiertos tramontina", "pinza para ensalada", "pinza de hielo",
								"balde para hielo", "corta papas", "rallador de queso", "cortador de queso", "pisapapas", 
								"mate de metal", "mate de loza","bombilla inoxidable", "pava de acero inoxidable", "termo de acero inoxidable", 
								"termo de plastico", "temporizador", "juego de tazas", "platos para bebe", "vaso para bebe"};
		//Descripción 1 a 25 Indumentaria
		//Perfumeria 26-50
		//Accesorios 51-100
		String [] descripcionNoAsoc= {"Lapiz labial", "Brillo labial", "Arqueador de pestañas", "Mascara para pestañas", "Base maquillaje", 
										"Base maquillaje con filtro solar", "Calcio para uñas", "Lima de carton para uñas", "Rubor", "Lapiz delineador de ojos"};
		
		ProductoBD [] productos = new ProductoBD[100];
		for (int i = 0; i < productos.length; i++) {
			productos[i]=em.create(ProductoBD.class);
			productos[i].setCodigo(100*(i+1));
			productos[i].setCantidadEnStock(r.nextInt(100));
			productos[i].setPrecio(Double.valueOf(formato.format(r.nextDouble()*r.nextInt(100)+1)));//numero aleatorio con techo 1000
			productos[i].setDescripcion(descripcion[i]);//una descripcion aleatoria
					
		}
		//desde el 0 hasta la cuarta parte
		for (int i = 0; i < productos.length / 4; i++) {
			productos[i].setTipoDeProductoBD(tipos[0]);
			productos[i].setTomoBD(tom1);
			productos[i].save();
		}
		//desde la cuarta parte hasta los 3/4 
		for (int i = productos.length/4 ; i < productos.length*3/4; i++) {
			
			productos[i].setTipoDeProductoBD(tipos[1]);
			productos[i].setTomoBD(tom2);
			productos[i].save();
		}
		
		//desde los 3/4 hasta el final
		for (int i =  productos.length*3/4; i < productos.length; i++) {
			productos[i].setTipoDeProductoBD(tipos[2]);
			productos[i].setTomoBD(tom3);
			productos[i].save();
			
		}
		
		ProductoBD [] productosNoAsociados = new ProductoBD[10];
		for (int i = 0; i < productosNoAsociados.length; i++) {
			productosNoAsociados[i]=em.create(ProductoBD.class);
			productosNoAsociados[i].setCodigo(10000*(i+1));
			productosNoAsociados[i].setCantidadEnStock(r.nextInt(100)+1);
			productosNoAsociados[i].setPrecio(r.nextDouble()*r.nextInt(100)+1);//numero aleatorio con techo 1000
			productosNoAsociados[i].setDescripcion(descripcionNoAsoc[i]);//una descripcion aleatoria
			productosNoAsociados[i].save();
					
		}
		
		//carga de los pedidos
		
		PedidoPersonalBD pedidos[] = new PedidoPersonalBD[100];
		for (int i = 0; i < pedidos.length; i++) {
			pedidos[i] = em.create(PedidoPersonalBD.class);
			Date dia = new Date();
			Calendar dia1 = GregorianCalendar.getInstance();
			dia1.set(2012, ((r.nextInt(12))), r.nextInt(30)+1);
		
			
			pedidos[i].setFechaEmision(dia1.getTime());
			dia1.set(Calendar.DATE,dia1.get(Calendar.DATE)+1);

			pedidos[i].setFechaRecepcion(dia1.getTime());
			pedidos[i].setPedidoFabricaBD(null);
			pedidos[i].setNumeroPedido(i);
			pedidos[i].save();
		}
		//carga de los detalles
		DetallePedidoPersonalBD detalles[] = new DetallePedidoPersonalBD[500];
		
		for (int i = 0; i < detalles.length; i++) {
			
			
			
			detalles[i]= em.create(DetallePedidoPersonalBD.class);

			
			int elegirTipo= (r.nextInt(4)+1);
			switch (elegirTipo) {
			case 1: //Indumentaria
				int elegirColor=(r.nextInt(6)+1);
				switch (elegirColor) {
				case 1://caso de x
					detalles[i].setColor("Negro");
					break;
				case 2:
					detalles[i].setColor("Rojo");
					break;
				case 3:
					detalles[i].setColor("Azul");
					break;
				case 4:
					detalles[i].setColor("Amarillo");
					break;
				case 5:
					detalles[i].setColor("Naranja");
					break;

				
				}// fin de la eleccion de color
				int elegirTalle=(r.nextInt(5)+1);
				switch (elegirTalle) {
				case 1:
					detalles[i].setTalle("M");
					break;
				case 2:
					detalles[i].setTalle("L");
					break;
				case 3:
					detalles[i].setTalle("XL");
					break;
				case 4:
					detalles[i].setTalle("S");
					break;

				
				}// fin de la eleccion del talle
				break;
			case 2: //perfumerìa
				detalles[i].setColor("N/A");
				detalles[i].setTalle("N/A");
				break;
			case 3://accesorios
				detalles[i].setColor("N/A");
				detalles[i].setTalle("N/A");
				break;
			
			
			}
			
			
			//obtengo un producto para asociar el detalle
			
			int elegirProducto= r.nextInt(100);
			detalles[i].setProductoBD(productos[elegirProducto]);
			//obtengo un pedido para asociar al detalle
			int elegirPedido=r.nextInt(100);
			int elegirCantidad= r.nextInt(101);
			detalles[i].setCantidad(elegirCantidad);
			
			detalles[i].setPedidoPersonalBD(pedidos[elegirPedido]);
			detalles[i].setPrecio(productos[elegirProducto].getPrecio()*detalles[i].getCantidad());
			
			detalles[i].save();
			
		}
		//cargo tipos de usuario
		TipoDeUsrBD[] tipoUsr=new TipoDeUsrBD[4];
		for (int j = 0; j < 4; j++) {
			 tipoUsr[j]= em.create(TipoDeUsrBD.class);
			tipoUsr[j].setnroTipoUsr(j+1);
			tipoUsr[j].save();
		}
	
		
		//cargo los usuarios
		String [] nombres = {"Rodrigo",	"Admin",	"Vader",	"Luke",	"Marta",	"Claudio","Marcelo","Gloria","Gabriel","Anahi","Jonhatan","Cristian",
				"Davies","Carolina"};
		String [] apellido = {"Huincalef","Admin","Darth","SkyWalker",	"Lopez",	"Riquelme",	"Gutierrez","Bianchi",	"postgres",	"Griffhis",
		                                "WIlliams",	"Ballejos",	"Sepulveda","Kovacic"};
		int [] dni = {35887888,	33345146,32000111,32222111,	11111111,22222222,33333333,	44444444,55555555,66666666,77777777,88888888,	99999999,10000000};
		String[] userName= {"Rodrigo","Admin","Vader","Luke","Marta","Claudio","Marcelo","Gloria","Gabriel","Anahi",	"Jonhatan","Cristian","Davies","Carolina"};
		
		String contrasenia= "1234";
		Calendar dia1 = GregorianCalendar.getInstance();
		
		
		for (int j = 0; j < nombres.length; j++) {
			usuarioBD usuario= em.create(usuarioBD.class);
			usuario.setApellido(apellido[j]);
			usuario.setContrasenia(contrasenia);
			usuario.setDni(dni[j]);
			usuario.setNombre(nombres[j]);
			usuario.setNombreUsr(userName[j]);
			dia1.set(r.nextInt(100)+1900, ((r.nextInt(11)+1)), r.nextInt(30)+1);
			usuario.setFecha_nacimiento(dia1.getTime());

			switch (j) {
			case 0:usuario.setTipoDeUsrBD(tipoUsr[2]);
			break;
			case 1: usuario.setTipoDeUsrBD(tipoUsr[3]);
			break;
			case 2: usuario.setTipoDeUsrBD(tipoUsr[1]);
			break;
			default: usuario.setTipoDeUsrBD(tipoUsr[0]);
			break;

			
			}
			
			usuario.save();
			
		}

		
		//carga de las facturas
		
				
		
				usuarioBD [] usuario=em.find(usuarioBD.class);
				usuarioBD [] vendedores=new usuarioBD[100];
				int cantidadVendedores=0;
				for (int j = 0; j < usuario.length; j++) {
					if (usuario[j].getTipoDeUsrBD().getnroTipoUsr()==4){
						
						vendedores[cantidadVendedores]= usuario[j];
						cantidadVendedores++;
					}
				}
				//TODO tienen que existir usuarios vendedores
				FacturaPersonalBD facturasP[] = new FacturaPersonalBD[100];
				//fechasFacturasR es el arreglo que guarda dependiendo el mes, las posiciones de los pedidos en los respectivos meses
				int  fechasFacturasR[][]= new int [12][100];
				int [] contador = new int[12];
				boolean bandera=true;
				int [] facturasUsuario= new int [11];
				for (int j = 0; j < facturasP.length; j++) {
					facturasP[j]=em.create(FacturaPersonalBD.class);
					facturasP[j].setNumero(j);
					facturasP[j].setPedidoPersonalBD(pedidos[j]);
					
					facturasP[j].setTipo(1);
					
					Date fechaFacturaP=pedidos[j].getFechaRecepcion();
					fechaFacturaP.setDate(fechaFacturaP.getDate());
					facturasP[j].setFecha(fechaFacturaP);
					switch (facturasP[j].getFecha().getMonth()) {
					case 0: //mes enero
						
						fechasFacturasR[0][contador[0]++]=j;
						break;
					case 1: 
						fechasFacturasR[1][contador[1]++]=j;
						break;
					case 2: 
						fechasFacturasR[2][contador[2]++]=j;
						break;
					case 3: 
						fechasFacturasR[3][contador[3]++]=j;
						break;
					case 4: 
						fechasFacturasR[4][contador[4]++]=j;
						break;
					case 5: 
						fechasFacturasR[5][contador[5]++]=j;
						break;
					case 6: 
						fechasFacturasR[6][contador[6]++]=j;
						break;
					case 7:
						fechasFacturasR[7][contador[7]++]=j;
						break;
					case 8:
						fechasFacturasR[8][contador[8]++]=j;
						break;
					case 9:
						fechasFacturasR[9][contador[9]++]=j;
						break;
						//En el mes 10 y 11 pagan
					case 10: //mes noviembre
						fechasFacturasR[10][contador[10]++]=j;
						break;
					case 11: //mes diciembre
						fechasFacturasR[11][contador[11]++]=j;
						break;

					
					}
					//todas las facturas se encuentra pagas.
					if ((facturasP[j].getFecha().getMonth()>9) && (facturasP[j].getFecha().getMonth()<=11)){
						facturasP[j].setPagada(false);
						//todas las facturas personales desde octubre a diciembre
					}else{
						facturasP[j].setPagada(true);
					}
					
					facturasP[j].save();
										
				}
				//debo crear los pedidos a fabrica
				
				PedidoFabricaBD[] pedidosFabrica = new PedidoFabricaBD[10];
				int puntero=0;
				int empezar=0;
				for (int j = 0; j < pedidosFabrica.length; j++) {
					pedidosFabrica[j]= em.create(PedidoFabricaBD.class);
					Date hoy = new Date();
					//la fecha debe calcularse en base a las facturas personal
					//se recorre la coleccion donde se guardan los meses y dependiendo de si existen se crean pedidos
				
					for (int i = empezar; i < contador.length; i++) {
						if (contador[i] >0){
							//quiere decir que hay pedidos en el mes que apunta i
							puntero=i;
							empezar=i+1;
							break;//salgo del for con puntero
							
						}
					}
					//como sali del for tengo que buscar la mayor fecha
					boolean esPrimero=true;
					for (int i = 0; i < contador[puntero]; i++) {
						//contador posee la cantidad de pedidos en el mes determinado por puntero
						
						int posicionFactura=fechasFacturasR[puntero][0];; 
						if (esPrimero){
							esPrimero=false;
							hoy =facturasP[posicionFactura].getFecha();
						}else{
							if (hoy.compareTo(facturasP[posicionFactura].getFecha())>0){
								hoy= facturasP[posicionFactura].getFecha();
								
								
							}
						}
						
					}
					
					System.out.println("fecha emision fabrica:"+hoy);
					pedidosFabrica[j].setFechaEmision(hoy);
					//se setea un dia despues la recepcion del pedido a fabrica
					Date hoy2= new Date();
					hoy2=(Date) hoy.clone();
					hoy2.setDate(hoy2.getDate()+1);
					System.out.println("fecha recepción fabrica:"+hoy2);
					pedidosFabrica[j].setFechaRecepcion(hoy2);
					pedidosFabrica[j].setNumeroPedido(j);
					pedidosFabrica[j].setRecibido(false);
					pedidosFabrica[j].save();
					
				
				}
				for (int i = 0; i < pedidosFabrica.length; i++) {
					System.out.println("PEDIDO A FABRICA Nº:"+pedidosFabrica[i].getID());
					//por cada factura recorro el contador que tiene la cantidad de pedidos
					for (int k = 0; k < contador[i]; k++) {
						int posicion=fechasFacturasR[i][k];//la posicion adecuada del pedido
						//se setea el pedido a fabrica
						pedidos[posicion].setPedidoFabricaBD(pedidosFabrica[i]);
						//facturasP[posicion].getPedidoPersonalBD().setPedidoFabricaBD(pedidosFabrica[i]);
						//se guarda en bd
						//facturasP[posicion].getPedidoPersonalBD().save();
						pedidos[posicion].save();
						System.out.println("El pedido personal:"+pedidos[posicion].getID()+" pertenece a "+pedidosFabrica[i].getID());
					}
					if (pedidosFabrica[i].getColPedidoPersonalBD().length==0){
						//si tiene pedidos no lo borro
						em.delete(pedidosFabrica[i]);
					}
				}
				System.out.println("Se cargaron todos los datos");
				//seteo el pedido al pedido a fabrica
				
		
				/*
				 * No se crean las facturas  a fabrica porque existe un caso de uso que lo realiza.
				 * 
				 * */
			
		
	}

}
