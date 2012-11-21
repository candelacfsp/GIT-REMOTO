package utilidades;

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
		tom2.setDescripcion("Perfumeria");
		tom3.setDescripcion("Accesorios");
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
		tipos[1].setDescripcion("Perfumeria");
		tipos[2].setDescripcion("Accesorios");
		
		tipos[0].save();
		tipos[1].save();
		tipos[2].save();

		//para generar aleatorios
		Random r = new Random();
		DecimalFormatSymbols decimal = new DecimalFormatSymbols();
		decimal.setDecimalSeparator('.');
		DecimalFormat formato = new DecimalFormat("######.##",decimal);
		
		
		//carga de productos
		
		
		ProductoBD [] productos = new ProductoBD[100];
		for (int i = 0; i < productos.length; i++) {
			productos[i]=em.create(ProductoBD.class);
			productos[i].setCodigo(100*(i+1));
			productos[i].setCantidadEnStock(r.nextInt(100));
			productos[i].setPrecio(Double.valueOf(formato.format(r.nextDouble()*r.nextInt(100)+1)));//numero aleatorio con techo 1000
			productos[i].setDescripcion(String.valueOf(i*333));//una descripcion aleatoria
					
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
		
		ProductoBD [] productosNoAsociados = new ProductoBD[50];
		for (int i = 0; i < productosNoAsociados.length; i++) {
			productosNoAsociados[i]=em.create(ProductoBD.class);
			productosNoAsociados[i].setCodigo(10000*(i+1));
			productosNoAsociados[i].setCantidadEnStock(r.nextInt(100)+1);
			productosNoAsociados[i].setPrecio(r.nextDouble()*r.nextInt(100)+1);//numero aleatorio con techo 1000
			productosNoAsociados[i].setDescripcion(String.valueOf(i*666));//una descripcion aleatoria
			productosNoAsociados[i].save();
					
		}
		
		//carga de los pedidos
		
		PedidoPersonalBD pedidos[] = new PedidoPersonalBD[100];
		for (int i = 0; i < pedidos.length; i++) {
			pedidos[i] = em.create(PedidoPersonalBD.class);
			Date dia = new Date();
			Calendar dia1 = GregorianCalendar.getInstance();
			dia1.set(2012, ((r.nextInt(11)+1)), r.nextInt(30)+1);
		
			
			pedidos[i].setFechaEmision(dia1.getTime());
			dia1.set(Calendar.DATE,dia1.get(Calendar.DATE)+1);

			pedidos[i].setFechaRecepcion(dia1.getTime());
			pedidos[i].setPedidoFabricaBD(null);
			pedidos[i].setNumeroPedido(i+1);
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
		
		for (int j = 0; j < nombres.length; j++) {
			usuarioBD usuario= em.create(usuarioBD.class);
			usuario.setApellido(apellido[j]);
			usuario.setContrasenia(contrasenia);
			usuario.setDni(dni[j]);
			usuario.setNombre(nombres[j]);
			usuario.setNombreUsr(userName[j]);
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
				boolean bandera=true;
				for (int j = 0; j < facturasP.length; j++) {
					facturasP[j]=em.create(FacturaPersonalBD.class);
					facturasP[j].setNumero(j+1);
					facturasP[j].setPedidoPersonalBD(pedidos[j]);
					if (bandera){
						facturasP[j].setPagada(true);
						bandera=false;
					}else{
						facturasP[j].setPagada(false);
						bandera=true;
					}
					facturasP[j].setTipo(1);
					facturasP[j].setFecha(pedidos[j].getFechaRecepcion());
					
					int elegirUsuario= (r.nextInt(cantidadVendedores+1)+1);
					facturasP[j].setUsuario(vendedores[elegirUsuario]);
					facturasP[j].save();
										
				}
				//debo crear los pedidos a fabrica
				
				PedidoFabricaBD[] pedidosFabrica = new PedidoFabricaBD[10];
				for (int j = 0; j < pedidosFabrica.length; j++) {
					pedidosFabrica[j]= em.create(PedidoFabricaBD.class);
					Date hoy = new Date();
					pedidosFabrica[j].setFechaEmision(hoy);
					hoy.setMonth(hoy.getMonth()+1);
					pedidosFabrica[j].setFechaRecepcion(hoy);
					pedidosFabrica[j].setNumeroPedido(j+1);
					pedidosFabrica[j].setRecibido(false);
					pedidosFabrica[j].save();
					
				
				}
				int j = 0;
				for (int i = 0; i < pedidosFabrica.length; i++) {
					
					while (true){
						pedidos[j].setPedidoFabricaBD(pedidosFabrica[i]);
						pedidos[j].save();
						if (j % 10 == 0){
							//si es multiplo de diez corto
							break;
							//cuando corto entra al bucle superior
						}
						j++;
					}
					
				}
				
				//factutras a fabrica
				FacturaFabricaBD []facturaF = new FacturaFabricaBD[10];
				for (int j1 = 0; j1 < facturaF.length; j1++) {
					facturaF[j1] = em.create(FacturaFabricaBD.class);
					facturaF[j1].setNumero(j1+1);
					facturaF[j1].setFecha(pedidosFabrica[j1].getFechaRecepcion());
					facturaF[j1].setPagada(false);
					facturaF[j1].setPedidoFabricaBD(pedidosFabrica[j1]);
					facturaF[j1].setTipo(1);
					facturaF[j1].save();
				}
				
				for (int i = 1; i < pedidosFabrica.length; i++) {
					facturaF[i].setPedidoFabricaBD(pedidosFabrica[i]);
					facturaF[i].save();
				}
			
		
	}

}
