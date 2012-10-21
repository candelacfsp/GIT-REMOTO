package negocio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import excepciones.ProductoNoAsocTomoException;
import excepciones.ProductoNoEncontradoException;
import excepciones.TomoNoEncontradoException;
import excepciones.TomoNoExisteExcepcion;

import persistencia.CatalogoBD;
import persistencia.ProductoBD;
import persistencia.TomoBD;

import utilidades.Constantes;

import net.java.ao.EntityManager;
/**
 * Esta clase contiene la informacion relacionada con el cat�logo; Realiza las altas y bajas de los tomos
 * asociados al catalogo.
 * @author Huincalef Rodrigo-Mansilla Damian
 *
 */
public class Catalogo {
	private int anioVigencia;
	//private Calendar fecha_inicio=null;
	//private Calendar fecha_fin=null;
	private ArrayList<Tomo> tomos=null;
	private EntityManager em=null;
	/**
	 * Constructor de cat�logo que inicializa el catalogo con su fecha de inicio de vigencia, su fecha de fin
	 * de vigencia y su manejador de entidades.
	 * @param em: manejador de entidades, empleado para realizar lecturas/escrituras a la base de datos.
	 * @param fecha_inicio: fecha de inicio de vigencia del catalogo.
	 * @param fecha_fin: fecha hasta la que estara en vigencia el cat�logo
	 */
	public Catalogo(EntityManager em,Date fecha_inicio){ //TODO MODIFICADO RODRIGO
		this.em=em;
		this.tomos=new ArrayList<Tomo>();
		//El anio de Vigencia, es el anio de la fecha_inicio(la fecha menor)
		//Se crean fecha_inicio y fecha_fin con la fecha actual y se les setea el valor de las fechas
		//que se ingresaron como parametro.
		Calendar fechaVigencia= Calendar.getInstance();
		fechaVigencia.setTime(fecha_inicio);
		
		//this.fecha_fin=Calendar.getInstance();
		//this.fecha_fin.setTime(fecha_fin);
		this.anioVigencia= fechaVigencia.get(Calendar.YEAR);
		
	}
	
	
	/**
	 * setTomos
	 * Setea los tomos que estaran asignados al tomo.
	 * @param tomos: coleccion de tomos que se asociaran al catalogo.
	 */
	public void setTomos(ArrayList<Tomo> tomos) {
		this.tomos = tomos;
	}

	/**
	 * getTomos
	 * Obtiene una coleccion de los tomos que estan asignados al catalogo.
	 * @return: una coleccion de los tomos que estan asignados al catalogo.
	 */
	public ArrayList<Tomo> getTomos() {
		return tomos;
	}
	
	
	public int getAnioVigencia() {
		return anioVigencia;
	}
	/**
	 * Este metodo realiza la asignacion de un tomo a un catalogo en memoria
	 * @param codigo: es el codigo de tomo que se busca en la coleccion
	 */
	private void asignarTomoACatalogo(int codigo,String descripcion){
		
		//Se crea el tomo en memoria
		Tomo tom1=new Tomo(this.em);
		tom1.setCodigoTomo(codigo);
		tom1.setDescripcion(descripcion);
		
		//Se lo a�ade a la coleccion de tomos del catalogo.
		tomos.add(tom1);
	}

	/**
	 * altaTomo
	 * Da de alta un tomo al catalogo. 
	 * @param codigo: codigo del tomo
	 * @param descripcion: descripcion del tomo
	 * @return: un valor entero que indica el resultado de la operacion.
	 */
	public void altaTomo(int codigo, String descripcion,boolean esCatalogoNuevo) throws SQLException{
		//Se da de alta el tomo en memoria y en BD.
		
		//1.Se crea y asigna el tomo al Catalogo en memoria
		this.asignarTomoACatalogo(codigo,descripcion);
		
		//2.Se crea el tomo en la BD
		TomoBD tomoNuevo=null;
		//try {
			tomoNuevo = em.create(TomoBD.class);
		//} catch (SQLException e) {
			// TODO Auto-generated catch block
		/*	e.printStackTrace();
			System.out.println("Error al dar de alta el tomo en la BD");
			return Constantes.EXCEPCION_SQL;
		}*/
		tomoNuevo.setCodigoTomo(codigo);
		tomoNuevo.setDescripcion(descripcion);
		tomoNuevo.save();
		
		//3.Se asigna el tomo al catalogo en BD. Para ello se detecta si es un catalogoNuevo o Viejo.
		//Si es nuevo se crea un nuevo catalogo en la BD. Sino se busca el ultimo catalogo en la BD.
		CatalogoBD[] cat1= new CatalogoBD[1];
		if(esCatalogoNuevo==true){
			
			cat1[0]=em.create(CatalogoBD.class);																	
			cat1[0].setAnioVigencia(anioVigencia);
			cat1[0].save();
			
		}else{
			//Defino una var temporal catalogoBD para recuperar el ID del catalogoVigente
			CatalogoBD catTemp[]= em.find(CatalogoBD.class);
			int COD_CAT_VIGENTE= catTemp[catTemp.length-1].getID();
			
			cat1=em.find(CatalogoBD.class,"id=?", COD_CAT_VIGENTE);
			
		}
		tomoNuevo.setCatalogoBD(cat1[0]);
		tomoNuevo.save();
		
	}
	/**
	 * bajaTomo
	 * Da de baja un tomo del catalogo.
	 * @param codigo: codigo del tomo que se dar� de baja
	 * @return: un valor entero indicando el resultado de la operacion.
	 */
	public void bajaTomo(int codigo) throws TomoNoExisteExcepcion, SQLException{ 
		
		this.eliminarProductosTomo(codigo);
		
		//2. Luego  en Baja de Tomo hay que buscar  el tomo en la BD y setearle null a su catalogo.
		
		TomoBD [] tom1=null;
		//Buscar el tomo
		//try {
			 tom1= em.find(TomoBD.class,"codigoTomo =?",codigo);
		/*} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al buscar tomoBD ");
			e.printStackTrace();
			System.exit(Constantes.EXCEPCION_SQL);
		}*/
		//Eliminar los productos asociados al tomo
			ProductoBD [] productostom= tom1[0].getProductos();
			for (int i = 0; i < productostom.length; i++) {
				//try {
					productostom[i].setTomoBD(null);
					productostom[i].save();
					//em.delete(productostom[i]);
				/*} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Error al eliminar los productos del tomo");
					System.exit(Constantes.EXCEPCION_SQL);
				}*/
			}
				
			tom1[0].setCatalogoBD(null); 
			tom1[0].save();
			
			//Borro el tomo
			//try {
				em.delete(tom1[0]);
			/*} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error al eliminar Tomo de la BD!");
				System.exit(Constantes.EXCEPCION_SQL);
			}*/
			
		//return Constantes.TOMO_DADO_BAJA_OK;
		
		
	}
	
	/**
	 * Este metodo realiza la desasignacion de los productos pertenecientes a un Tomo en memoria. Es
	 * llamado por la baja de tomo que se encarga de realizar la baja del tomo en la Base de datos.
	 * @param codigoTomo
	 * @throws TomoNoExisteExcepcion
	 */
	private void eliminarProductosTomo(int codigoTomo) throws TomoNoExisteExcepcion{
	
		int posTomoBorrar=0;
		while(posTomoBorrar<tomos.size() && (tomos.get(posTomoBorrar).getCodigoTomo()!=codigoTomo)){
			posTomoBorrar++;
		}
		if(posTomoBorrar==tomos.size()){
			throw new TomoNoExisteExcepcion();
		}else{
			tomos.remove(tomos.get(posTomoBorrar));//Borro el tomo en memoria
		}
	}
	
	
	/**
	 *Este metodo se encarga de verificar si existe o no un numero de tomo, en la
	 *coleccion de Tomos del Catalogo. Este metodo se emplea principalmente cuando se esta dando el alta a un Tomo.
	 */	
	public boolean tomoExiste(int nroTomo){
		boolean resultado=false; //Se asume que el tomo no existe en el catalogoNuevo
		
		int i=0;
		while (i<tomos.size() && tomos.get(i).getCodigoTomo()!=nroTomo){
			i++;
		}
		//Si encontre el tomo altero la var. resultado, sino envio el resultado como esta
		if(i<tomos.size()){
			
			resultado=true;
		}
		return resultado;
		
	}
	
	/**
	 * Este metodo determina si un producto esta asociado al tomo de un determinado catalogo. Este catalogo puede ser: el
	 * CatalogoVigente o el catalogoNuevo, dependiendo desde donde se lo invoque.
	 * @return resultado: true si el producto esta asociado al catalogoVigente o false, si no esta asociado
	 * al catalogoVigente.
	 */
	public boolean estaProdAsocTomo(int codProducto){
		boolean resultado=false;
		
		int k;  		
		for(k=0; k<tomos.size() ; k++){ // Se comienza a recorrer los tomos del catalogoVigente de candela, y verificando que el producto no este asociado a algun tomo
			ArrayList<Producto> productos= tomos.get(k).getProductos();

			for(int j=0; j<productos.size(); j++){//Recorro los productos correspondientes a cada tomo.
					
				if(productos.get(j).getCodigo()==codProducto){
					resultado=true;
					break;
				}
				
		
			}
		}
		return resultado;
	}
	
	/**
	 * Este metodo desasigna un producto de un Tomo en memoria en base a su codigo. Luego se encarga de llamar al 
	 * metodo de tomo "desasignarProdTomo()" que realiza la desasignacion del tomo  en la Base de Datos.
	 * @throws TomoNoEncontradoException : Si el producto no se encuentra en la coleccion de productos de ningun tomo
	 * vigente, se lanza esta excepcion.
	 */
	public void desasignarProdTomo(int codigoProd) throws SQLException, ProductoNoAsocTomoException{
		
		//ArrayList <Tomo> tomos= candela.getCatalogoVigente().getTomos();
		int postomo;
		boolean estaTomoDesasignado=false;
		
		for(postomo=0; postomo<tomos.size(); postomo++){ //1.Recorro los Tomos y por cada tomo se obtienen los productos y se recorren, hasta encontrar el ProductoIngresado.
															//2.Obtener los   productos, y verificar si esta asociado con un tomo
			ArrayList<Producto> prods= tomos.get(postomo).getProductos(); 
			int posproducto;
			for ( posproducto=0; posproducto<prods.size(); posproducto++){
				if(prods.get(posproducto).getCodigo()==codigoProd){ //Si el producto ESTA ASOCIADO CON ESE TOMO, ya se cuenta con el Producto y el Tomo
					
					//Obtengo el numero del tomo que tiene el producto Asociado, que me servira para darle de baja en la BD con el metodo: desasignarProdTomo()
					int codigoTomo= tomos.get(postomo).getCodigoTomo();
					
					// Desasignar el producto del tomo del catalogoVigente. Tengo que guardar la referencia al objeto que deseo borrar,
					//recorrer la el ArrayList y guardar la referencia en una varaible temporal, que se empleara para borarr el objeot
					//Nota: No sirve crear una instancia de otro objeto, tiene que ser la referencia a la misma posicion de memoria para que funcione
					//con ArrayList.
					 
					//Elimino el tomo de memoriaRAM
					tomos.get(postomo).getProductos().remove(prods.get(posproducto));						
					
					//Desasignar el producto de la Base de Datos
					
					tomos.get(postomo).desasignarProdTomo(codigoProd,codigoTomo);
					estaTomoDesasignado=true;
					break;
				}
	
			}//Fin de for de itearcion de Productos.
		
		} // Fin del for de iteracion de Tomos.
		if(estaTomoDesasignado==false){ //Se lanza excepcion de ProductoNoAsocTomoException
			throw new ProductoNoAsocTomoException();
		}
		
	}
	/**
	 * Este metodo desasigna un producto de un Tomo en memoria en base a su nombre de producto. Luego se encarga de llamar al 
	 * metodo de tomo "desasignarProdTomo()" que realiza la desasignacion del tomo  en la Base de Datos.
	 * @throws TomoNoEncontradoException : Si el producto no se encuentra en la coleccion de productos de ningun tomo
	 * vigente, se lanza esta excepcion.
	 */
	
	public void desasignarProdTomo(String nombre) throws SQLException, ProductoNoAsocTomoException{
		
		//ArrayList <Tomo> tomos= candela.getCatalogoVigente().getTomos();
		int postomo;
		boolean estaTomoDesasignado=false;
		
		for(postomo=0; postomo<tomos.size(); postomo++){ //1.Recorro los Tomos y por cada tomo se obtienen los productos y se recorren, hasta encontrar el ProductoIngresado.
															//2.Obtener los   productos, y verificar si esta asociado con un tomo
			ArrayList<Producto> prods= tomos.get(postomo).getProductos(); 
			int posproducto;
			for ( posproducto=0; posproducto<prods.size(); posproducto++){
				if(prods.get(posproducto).getDescripcion().equals(nombre)){ //Si el producto ESTA ASOCIADO CON ESE TOMO, ya se cuenta con el Producto y el Tomo
					
					//Obtengo el numero del tomo que tiene el producto Asociado, que me servira para darle de baja en la BD con el metodo: desasignarProdTomo()
					int codigoTomo= tomos.get(postomo).getCodigoTomo();
					
					// Desasignar el producto del tomo del catalogoVigente. Tengo que guardar la referencia al objeto que deseo borrar,
					//recorrer la el ArrayList y guardar la referencia en una varaible temporal, que se empleara para borarr el objeot
					//Nota: No sirve crear una instancia de otro objeto, tiene que ser la referencia a la misma posicion de memoria para que funcione
					//con ArrayList.
					 
					//Elimino el tomo de memoriaRAM
					tomos.get(postomo).getProductos().remove(prods.get(posproducto));						
					
					//Desasignar el producto de la Base de Datos
					int codigoProd=prods.get(posproducto).getCodigo();
					
					tomos.get(postomo).desasignarProdTomo(codigoProd,codigoTomo);
					estaTomoDesasignado=true;
					break;
				}
	
			}//Fin de for de itearcion de Productos.
			if(estaTomoDesasignado==false){ //Se lanza excepcion de ProductoNoAsocTomoException
				throw new ProductoNoAsocTomoException();
			}
		} // Fin del for de iteracion de Tomos.
		
		
	}
	
	
	/**
	 * Este metodo asocia un producto con un Tomo, en memoria y luego llama al asignarProdTomo() de Tomo que realiza la asignacion
	 * en la Base de Datos.
	 * @param codigoProd: codigo del producto a ser asignado.
	 * @param codigoTomo: codigo del tomo a al que sera asignado un producto.
	 * @param colProductos: coleccion de TODOS los productos en Candela, donde se buscara el producto por medio de su codigo.
	 */
	public void AsignarProdTomo(int codigoProd,int codigoTomo,ArrayList<Producto> colProductosCand,boolean isCatNuevo, String descripcion)throws TomoNoEncontradoException,SQLException{
	
		int postomo=0;
		//Se declara el i aca para que pueda ser usado luego para acceder directamente al tomo del CatVigente y a�adirle el ProductoNuevo
		//Busco el tomo y verifico  que el tomo exista
		
		//ArrayList<Tomo> tomos= candela.getCatalogoVigente().getTomos();
		
	
		while (postomo<tomos.size() && (tomos.get(postomo).getCodigoTomo()!=codigoTomo)){
			postomo++;
		}
		if(postomo<tomos.size()){ //Si encontre el Tomo, busco la posicion del producto en la coleccion de candela
			
			int posprod=0;
			//ArrayList<Producto> productosCand= candela.getProductos();
			while (posprod<colProductosCand.size() && (colProductosCand.get(posprod).getCodigo()!=codigoProd)){
				posprod++;
			}
			
			//Asigno el producto al tomo en memoria, y asocio el producto al tomo en la BD
			tomos.get(postomo).getProductos().add(colProductosCand.get(posprod));
			tomos.get(postomo).AsignarProdTomo(codigoProd,codigoTomo,descripcion); 
				
			
		}else{
			if (!isCatNuevo){ //SI es un catalogo vigente, es un error ya que se llamo desde un alta de tomo
			throw new TomoNoEncontradoException();
			}else{ //Si es un catalogo nuevo, se crea y añade el tomo a la coleccion del catalogo.
				Tomo tomo= new Tomo(em);
				tomo.setCodigoTomo(codigoTomo);
				tomo.setDescripcion(descripcion);
				tomos.add(tomo);
				tomo.AsignarProdTomo(codigoProd, codigoTomo,descripcion);
			}
		}

	}
	
	/**
	 * Este metodo evalua si un producto esta asignado al catalogo vigente. 
	 * @return: false si el producto esta asignado al catalogoVigente o true si no lo esta.
	 */
	public boolean estaProdAsignadoCatVigente(int codProd){
		
		boolean resultado=false;
		//Verificar que el producto no Exista en el Catalogo Vigente
		//ArrayList<Tomo> tomosVigentes= candela.getCatalogoVigente().getTomos();
		for(int i=0; i< tomos.size(); i++){ //Obtengo de cada tomo su coleccion de productos y la recorro
			ArrayList<Producto> prods= tomos.get(i).getProductos();
			for(int j=0; j< prods.size();j++){//Leo la lista de productos de cada tomo, y verifico que no este asignado al catalogo actual
				if(codProd==prods.get(j).getCodigo()){
					resultado=true;
				}
			}//fin del for productos
		}//fin del for de tomos
		return resultado;
	}
	
	
	
}	