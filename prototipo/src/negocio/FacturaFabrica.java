package negocio;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

import net.java.ao.EntityManager;
/***
 * Esta clase contiene la informacion relacionada con las facturas a f�brica, asi como la referencia a su
 * pedido de f�brica correspondiente.
 * @see Factura
 * @author "Huincalef Rodrigo-Mansilla Damian"
 *
 */

public class FacturaFabrica extends Factura{

	private boolean pagada;
	private PedidoFabrica pedidofab;
	
	/**
	 * Constructor FacturaFabrica
	 * Inicializa la Factura a fabrica con el pedido a fabrica correspondiente, el numero de factura, el tipo y
	 * la fecha de creacion de la factura, ademas de su correspondiente manejador de entidades.
	 * @param em: manejador de entidades empleado para manipular la escritura a la base de datos.
	 * @param ped: n�mero de pedido a fabrica al que referecia la factura a Fabrica.
	 * @param numero: n�mero de factura a fabrica.
	 * @param tipo: tipo de la factura fabrica.
	 * @param fecha: fecha de creacion de la factura a f�brica.
	 */
	 public FacturaFabrica(Connection em,PedidoFabrica ped, int numero, int tipo, Date fecha){
		 super(em,numero,tipo,fecha);
		 this.pagada=false;
		 this.pedidofab=ped;
	 }

	 /**
	  * getPedidofab
	  * Obtiene el pedido f�brica correspondiente a la factura de fabrica.
	  * @return: la referencia al pedido fabrica que corresponde con la factura f�brica.
	  */
	public PedidoFabrica getPedidofab() {
		return pedidofab;
	}

	@Override
	/**
	 * total
	 * Este metodo calcula el importe total de la factura a fabrica.
	 * @return: un valor real que representa el importe de la factura.
	 */
	public float total() {
		return pedidofab.total();
	}
	/**
	 * setPagada
	 * Este metodo cambia el estado de la factura a f�brica.
	 * @param pagada: estado de la factura a fabrica. Verdadero para indicar que esta pagada o Falso,
	 * para indicar que todavia no ha sido abonada.
	 */
	public void setPagada(boolean pagada) {
		this.pagada = pagada;
	}
	/**
	 * esPagada
	 * Este metodo indica si una factura a f�brica esta pagada o no, retornando su estado.
	 * @return: un estado indicando si la factura a fabrica esta pagada o no.
	 */
	public boolean esPagada(){
		return this.pagada;
		
	}
	 
}
