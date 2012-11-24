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
	
	//TODO MODIFICADO RODRIGO 22-11-2012
	//EL codigo de factura se usa para que el usuario identifique la factura
	//que llego de fabrica. En cambio el numero es usado por el sistema internamente
	//para gestionar la carga de las facturas.
	
	private int codigofactura;
	
	
	
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
	 public FacturaFabrica(Connection em,PedidoFabrica ped, int numero, int tipo, Date fecha,int codigofactura){
		 //Constructor que por defecto crea una factura fabrica impaga. Se usa en CU pagoAFabrica
		  super(em,numero,tipo,fecha);
		 this.pagada=false;
		 this.pedidofab=ped;
		 this.codigofactura=codigofactura;
	 }
	 
	 public FacturaFabrica(Connection em,PedidoFabrica ped, int numero, int tipo, Date fecha,boolean pagada,int codigofactura){
		 //Constructor que por defecto crea una factura fabrica impaga. Se usa al cargar las facturas Fabrica en la coleccion de facts de Candela.
		 super(em,numero,tipo,fecha);
		 this.pagada=pagada;
		 this.pedidofab=ped;
		 this.codigofactura=codigofactura;
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
	
	//TODO MODIFICADO RODRIGO 22-11-2012
	//EL codigo de factura se usa para que el usuario identifique la factura
	//que llego de fabrica. En cambio el numero es usado por el sistema internamente
	//para gestionar la carga de las facturas.
	
	public int getCodigo() {
		
		return this.codigofactura;
	}
	
	public void setCodigo(int cod){
		
		this.codigofactura=cod;
	}
	 
}
