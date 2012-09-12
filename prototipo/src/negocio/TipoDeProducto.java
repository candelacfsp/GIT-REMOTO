package negocio;

import java.util.ArrayList;

import excepciones.TipoProductoExcepcion;

import persistencia.TipoDeProductoBD;

import net.java.ao.EntityManager;

public class TipoDeProducto {
	EntityManager em= null;

	public TipoDeProducto(EntityManager em) {
	this.em=em;
	}
/***
 * modificarDescripcionTipoProducto modifica la descripcion del tipo de producto
 * @param codigoTipoDeProducto: entero es el codigo del tipo de producto
 * @param descripcion:cadena la descripcion del tipo de producto
 * @param colTipoDeProducto: arraylist es la coleccion de tipo de producto que mantiene Candela
 * @return: entero retorna error si no encuentra el tipo de producto
 * @throws TipoProductoExcepcion 
 */
	public int modificarDescripcionTipoProducto(int codigoTipoDeProducto, String descripcion, ArrayList<TipoDeProductoBD> colTipoDeProducto) throws TipoProductoExcepcion {
		for (int i = 0; i < colTipoDeProducto.size(); i++) {
			if (colTipoDeProducto.get(i).getCodTipoProd()==codigoTipoDeProducto){
				colTipoDeProducto.get(i).setDescripcion(descripcion);
				colTipoDeProducto.get(i).save();
				System.exit(0);
			}
		}
		throw new TipoProductoExcepcion();
	}
	


}
