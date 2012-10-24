package storedProcedures;

import java.sql.Date;
import java.sql.SQLException;

import net.java.ao.EntityManager;

import persistencia.PedidoFabricaBD;
import persistencia.PedidoPersonalBD;
import utilidades.Constantes;

public class PedidoFabricaSP {



	public static void marcarPedidoSP(int nroPedido, Date fechaPedido)throws SQLException{


		EntityManager em= new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);


		PedidoFabricaBD [] pedFab=em.find(PedidoFabricaBD.class,"numeropedido=?",nroPedido);

		if(pedFab.length>0){
			pedFab[0].setRecibido(true);
			//Cuando se marca un pedido como recibido, se debe ademas establecer la fecha de recepcion

			//Calendar diaActual= Calendar.getInstance();
			//Date fechaEmision= diaActual.getTime();
			//pedFab[0].setFechaEmision(fechaEmision);

			//TODO MODIFICADO RODRIGO
			pedFab[0].setFechaRecepcion(fechaPedido);			
			pedFab[0].save();

			PedidoPersonalBD [] pedPers=null;
			pedPers=pedFab[0].getColPedidoPersonalBD();

			if(pedPers.length>0){

				pedPers[0].setFechaRecepcion(fechaPedido);
				pedPers[0].save();

			}else{
				throw new SQLException(); //TODO REVISAR
			}

		}

	}
}
