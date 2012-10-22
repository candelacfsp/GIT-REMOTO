package storedProcedures;

import java.sql.Date;
import java.sql.SQLException;


import net.java.ao.EntityManager;

import persistencia.FacturaPersonalBD;
import persistencia.PedidoPersonalBD;
import persistencia.usuarioBD;
import utilidades.Constantes;

public class FacturaPersonalSP {

	
	public static void crearFactura(Date fecha,int numero, int tipo, int dniUsr, boolean pagada, int numeroPedido) throws SQLException{
		
		
		
		
		EntityManager em= new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);

		FacturaPersonalBD facturaBD= null;
		facturaBD=em.create(FacturaPersonalBD.class);
		
		if (facturaBD != null){
			facturaBD.setFecha(fecha);
			facturaBD.setNumero(numero);
			facturaBD.setTipo(tipo);
			usuarioBD usr[]=null;
			usr=em.find(usuarioBD.class,"dni=?",dniUsr);
			
			facturaBD.setUsuario(usr[0]);
			facturaBD.setPagada(pagada);
			PedidoPersonalBD peds[]=null;
			peds= em.find(PedidoPersonalBD.class,"numeroPedido=?",numeroPedido);
			
			facturaBD.setPedidoPersonalBD(peds[0]);
			facturaBD.save();
		}
		
		
	}
}
