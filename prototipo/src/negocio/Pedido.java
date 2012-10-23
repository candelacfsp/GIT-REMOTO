package negocio;

import java.sql.Connection;
import java.util.Date;

import net.java.ao.Entity;
import net.java.ao.EntityManager;
/**
 * Esta clase es una clase abstracta, de la que heredan sus atributos las clases: PedidoPersonal y 
 * PedidoFabrica.
 * @author Huincalef Rodrigo-Mansilla Damian
 *
 */
public  abstract class  Pedido {
	private int numeroPedido;
	private Date fecha_emision;
	private Date fecha_recepcion;
	private Connection conexion;
	
	/**
	 * Pedido
	 * Constructor de la clase pedido, que inicializa los campos de un pedido.
	 * @param em: manejador de entidades, encargado de realizar la manipulacion de la base de datos.
	 * @param numeroPedido: n�mero que identifica al pedido.
	 * @param fecha_emision: fecha de emisi�n del pedido.
	 * @param fecha_recepcion: fecha de recepci�n del pedido.
	 */
	public Pedido(Connection conexion,int numeroPedido, Date fecha_emision, Date fecha_recepcion){
		this.conexion=conexion;
		this.numeroPedido=numeroPedido;
		this.fecha_emision=fecha_emision;
		this.fecha_recepcion=fecha_recepcion;
	}
	
	/**
	 * getEm
	 * Retorna el manejador de entidades correspondiente a la clase Pedido.
	 * @return: EntityManager correspondiente a los Pedidos.
	 */
	public Connection getConnection(){
		return this.conexion;
	}
	
	/**
	 * getNumeroPedido
	 * Obtiene el numero del pedido.
	 * @return: un valor entero que identifica al pedido.
	 */
	public int getNumeroPedido() {
		return numeroPedido;
	}
	/**
	 * setNumeroPedido
	 * Establece el numero del pedido.
	 */
	public void setNumeroPedido(int numeroPedido) {
		this.numeroPedido = numeroPedido;
	}
	/**
	 * getFecha_emision
	 * Obtiene la fecha de emision del pedido.
	 * @return: un objeto fecha que corresponde a la fecha del pedido.
	 */
	public Date getFecha_emision() {
		return fecha_emision;
	}
	/**
	 * setFecha_emision
	 * Establece la fecha en que se emiti� el pedido.
	 */
	public void setFecha_emision(Date fecha_emision) {
		this.fecha_emision = fecha_emision;
	}
	/**
	 * getFecha_recepcion
	 * Obtiene la fecha de recepcion del pedido.
	 * @return: un objeto fecha que corresponde a la fecha de recepcion del pedido.
	 */
	public Date getFecha_recepcion() {
		return fecha_recepcion;
	}
	/**
	 * setFecha_recepcion
	 * Establece la fecha en que se marco un pedido como recibido.
	 */
	public void setFecha_recepcion(Date fecha_recepcion) {
		this.fecha_recepcion = fecha_recepcion;
	}
	/**
	 * total
	 * Metodo que permite calcular el total de un pedido.
	 */
	public abstract float total();
	
}
