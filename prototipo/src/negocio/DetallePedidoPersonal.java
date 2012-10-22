package negocio;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import persistencia.DetallePedidoPersonalBD;
import persistencia.PedidoPersonalBD;
import persistencia.ProductoBD;
import persistencia.TomoBD;
import net.java.ao.EntityManager;

public class DetallePedidoPersonal {

	private Connection conn=null;
	private int cantidad;
	private Producto prod;
	private String talle;
	private String color;
	public DetallePedidoPersonal(Connection conn, Producto prod){
		this.conn=conn;
		this.prod=prod;

	}

	public Producto getProd() {
		return prod;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public float subtotal(){
		return (float) (prod.getPrecio()*cantidad);
	}

	public void crearDetalle(PedidoPersonalBD pedido, DetallePedidoPersonal detallePedidoPersonal) throws SQLException {

		CallableStatement sentencia=null;

		sentencia= conn.prepareCall("{call creardetalle(?,?,?,?,?)}");

		//Seteo los argumentos de la funcion.
		sentencia.setInt(1, detallePedidoPersonal.getProd().getCodigo());
		sentencia.setInt(2, detallePedidoPersonal.getCantidad());
		sentencia.setString(3,detallePedidoPersonal.getColor());
		sentencia.setDouble(4, detallePedidoPersonal.getProd().getPrecio());
		sentencia.setInt(5, pedido.getNumeroPedido());
		//Seteo parametro de salida

		//sentencia.registerOutParameter(1, java.sql.Types.VARCHAR);


		sentencia.execute();

	}



	public String getTalle() {
		return talle;
	}

	public void setTalle(String talle) {
		this.talle = talle;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
