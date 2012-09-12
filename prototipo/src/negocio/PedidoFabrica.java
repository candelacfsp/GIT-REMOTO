package negocio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import persistencia.PedidoFabricaBD;
import persistencia.PedidoPersonalBD;

import net.java.ao.EntityManager;
/**
 * Esta clase se encarga de mantener la informacion correspodiente a un pedido fabrica. En esta clase se calcula el total
 * de un pedido de fabrica y se marca el pedido de fabrica como recibido. 
 * @author Huincalef Rodrigo-Damian Mansilla.
 *
 */
public class PedidoFabrica extends Pedido {

	private boolean pedido_recibido;
	private ArrayList<PedidoPersonal> pedidosPers;
 
	
	/**
	 * Constructor de la clase pedido de fabrica, que inicializa el pedido de fabrica con sus correspondientes campos,
	 *  una coleccion de los pedidos de personal que componen el pedido de fabrica y un manejador de entidades.
	 * @param em: manejador de entidades empleado para realizar lecturas/escrituras a la base de datos.
	 * @param pedPers: coleccion de los pedidos de personal.
	 * @param nroPedido: numero de pedido de fabrica.
	 * @param fecha_emision: fecha de emision del pedido de fabrica.
	 * @param fecha_recepcion: fecha de recepcion del pedido de fabrica.
	 * @param recibido: estado del pedido de fabrica que indica si el pedido de fabrica fue recibido o no.
	 */
	public PedidoFabrica(EntityManager em,ArrayList<PedidoPersonal> pedPers,int nroPedido,Date fecha_emision,Date fecha_recepcion, boolean recibido){
		super(em,nroPedido,fecha_emision,fecha_recepcion);
		this.pedido_recibido=recibido;
		this.pedidosPers=pedPers;
	}
	
	//TODO MODIFICADO RODRIGO 02-09-2012
	/**
	 * marcarPedidoRecibido
	 * Marca un pedido de fabrica como recibido y registra la moficacion en la BD.
	 * @param nroPedido: el numero de pedido Fabrica que sera marcado como recibido.
	 */
	public void marcarPedidoRecibido(int nroPedido, Date fechaPedido)throws SQLException{

	
		//Marcar el pedido  como Recibido en memoria.
		this.pedido_recibido=true;
		
		//Luego por cada pedidoPersonal en el pedidoFabrica, se setea la fecha de recepcion de los
		//pedidos personal.
		for (int i = 0; i < pedidosPers.size(); i++) {
			pedidosPers.get(i).setFecha_recepcion(fechaPedido);
		}
		
		
		//Marcar el pedido ingresado como "Recibido" en la Base de Datos
		
		PedidoFabricaBD [] pedFab=this.getEm().find(PedidoFabricaBD.class,"numeropedido=?",nroPedido);
		
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
				
			}
			
			//FIN TODO MODIFICADO RODRIGO.
		}else{
			throw new SQLException();
		}
	
	}
	/**
	 * estaRecibido
	 * Devuelve el estado de un pedido de fabrica, indicando si este esta recibido o no.
	 * @return: el estado del pedido de fabrica.
	 */
	public boolean estaRecibido(){
		return this.pedido_recibido;
	}
	/**
	 * getPedidos
	 * Retorna una coleccion de los pedidos de personal asociados con el pedido de fabrica
	 * @return: una coleccion de pedidos de personal.
	 */
	public ArrayList<PedidoPersonal> getPedidos() {
		return pedidosPers;
	}
 
	/**
	 * total
	 * Realiza el calculo del total del pedido de fabrica, en base a los totales de 
	 * los pedidos de personal. 
	 */
	@Override
	public float total() {
		 float total=0;
		 for (PedidoPersonal ped : pedidosPers) {
			total+=ped.total();
		}
		 return total;
	}

}
