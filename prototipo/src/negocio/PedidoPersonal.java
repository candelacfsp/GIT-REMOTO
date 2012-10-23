package negocio;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import persistencia.PedidoPersonalBD;
import persistencia.ProductoBD;

import net.java.ao.EntityManager;

public class PedidoPersonal extends Pedido{

	/**
	 * Esta clase contiene la lógica relacionada con los pedidos de personal.
	 * @author  Huincalef Rodrigo-Mansilla Damian
	 *
	 */
	private ArrayList<DetallePedidoPersonal> detalles;
	
	/**
	 * PedidoPersonal
	 * Constructor de la clase pedido de personal, que la inicializa con sus datos y un manejador de entidades.
	 * @param em: manejador de entidades, empleado para realizar lecturas/escrituras en la base de datos.
	 * @param detalles: coleccion de detalles asociados con un pedido de personal.
	 * @param nroPedido: numero de pedido de personal
	 * @param fecha_emision: fecha de emision de un pedido de personal
	 * @param fecha_recepcion: fecha de recepcion de un pedido de personal
	 */
	public PedidoPersonal(Connection conexion,ArrayList<DetallePedidoPersonal> detalles,int nroPedido, Date fecha_emision,Date fecha_recepcion){
		super(conexion,nroPedido,fecha_emision,fecha_recepcion);
		this.detalles=detalles;
		
	}

	/**
	 * getDetalles
	 * Obtiene los detalles de un pedido de personal
	 * @return: una coleccion de los detalles asociados a un pedido de personal.
	 */
	public ArrayList<DetallePedidoPersonal> getDetalles() {
		return detalles;
	}


	@Override
	/**
	 * total
	 * Metodo empleado para calcular el monto total de un pedido de personal.
	 */
	public float total() {
		float total=0;
		for (DetallePedidoPersonal det1 : detalles) {
			total=0;
			total+=det1.subtotal();			
		}
		return total;
	}


	public void crearPedido(ArrayList<DetallePedidoPersonal> colDetalles,ArrayList<Producto> colProductos) throws SQLException {
		
		CallableStatement sentencia=null;

		sentencia= super.getConnection().prepareCall("{call crearpedido(?,?,?)}");

		//Seteo los argumentos de la funcion.
		sentencia.setDate(1, (java.sql.Date)this.getFecha_emision());
		sentencia.setDate(2, (java.sql.Date)this.getFecha_recepcion());
		
		sentencia.setInt(3,this.getNumeroPedido());

		//Seteo parametro de salida

		//sentencia.registerOutParameter(1, java.sql.Types.VARCHAR);
		
		
		sentencia.execute();
		
		
		for (int i = 0; i < colDetalles.size(); i++) {
			
			//por cada uno de los detalles los almaceno en base de datos
			colDetalles.get(i).crearDetalle(this, colProductos);
			
			
			
		}
			
		
	}
	
}
