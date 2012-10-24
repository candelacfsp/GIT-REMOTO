package storedProcedures;

import java.sql.SQLException;

import persistencia.TipoDeProductoBD;
import excepciones.TipoProductoExcepcion;
import net.java.ao.EntityManager;
import utilidades.Constantes;

public class TipoDeProductoSP {
	
	public static void modificarDescripcionTipoProducto(int codigoTipoProd, String descripcion) throws SQLException{
		EntityManager em= new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);
		
		TipoDeProductoBD [] tipos=null;
		
		tipos= em.find(TipoDeProductoBD.class,"CodTipoProd=?",codigoTipoProd);
		
		tipos[0].setDescripcion(descripcion);
		tipos[0].save();
		
		
	}
	

}
