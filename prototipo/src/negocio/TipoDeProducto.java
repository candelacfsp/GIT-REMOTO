package negocio;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import excepciones.TipoProductoExcepcion;

import persistencia.TipoDeProductoBD;

import net.java.ao.EntityManager;

public class TipoDeProducto {
	private Connection conexion;
	private int codigoTipoProducto;
	private String descripcion;
	
	
	public TipoDeProducto(Connection conexion) {
		this.conexion=conexion;
	}
/***
 * modificarDescripcionTipoProducto modifica la descripcion del tipo de producto
 * @param codigoTipoDeProducto: entero es el codigo del tipo de producto
 * @param descripcion:cadena la descripcion del tipo de producto
 * @param colTipoDeProducto: arraylist es la coleccion de tipo de producto que mantiene Candela
 * @return: entero retorna error si no encuentra el tipo de producto
 * @throws TipoProductoExcepcion 
 * @throws SQLException 
 */
	public void modificarDescripcionTipoProducto(int codigoTipoDeProducto, String descripcion, ArrayList<TipoDeProducto> colTipoDeProducto) throws TipoProductoExcepcion, SQLException {
		boolean encontrado=false;
		for (int i = 0; i < colTipoDeProducto.size(); i++) {
			if (colTipoDeProducto.get(i).getCodigoTipoProducto()==codigoTipoDeProducto){
				
				//Creo la sentencia preparada
				
				//2.Se  llama a los procedimientos almacenados.
				CallableStatement sentencia=null;

				sentencia= conexion.prepareCall("{call modificardescripciontipoproducto(?,?)}");

				//Seteo los argumentos de la funcion.
				sentencia.setInt(1, codigoTipoDeProducto);
				sentencia.setString(2, descripcion);
				
				sentencia.execute();
				
				
				encontrado=true;
			}
		}
		if(!encontrado){
			throw new TipoProductoExcepcion();	
		}
		
	}
public Connection getConexion() {
	return conexion;
}
public void setConexion(Connection conexion) {
	this.conexion = conexion;
}
public int getCodigoTipoProducto() {
	return codigoTipoProducto;
}
public void setCodigoTipoProducto(int codigoTipoProducto) {
	this.codigoTipoProducto = codigoTipoProducto;
}
public String getDescripcion() {
	return descripcion;
}
public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}
	


}
