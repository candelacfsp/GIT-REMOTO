package negocio;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
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
	private ArrayList<Tomo> tomos=null;
	//private EntityManager em=null;
	private Connection conn=null;
	/**
	 * Constructor de cat�logo que inicializa el catalogo con su fecha de inicio de vigencia, su fecha de fin
	 * de vigencia y su manejador de entidades.
	 * @param em: manejador de entidades, empleado para realizar lecturas/escrituras a la base de datos.
	 * @param fecha_inicio: fecha de inicio de vigencia del catalogo.
	 * @param fecha_fin: fecha hasta la que estara en vigencia el cat�logo
	 */
	public Catalogo(Connection con,Date fecha_inicio) throws SQLException{ //TODO MODIFICADO RODRIGO
		//	this.em=em;
		this.tomos=new ArrayList<Tomo>();
		//El anio de Vigencia, es el anio de la fecha_inicio(la fecha menor)
		//Se crean fecha_inicio y fecha_fin con la fecha actual y se les setea el valor de las fechas
		//que se ingresaron como parametro.
		Calendar fechaVigencia= Calendar.getInstance();
		fechaVigencia.setTime(fecha_inicio);

		this.anioVigencia= fechaVigencia.get(Calendar.YEAR);

		
		conn = con;

	}


	public Catalogo(Connection conexion) {
		this.tomos= new ArrayList<Tomo>();
		this.conn=conexion;
		//Se inicializa el catalogo con anio -1 para mostrar que no existe realmente.
		//sino que se cuenta con su referencia en memoria.
		this.anioVigencia=-1;
		
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
	private void asignarTomoACatalogo(int codigo,String descripcion) {

		//Se crea el tomo en memoria
		Tomo tom1=new Tomo(conn);
		tom1.setCodigoTomo(codigo);
		tom1.setDescripcion(descripcion);




		//Se lo a�ade a la coleccion de tomos del catalogo.
		tomos.add(tom1);
	}

	/**
	 * cancelarCatalogo
	 * Este metodo se encarga de realizar la desasociacion del tomo y de los productos
	 * en memoria y en BD si un usuario cancela el alta de tomo en el CU. 
	 * "Asociacion de productos a Tomo".
	 * Este metodo se llama en restablecerCat.jsp.
	 * esCatNuevo sirve para detectar el tipo de catalogo para borarrlo en BD. La coleccion "codsTomosAsignados"
	 * sirve para tener una lista de los codigos de tomos que se deben buscar y eliminar en memmoria y en BD.
	 * 		
	 */
	 /* Los posibles estados para cancelarAltaCatalogo son:
	 *Cancelar la asignacion de productos en el Alta de TOmo comun.
			*Cancelar la asignacion de productos cuando no asigne ninguno.
			*Cancelar la asignacion de productos cuando asigne uno o mas productos
	 *Cancelar la asignacion de productos en el Alta de TOmo llamado por el ALta de Catalogo.
	 		*Cancelar la asignacion de productos cuando no se tiene nignun producto asignado.
	 		*Cancelar la asignacion de productos cuando se tiene un catalogo con uno o mas productos.
	 */
	public void cancelarAltaCatalogo(boolean esCatNuevo, ArrayList<Integer> codsTomosAsignados){
		

		EntityManager em= new EntityManager(Constantes.URL, Constantes.USUARIO, Constantes.PASS);
		
		
		if(!esCatNuevo){//Si es un catalogoVigente tengo que eliminar de memoria y de BD solamente los productos asignados.
			//Por cada uno de los codigos de los productos asignados se tiene que buscar su objetoBD y desasignar los productos
			// y borrar la tupla tomo correspondiente.

			//Si los codigosTomosAsignados=null, significa que el reestablecerCat fue llamado desde
			//"cancelar" de un alta de tomo comun, por lo que solamente se tiene que setear la referencia de catalogoVigente
			//a null. Cuando se llama de "Cancelar" del alta de tomo (llamado de alta de Catalogo), no se tiene problemas con 
			// codsTomosAsignados ya que se tienen que borrar todos los productos y tomos cargados.
			
		  if(codsTomosAsignados!=null){
				
			
			for (int i = 0; i < codsTomosAsignados.size(); i++) {
				//Buscar los tomos en la coleccion de tomos del catalogoVigente y borrarlos 
				//Se borra el tomo de memoria
				for (int j = 0; j < tomos.size(); j++) {
					if(tomos.get(j).getCodigoTomo()== codsTomosAsignados.get(i).intValue()){
						tomos.remove(tomos.get(j));
						break;
					}
					
				}//Fin del for que recorre la coleccion de productos de Candela
				//Se busca el tomo actual en BD y se desasginan los productos
				TomoBD[] tomos=null;
				try {
					
					//REVISAR! HACER EL FIND CON EL codsTomosAsignados.get(i).intValue() COMO CLAVE. 
					
					//Leo el tomo actual de la BD y busco su objeto BD para desasignarle los productos y eliminarlo
					//Se busca el tomo en la BD segun su codigo.
					
					tomos = em.find(TomoBD.class,"codigoTomo=?",codsTomosAsignados.get(i).intValue());
					if(tomos.length > 0){
					
						ProductoBD prods[]=null;
						prods=tomos[0].getProductos();
						
						//Si el tomo tiene productos asociados se desasocian dichos productos. 
						//Sino solamente se borra la tupla que correspodne al tomo.
						if (prods.length >0 ){
							for (int j2 = 0; j2 < prods.length; j2++) {
								prods[j2].setTomoBD(null);
								prods[j2].save();
							}//Fin del for que recorre desasocia los productos del tomo en BD
						
						}
						//Se borra el tomo
						em.delete(tomos[0]);		
					
					}//Fin del IF de tomosBD
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}//Fin del for que recorre la coleccion de Codigos de tomos asignados
			
		  }//Fin del IF de cosTomosAsignados
			
		}else{
			
		//Si es un catalogoNuevo se deben eliminar todos los productos asignados de memoria y de BD,
		//en el catalogo nuevo.
			
		//1. Eliminar los productos de los tomos del catalogo nuevo en memoria y en BD
		for (int i = 0; i < tomos.size(); i++) {
			//Se recorren por cada uno de los tomos sus productos.
			ArrayList<Producto> prods = tomos.get(i).getProductos();
			for (int j = 0; j < prods.size(); j++) {
				int codigo=prods.get(j).getCodigo();
				prods.remove(prods.get(j));
				ProductoBD[] productosBD=null;
				try {
					 productosBD= em.find(ProductoBD.class,"codigo=?",codigo);
					if (productosBD.length > 0) {
						productosBD[0].setTomoBD(null);
						productosBD[0].save();
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}//FIn del for de los productos
			
			
			
			//2. Desaociar el/los tomos del catalogo nuevo en memoria y en BD
			int codigoT=tomos.get(i).getCodigoTomo();
			
			tomos.remove(tomos.get(i));
			try{
				TomoBD[] tomo = em.find(TomoBD.class,"codigoTomo=?",codigoT);
				if(tomo.length>0){
					tomo[0].setCatalogoBD(null);
					tomo[0].save();
					em.delete(tomo[0]);
					
				}
				
				//Si es Catalogo nuevo se borra la tupla del catalogo nuevo en CatalogoBD que siempre es la ultima
					//NOTA: Es necesario borrar el ultimo catalogo por que al cargar el sistema siempre se lee el ulitmo. Si no se borra
					//la BD quedaria inconsistente ya que tendría un catalogo que no tiene al menos un tomo, con al menos un producto.
					
					CatalogoBD [] cat= em.find(CatalogoBD.class);
					int ultimoCatalogo= cat.length-1;
					em.delete(cat[ultimoCatalogo]);
				
			}catch(SQLException sql){
				
				sql.printStackTrace();
			}
			
		}//Fin del for de los tomos 
		
		//3.Eliminar el catalogo nuevo de BD. La eliminacion del catalogo nuevo en memoria
		//se realiza desde Candela que tiene la referencia en restablecerCat.jsp
		try {
			
			CatalogoBD[] catalogo = em.find(CatalogoBD.class);
			//Si se tiene mas de un catalogo, se borra el ultimo 
			//sino no se borra.
			if(catalogo.length >1){
				//Se obtiene del conjunto de catalogos en la BD el ultimo.
				int ID=(catalogo.length-1);
				em.delete(catalogo[ID]);
			}//Fin del IF de Catalogo.
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error eliminando el catalogo de la BD!");
		}	
		
		}//Fin del if de esCatNuevo
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

		//2.Se  llama a los procedimientos almacenados.
		CallableStatement sentencia=null;

		//NOTA: En el altatomo  si es un catalogoNuevo se da de alta
		//sino se obtiene la referencia al ultimo catalogoBD.
		
		sentencia= conn.prepareCall("{call altatomo(?,?,?,?)}");

		//Seteo los argumentos de la funcion.
		sentencia.setInt(1, codigo);
		sentencia.setString(2, descripcion);
		sentencia.setBoolean(3,esCatalogoNuevo);
		sentencia.setInt(4, anioVigencia);
		
		sentencia.execute();

		
		
		
	}
	/**
	 * bajaTomo
	 * Da de baja un tomo del catalogo.
	 * @param codigo: codigo del tomo que se dar� de baja
	 * @return: un valor entero indicando el resultado de la operacion.
	 */
	public void bajaTomo(int codigo) throws TomoNoExisteExcepcion, SQLException{ 

		this.eliminarProductosTomo(codigo);

		//Se llama a la sentencia preparada de Baja de Tomo
		CallableStatement sentencia=null;

		sentencia= conn.prepareCall("{call bajatomo(?)}");

		//Seteo los argumentos de la funcion.
		sentencia.setInt(1, codigo);
		
		//Seteo parametro de salida

		//sentencia.registerOutParameter(1, java.sql.Types.VARCHAR);
		
		
		sentencia.execute();

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
		while (i<tomos.size() && (tomos.get(i).getCodigoTomo()!=nroTomo)){
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
				Tomo tomo= new Tomo(conn);
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