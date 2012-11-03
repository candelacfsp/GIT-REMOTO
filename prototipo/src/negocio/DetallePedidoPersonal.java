package negocio;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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

	public void crearDetalle(PedidoPersonal pedido, ArrayList<Producto> colProductos) throws SQLException {

		
		//Se guarda el detalle en la BD
		CallableStatement sentencia=null;

		sentencia= conn.prepareCall("{call creardetalle(?,?,?,?,?)}");

		//Seteo los argumentos de la funcion.
		sentencia.setInt(1, this.getProd().getCodigo());
		sentencia.setInt(2, this.getCantidad());
		sentencia.setString(3,this.getColor());
		sentencia.setDouble(4, this.getProd().getPrecio());
		sentencia.setInt(5, pedido.getNumeroPedido());
		//Seteo parametro de salida

		//sentencia.registerOutParameter(1, java.sql.Types.VARCHAR);


		sentencia.execute();
		
		//Se actualiza la cantidad de cada producto en stock utilizando la coleccion de Candela
		
		for (int i = 0; i < colProductos.size(); i++) {
			if(colProductos.get(i).getCodigo()== this.getProd().getCodigo()){
				colProductos.get(i).setCantidadEnStock(colProductos.get(i).getCantidadEnStock()-this.cantidad);
			}
		}

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
