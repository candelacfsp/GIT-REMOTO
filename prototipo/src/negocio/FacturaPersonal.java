package negocio;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import persistencia.FacturaPersonalBD;
import persistencia.PedidoPersonalBD;
import persistencia.usuarioBD;

import net.java.ao.EntityManager;
/***
 * Esta clase controla la lógica relacionada exclusivamente con una factura personal.
 * @author "Huincalef Rodrigo-Mansilla Damian"
 *
 */
public class FacturaPersonal extends Factura {

	private boolean pagada;
	private PedidoPersonal pedidoPers;
	
	
	/**
	 * FacturaPersonal
	 * Inicializa los valores correspondiente a una de las facturas a personal.
	 * @param em: manejador de entidades empleado para lecturas y/o escrituras a la base de datos.
	 * @param pedPers: número de pedido personal, asociado con la factura personal.
	 * @param numero: número de factura de personal.
	 * @param tipo: tipo de factura personal.
	 * @param fecha: fecha de creacion de la factura personal.
	 * @param pagada: estado de la factura personal. 
	 */
	public FacturaPersonal(Connection conexion,PedidoPersonal pedPers, int numero, int tipo, Date fecha,boolean pagada ){
		super(conexion,numero,tipo,fecha);
		this.pedidoPers=pedPers;
		 
		this.pagada=pagada;
	}
	
	
	
 
	/**
	 * setPagada
	 * Este metodo setea el estado de la factura
	 * @param pagada: estado al que será setada la factura.
	 */
	public void setPagada(boolean pagada) {
		this.pagada = pagada;
	}



	@Override
	/**
	 * total
	 * Este metodo se encarga de realizar el calculo del total de una factura de personal.
	 */
	public float total() {
		 float total=0;
		 
		 ArrayList<DetallePedidoPersonal> detpers=pedidoPers.getDetalles();
		 for (DetallePedidoPersonal det1 : detpers) {
			total+=det1.getProd().getPrecio()*det1.getCantidad();
		}
		 return total;
	}





	public boolean getPagada() {
		
		return this.pagada;
	}

	public void crearFactura(FacturaPersonal factura, usuarioBD usuario,int nroPedido) throws SQLException{
		CallableStatement sentencia=null;

		sentencia= super.getConexion().prepareCall("{call crearfactura(?,?,?,?,?,?)}");

		//GregorianCalendar gc= (GregorianCalendar) GregorianCalendar.getInstance();
		//Date d2= gc.getTime();
		
		Date d2 = new Date();
		
		
		java.sql.Date d1= new java.sql.Date(d2.getYear(), d2.getMonth(), d2.getDate());
		
		//Seteo los argumentos de la funcion.
		sentencia.setDate(1, d1);
		sentencia.setInt(2, super.getNumero());
		sentencia.setInt(3,super.getTipo());
		sentencia.setInt(4, usuario.getDni());
		sentencia.setBoolean(5, this.getPagada());
		sentencia.setInt(6, nroPedido);

		//Seteo parametro de salida

		//sentencia.registerOutParameter(1, java.sql.Types.VARCHAR);
		
		
		sentencia.execute();
		//
		 
	}
	/**
	 * Este metodo registra el estado de una factura como pagada en la BD.
	 */
	public void almacenar_factImpaga(int nroFactImp)throws SQLException{
		//Se marca la factura en la BD como paga.
		CallableStatement sentencia=null;

		sentencia= this.getConexion().prepareCall("{call almacenar_factimp(?)}");

		//Seteo los argumentos de la funcion.
		sentencia.setInt(1, nroFactImp);
		
		sentencia.execute();
		 
	}
	public PedidoPersonal getPedidoPers(){
		return pedidoPers;
	}
	
}
