package storedProcedures;

import java.sql.SQLException;

import net.java.ao.EntityManager;

import persistencia.ProductoBD;
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
	
	
	public static asignarATipo


}
