package negocio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistencia.ProductoBD;
import persistencia.TomoBD;
import utilidades.Constantes;

import net.java.ao.EntityManager;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/**
 * Esta clase contiene la informacion relativa a los tomos en Candela, asi como los una coleccion con los productos
 * asociados al tomo.
 * @author Huincalef Rodrigo-Mansilla Damian
 *
 */
public class Tomo {
	private int codigoTomo;
	private String descripcion=null;
	private ArrayList<Producto> productos=null;
	private EntityManager em=null;
	/**
	 * Constructor de la clase tomo, que inicializa la misma con su manejador de entidades.
	 * @param em
	 */
	public Tomo( EntityManager em){
 
		this.em=em;
		this.productos=new ArrayList<Producto>();
		
	}
	
	
	
	/**
	 * getProductos
	 * Obtiene la coleccion de productos asociada con el tomo.
	 * @return: una coleccion de producto
	 */
	public ArrayList<Producto> getProductos() {
		return productos;
	}



	/**
	 * setDescripcion
	 * Obtiene una descripcion correspondiente al tomo
	 * @return: una descripcion del tomo.
	 */
	public String getDescripcion() {
		return descripcion;
	}



	/**
	 * setDescripcion
	 * Setea la descripcion para un tomo
	 * @param descripcion: descripcion que sera seteada al tomo.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	/**
	 * setCodigoTomo
	 * Setea el c�digo de un determinado tomo.
	 * @param codigoTomo: codigo para un tomo determinado.
	 */
	public void setCodigoTomo(int codigoTomo) {
		this.codigoTomo = codigoTomo;
	}



	/**
	 * getCodigoTomo
	 * Obtiene el codigo para el tomo
	 * @return: un numero de tomo.
	 */
	public int getCodigoTomo() {
		return codigoTomo;
	}
	/**
	 * AsignarProdTomo
	 * Asigna un producto a un tomo en la base de datos.
	 * @param codDeProd: numero del producto.
	 * @param codigoDeTomo: numero del tomo.
	 * @return: un numero que indica el resultado de la operacion.
	 */
	public void AsignarProdTomo(int codDeProd, int codigoDeTomo, String descripcion) throws SQLException{
		//1.Leer el Producto de la BD
		ProductoBD prods[]=null;
		
		//try {
			prods=em.find(ProductoBD.class,"codigo= ?",codDeProd);
		/*} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Error al encontrar el producto en la BD");
			System.exit(Constantes.EXCEPCION_SQL);
		}*/
  
		//2. Leer el tomo de la BD			
			 
		TomoBD[] tomo=null;
	//	try {
			tomo = em.find(TomoBD.class,"codigotomo= ?",codigoDeTomo);
			
	/*	} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error al encontrar el Tomo en la BD!");
				System.exit(Constantes.EXCEPCION_SQL);
		} */
		
		//3.Asignar el Producto persistente al tomo
		if (tomo.length>0){ //Si es un alta tomo con un catVigente
			prods[0].setTomoBD(tomo[0]);
			
		}else{ //SI es un alta Catalogo, y se pasa un nuevo tomo
			TomoBD tomo1= em.create(TomoBD.class);
			tomo1.setCodigoTomo(codigoDeTomo);
			tomo1.setDescripcion(descripcion);
			tomo1.save();
			prods[0].setTomoBD(tomo1);
		}
		prods[0].save();
		
	//	return Constantes.PRODUCTO_ASIGNADO;
		
	}
	/**
	 * desasignarProdTomo
	 * Desasigna un producto de un tomo seleccionado.
	 * @param codDeProd: n�mero del producto a desasignar.
	 * @param codDeTomo: n�mero de tomo del que desasignara el producto.
	 * @return: un numero indicando el resultado de la operaci�n.
	 */
	public void desasignarProdTomo(int codDeProd, int codDeTomo) throws SQLException{
		// Buscar el ProductoBD y el tomoBD y desasignar el producto del tomoBD
		ProductoBD [] prods=null;
		//try {
			prods= em.find(ProductoBD.class,"codigo = ?",codDeProd);
		/*} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al borrar la asociacion entre tom y producto de la BD");
			System.exit(Constantes.EXCEPCION_SQL);
		}*/
		prods[0].setTomoBD(null); //Seteo el tomoBD de producto en NULL
		prods[0].save();
		
	//	return Constantes.PRODUCTO_DESASIGNADO_OK;
	}
	
	//Metodos necesarios para jasper!!!!!!
	public List getListaProductos(){
		return this.getProductos();
	}
	public void setListaProductos(List productos){
		this.productos = (ArrayList<Producto>) productos;
	}
	public void addProducto(Producto producto){
		this.productos.add(producto);
	}
	
	public JRDataSource getColProductos(){
		return new JRBeanCollectionDataSource(this.productos);
	}
}
