package negocio;

import java.sql.CallableStatement;
import java.sql.Connection;
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
	private Connection conexion=null;
	
	
	/**
	 * Constructor de la clase tomo, que inicializa la misma con su manejador de entidades.
	 * @param em
	 */
	public Tomo( Connection em){
		this.conexion=em;
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
		
		//2.Se  llama a los procedimientos almacenados.
		CallableStatement sentencia=null;

		sentencia= conexion.prepareCall("{call asignarprodtomo(?,?,?)}");

		//Seteo los argumentos de la funcion.
		sentencia.setInt(1, codDeProd);
		sentencia.setInt(2, codigoDeTomo);
		sentencia.setString(3,descripcion);
	
		sentencia.execute();
		
		
	}
	/**
	 * desasignarProdTomo
	 * Desasigna un producto de un tomo seleccionado.
	 * @param codDeProd: n�mero del producto a desasignar.
	 * @param codDeTomo: n�mero de tomo del que desasignara el producto.
	 * @return: un numero indicando el resultado de la operaci�n.
	 */
	public void desasignarProdTomo(int codDeProd, int codDeTomo) throws SQLException{
		//2.Se  llama a los procedimientos almacenados.
		CallableStatement sentencia=null;

		sentencia= conexion.prepareCall("{call desasignarprodtomo(?,?)}");

		//Seteo los argumentos de la funcion.
		sentencia.setInt(1, codDeProd);
		sentencia.setInt(2, codDeTomo);
	
		sentencia.execute();
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
