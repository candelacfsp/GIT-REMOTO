package negocio;

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
	public PedidoPersonal(EntityManager em,ArrayList<DetallePedidoPersonal> detalles,int nroPedido, Date fecha_emision,Date fecha_recepcion){
		super(em,nroPedido,fecha_emision,fecha_recepcion);
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


	public PedidoPersonalBD crearPedido(ArrayList<DetallePedidoPersonal> colDetalles, ArrayList<Producto> colProductos) {

		//creo un pedidoBD para almacenar el pedido
		PedidoPersonalBD pedido= null;
		try {
			pedido= this.getEm().create(PedidoPersonalBD.class);
		} catch (SQLException e) {
			System.out.println("Error al crear un pedido");
			e.printStackTrace();
		}
		if (pedido!= null){
			pedido.setFechaEmision(getFecha_emision());
			pedido.setFechaRecepcion(getFecha_recepcion());
			pedido.setNumeroPedido(getNumeroPedido());
			 //TODO ver si se setea aquí a pedidofabrica
			pedido.save();
			for (int i = 0; i < colDetalles.size(); i++) {
				
				//por cada uno de los detalles los almaceno en base de datos
				colDetalles.get(i).crearDetalle(pedido, colDetalles.get(i));
			}
			
		}
		return pedido;
		
	}
	
}
