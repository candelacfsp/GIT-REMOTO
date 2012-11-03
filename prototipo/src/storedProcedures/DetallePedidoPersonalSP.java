package storedProcedures;

import java.sql.SQLException;

import negocio.PedidoPersonal;
import net.java.ao.EntityManager;

import persistencia.DetallePedidoPersonalBD;
import persistencia.PedidoPersonalBD;
import persistencia.ProductoBD;
import utilidades.Constantes;

public class DetallePedidoPersonalSP {


	public static void crearDetalle(int codigoDetallePersonal,int cantidad,String color,double precio,int numeroPedido) throws SQLException{


		EntityManager em= new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);

		DetallePedidoPersonalBD detallePedidoBD= null;

		try {
			detallePedidoBD=em.create(DetallePedidoPersonalBD.class);
		} catch (SQLException e) {
			System.out.println("Error al crear detalle de pedido personal");
			e.printStackTrace();
		}
		if (detallePedidoBD!= null){
			//busco el producto en base para setearlo

			ProductoBD[] prod =null;
			try {
				prod= em.find(ProductoBD.class, "codigo =?", codigoDetallePersonal);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (prod!= null){
				detallePedidoBD.setCantidad(cantidad);
				detallePedidoBD.setColor(color);
				//calculo el precio por la cantidad del producto

				detallePedidoBD.setPrecio(cantidad*precio);

				//Hacer la busqueda del pedido

				PedidoPersonalBD ped[]=null;

				ped=em.find(PedidoPersonalBD.class,"numeroPedido=?",numeroPedido);

				detallePedidoBD.setPedidoPersonalBD(ped[0]);

				detallePedidoBD.setProductoBD(prod[0]);

				detallePedidoBD.save();

				//Actualizo el producto en base de datos, debería avisarle a Candela

				prod[0].setCantidadEnStock(prod[0].getCantidadEnStock()-cantidad);

				prod[0].save();



			}
		}
	}
}
