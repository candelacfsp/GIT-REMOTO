package storedProcedures;

import java.sql.Date;
import java.sql.SQLException;

import net.java.ao.EntityManager;

import persistencia.PedidoPersonalBD;
import utilidades.Constantes;

public class PedidoPersonalSP {

	public static void crearPedido(Date fecha_emision, Date fecha_recepcion, int numeroPedido)throws SQLException{
		//creo un pedidoBD para almacenar el pedido

		EntityManager em= new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);

		PedidoPersonalBD pedido= null;
		try {
			pedido= em.create(PedidoPersonalBD.class);
		} catch (SQLException e) {
			System.out.println("Error al crear un pedido");
			e.printStackTrace();
		}
		if (pedido!= null){
			pedido.setFechaEmision(fecha_emision);
			pedido.setFechaRecepcion(fecha_recepcion);
			pedido.setNumeroPedido(numeroPedido);
			 //TODO ver si se setea aquí a pedidofabrica
			pedido.save();
		
			
		}
	
		
		
	}
	
	
}
