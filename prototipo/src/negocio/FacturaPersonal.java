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
import utilidades.Constantes;

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

		try {
			sentencia= super.getConexion().prepareCall("{call crearfactura(?,?,?,?,?,?)}");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//GregorianCalendar gc= (GregorianCalendar) GregorianCalendar.getInstance();
		//Date d2= gc.getTime();
		
		Date d2 = new Date();
		java.sql.Date d1= new java.sql.Date(d2.getYear(), d2.getMonth(), d2.getDate());
		
		//TODO REVISAR ESTE PROC. ALMACENADO.
	/*	
		//Seteo los argumentos de la funcion.
		try {
			sentencia.setDate(1, d1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sentencia.setInt(2, this.getNumero());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sentencia.setInt(3,this.getTipo());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sentencia.setInt(4, usuario.getDni());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sentencia.setBoolean(5, this.pagada);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sentencia.setInt(6, nroPedido);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Seteo parametro de salida

		//sentencia.registerOutParameter(1, java.sql.Types.VARCHAR);
		
		
		try {
			sentencia.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//FIN DE PROC-. ALMACENADO
		
		//INICIO TEST ENTITYMANAGER
		
		EntityManager em= new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);

		FacturaPersonalBD facturaBD= null;
		facturaBD=em.create(FacturaPersonalBD.class);
		
		if (facturaBD != null){
			facturaBD.setFecha(d1);
			facturaBD.setNumero(this.getNumero());
			facturaBD.setTipo(this.getTipo());
			usuarioBD usr[]=null;
			usr=em.find(usuarioBD.class,"dni=?",usuario.getDni());
			
			facturaBD.setUsuario(usr[0]);
			facturaBD.setPagada(this.pagada);
			PedidoPersonalBD peds[]=null;
			peds= em.find(PedidoPersonalBD.class,"numeroPedido=?",nroPedido);
			
			facturaBD.setPedidoPersonalBD(peds[0]);
			facturaBD.save();
		}
		
		//FIN TEST
		 
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
