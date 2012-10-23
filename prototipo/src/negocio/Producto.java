package negocio;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import excepciones.ProductoExisteExcepcion;
import excepciones.ProductoNoExisteExcepcion;
import excepciones.TipoProductoExcepcion;
import excepciones.TomoAsignadoExcepcion;

import persistencia.ProductoBD;
import persistencia.TipoDeProductoBD;
import persistencia.TomoBD;
import utilidades.Constantes;

import net.java.ao.EntityManager;

public class Producto {

	private Connection conexion=null;
	private int codigo;
	private String descripcion;
	private int cantidadEnStock;
	private double precio;
	private int TipoProducto;


	public Producto(Connection em){
		this.conexion=em;

	}
	/***
	 * modDeProducto es una funcion que modifica el precio y cantidad en stock de un producto
	 * @param codigo: entero que representa el producto
	 * @param precio: real es el costo del producto
	 * @param cantidad: entero es la cantidad existente en stock
	 * @param colProductos: Es la colección de productos que existe en Candela
	 * @throws ProductoNoExisteExcepcion 
	 * @throws SQLException 
	 */
	public void modDeProducto(int codigo, double precio, int cantidad, ArrayList<Producto> colProductos) throws  SQLException {
		//Se crea el objeto PreparedStatement
		CallableStatement sentencia=null;

		sentencia= conexion.prepareCall("{call modificacionProducto(?,?,?)}");

		

		//recorro la coleccion de productos
		for (int i = 0; i < colProductos.size(); i++) {
			//si encuentro el producto se llama al procedimiento almacenado que lo modifica
			if (colProductos.get(i).getCodigo()== codigo){
				//Se setean los parametros del proc. almacenado y se ejecuta.
				
				sentencia.setInt(1, codigo);
				sentencia.setDouble(2, precio);
				sentencia.setInt(3, cantidad);

				sentencia.execute();
				
			}
		}

	}		
	/***
	 * asignarATipoProducto asigna un producto a un tipo de producto
	 * @param codigoTProducto:entero es el codigo del tipo de producto
	 * @param codigoproducto:entero es el codigo del producto
	 * @param colProductos: es la coleccion de los productos que mantiene Candela
	 * @param colTipoDeProducto: es la coleccion de los tipos de productos que mantiene Candela
	 * @throws TipoProductoExcepcion 
	 * @throws ProductoNoExisteExcepcion 
	 * @throws SQLException 
	 */
	public void asignarATipoProducto(int codigoTProducto, int codigoproducto, ArrayList<Producto> colProductos, ArrayList<TipoDeProductoBD> colTipoDeProducto) throws TipoProductoExcepcion, ProductoNoExisteExcepcion, SQLException {
		boolean productoEncontrado= false;
		boolean tipoProductoEncontrado=false;
		//busco el producto
		for (int i = 0; i < colProductos.size(); i++) {
			if (colProductos.get(i).getCodigo()==codigoproducto){
				productoEncontrado= true;
				//busco en la coleccion de tipos de productos el tipo de producto
				for (int j = 0; j < colTipoDeProducto.size(); j++) {
					if (colTipoDeProducto.get(j).getCodTipoProd()==codigoTProducto){
						tipoProductoEncontrado=true;
						ProductoBD prod []=null;
						try {
							prod=conexion.find(ProductoBD.class, "codigo=?",codigoproducto);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							throw new SQLException();

						}
						prod[0].setTipoDeProductoBD(colTipoDeProducto.get(j));
						prod[0].save();
						colTipoDeProducto.get(j).save();

					}
				}
				if (!tipoProductoEncontrado){
					throw new TipoProductoExcepcion();
				}

			}

		}
		if (!productoEncontrado){
			throw new ProductoNoExisteExcepcion();
		}
	}

	/***
	 * Este método da de alta un producto y devuelve un error en caso de que exista
	 * @param codigo:entero es el codigo del producto
	 * @param descripcion:cadena es el comentario del producto
	 * @param precio:real es el costo del producto
	 * @param cantidad:entero es la cantidad en stock
	 * @param colProductos:arreglo de ProductoBD es la colección de productos que mantiene Candela en memoria
	 * @return: entero retorna errores definidos en Errores.java {@link Errores.java}
	 * @author Huincalef-Mansilla
	 * @throws ProductoExisteExcepcion 
	 * @throws SQLException 
	 */
	public void altaDeProducto(int codigo, String descripcion, double precio,
			int cantidad, ArrayList<Producto> colProductos) throws ProductoExisteExcepcion, SQLException {

		boolean encontrado= false;

		for (int i = 0; i < colProductos.size(); i++) {
			//Si el codigo se encuentra se activa la bandera
			if(colProductos.get(i).getCodigo()== codigo){
				encontrado= true;

			}
		}
		if (!encontrado){
			//si no encontre el codigo
			ProductoBD productonuevo=null;



			productonuevo=conexion.create(ProductoBD.class);

			if (productonuevo != null){
				productonuevo.setCodigo(codigo);

				productonuevo.setDescripcion(descripcion);
				productonuevo.setPrecio(precio);
				productonuevo.setCantidadEnStock(cantidad);



				productonuevo.save();

				Producto prod=new Producto(this.conexion);

				prod.setCantidadEnStock(cantidad);
				prod.setCodigo(codigo);
				prod.setDescripcion(descripcion);
				prod.setPrecio(precio);
				prod.setTipoProducto(-1);
				//TODO DAMIAN modificación, desde memoria para poder detectar en el flash sobre el generador.


				colProductos.add(prod);


			}else{
				throw new SQLException();
			}

		}else{
			throw new ProductoExisteExcepcion();
		}

	}
	/***
	 * bajaDeProducto da de baja un producto mediante su codigo y actualiza la colección de productos de candela
	 * @param codigo:entero es el codigo con el cual se identifica el producto
	 * @param colProductos lista de productos de tipo ProductosBD que posee Candela
	 * @author Huincalef - Mansilla
	 * @throws SQLException 
	 * @throws TomoAsignadoExcepcion 
	 * @throws ProductoNoExisteExcepcion 
	 * @see altaDeProducto
	 */
	public void bajaDeProducto(int codigo, ArrayList<Producto> colProductos) throws SQLException, TomoAsignadoExcepcion, ProductoNoExisteExcepcion {
		//Se recorre la coleccion en busca del producto

		for (int i = 0; i < colProductos.size(); i++) {
			if(colProductos.get(i).getCodigo()== codigo){
				//si se encuentra el producto
				TomoBD tomos []=null;

				tomos= conexion.find(TomoBD.class);


				if (tomos!=null){
					//Si no encuentro ningun tomo asignado al producto, procedo a eliminarlo
					for (int j = 0; j < tomos.length; j++) {
						for (int k = 0; k < tomos[j].getProductos().length; k++) {
							if (tomos[j].getProductos()[k].getCodigo()==colProductos.get(i).getCodigo()){
								throw new TomoAsignadoExcepcion();
							}
						}
					}
				}


				ProductoBD prod1[];
				try {
					prod1 = conexion.find(ProductoBD.class, "codigo=?", codigo);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new ProductoNoExisteExcepcion();
				}



				conexion.delete(prod1[0]);
				//Se quita de la coleccion de productos
				colProductos.remove(i);

			}

		}

	}
	/***
	 * consPrecioProd devuelve un real que equivale al precio de un producto
	 * @param codigo: entero es el codigo del producto a consultar
	 * @param colProductos: es la coleccion de productos de Candela
	 * @return: double si productoexiste entonces devuelve el precio sino devuelve productonoexiste
	 * @throws ProductoNoExisteExcepcion 
	 */
	public double consPrecioProd(int codigo, ArrayList<Producto> arrayList) throws ProductoNoExisteExcepcion {
		for (int i = 0; i < arrayList.size(); i++) {
			if(arrayList.get(i).getCodigo()== codigo){
				return arrayList.get(i).getPrecio();

			}
		}		
		throw new ProductoNoExisteExcepcion();

	}


	/**
	 * getCodigo
	 * Obtiene el codigo que identifica al producto.
	 * @return: un numero de codigo correspondiente al producto.
	 */
	public int getCodigo() {
		return codigo;
	}
	/**
	 * setCodigo
	 * Setea el codigo asociado con un producto
	 * @param codigo: numero de codigo nuevo del producto
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	/**
	 * getDescripcion
	 * Obtiene una descripcion asociada al producto.
	 * @return: una descripcion del producto
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * setDescripcion
	 * Setea la descripcion de un producto.
	 * @param descripcion: descripcion nueva que sera adoptada por el producto.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * getCantidadEnStock
	 * Obtiene la cantidad en stock de un producto detemrinado.
	 * @return: un entero que representa la cantidad de un producto para la venta en local.
	 */
	public int getCantidadEnStock() {
		return cantidadEnStock;
	}
	/**
	 * setCantidadEnStock
	 * Setea la cantidad de un producto en stock.
	 * @param cantidadEnStock: cantidad actual de un producto en stock.
	 */
	public void setCantidadEnStock(int cantidadEnStock) {
		this.cantidadEnStock = cantidadEnStock;
	}
	/**
	 * getPrecio
	 * Obtiene el precio unitario de un producto.
	 * @return: un numero real que representa el importe de un producto.
	 */
	public double getPrecio() {
		return precio;
	}
	/**
	 * setPrecio
	 * Setea el precio de un Producto.
	 * @param precio: precio del producto.
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public int getTipoProducto() {
		return this.TipoProducto;
	}
	public void setTipoProducto(int tipoProd1) {
		this.TipoProducto= tipoProd1;
	}


}


