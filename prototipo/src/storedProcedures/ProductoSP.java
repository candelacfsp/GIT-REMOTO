package storedProcedures;

import java.sql.SQLException;

import excepciones.ProductoNoExisteExcepcion;

import net.java.ao.EntityManager;

import persistencia.ProductoBD;
import persistencia.TipoDeProductoBD;
import utilidades.Constantes;

public class ProductoSP {

	public static void modificacionProducto(int codigo, double precio, int cantidad) throws SQLException{

		EntityManager em= new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);

		ProductoBD modificar []= null;

		modificar= em.find(ProductoBD.class,"codigo=?",codigo);
		modificar[0].setCantidadEnStock(cantidad);
		modificar[0].setPrecio(precio);
		modificar[0].save();


	}

	/**
	 * En este procedimiento almacenado se pasa el codigo de producto a asignar en BD y 
	 * el codigo del tipo de producto al que se asignara dicho producto.
	 * @param codigoproducto
	 * @param codigoTipoProducto
	 */
	public static void asignarATipoProd(int codigoproducto,int codigoTipoProducto)throws SQLException{

		EntityManager em= new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);
		ProductoBD prod []=null;
		prod=em.find(ProductoBD.class, "codigo=?",codigoproducto);

		//Se busca el tipo de producto.
		TipoDeProductoBD tipo[]=null;
		tipo= em.find(TipoDeProductoBD.class, "CodTipoProd=?",codigoTipoProducto);
		prod[0].setTipoDeProductoBD(tipo[0]);
		prod[0].save();
		//colTipoDeProducto.get(j).save();

	}


	public static void altaDeProducto(int codigo, String descripcion, double precio, int cantidad)throws SQLException{


		EntityManager em= new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);

		//Producto creado en BD
		ProductoBD productonuevo=null;
		productonuevo=em.create(ProductoBD.class);
		if (productonuevo != null){
			
			productonuevo.setCodigo(codigo);
			productonuevo.setDescripcion(descripcion);
			productonuevo.setPrecio(precio);
			productonuevo.setCantidadEnStock(cantidad);
			productonuevo.save();
		
		}else{
			throw new SQLException();
		}

	}
	
	
	public static boolean bajaDeProducto(int codigo) throws SQLException{
		
		EntityManager em= new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);
		
		boolean ejecucionOK=false;
		
		ProductoBD prod1[]=null;
		prod1 = em.find(ProductoBD.class, "codigo=?", codigo);
		if(prod1.length>0){
			em.delete(prod1[0]);
			ejecucionOK=true;	
		}	
			//throw new ProductoNoExisteExcepcion();
		
		return ejecucionOK;
	}


}
