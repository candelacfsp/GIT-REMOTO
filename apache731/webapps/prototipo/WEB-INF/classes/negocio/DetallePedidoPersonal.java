package negocio;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import persistencia.DetallePedidoPersonalBD;
import persistencia.PedidoPersonalBD;
import persistencia.ProductoBD;
import persistencia.TomoBD;
import utilidades.Constantes;
import net.java.ao.EntityManager;

public class DetallePedidoPersonal {
	private Connection conn=null;
	private int cantidad;
	private Producto prod;
	private String talle;
	private String color;
	//Este precio es el precio calculado que figura ademas en BD, y que es: cantidad*precioProducto.
	private double precio;//TODO RODRIGO 15-11-2012
	
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
	//TODO RODRIGO 15-11-2012
	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	//colProductos: Coleccion de productos de Candela.
	public void crearDetalle(PedidoPersonal pedido, ArrayList<Producto> colProductos) throws SQLException {
	
		/*
		//Se guarda el detalle en la BD
		CallableStatement sentencia=null;
		sentencia= conn.prepareCall("{call creardetalle(?,?,?,?,?)}");
		//Seteo los argumentos de la funcion.
		sentencia.setInt(1, this.getProd().getCodigo());
		sentencia.setInt(2, this.getCantidad());
		sentencia.setString(3,this.getColor());
		sentencia.setDouble(4, this.getProd().getPrecio());
		sentencia.setInt(5, pedido.getNumeroPedido());
		sentencia.execute();*/
		
		
		EntityManager em= new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);
		
		DetallePedidoPersonalBD detallePedidoBD= null;

		try {
			detallePedidoBD=em.create(DetallePedidoPersonalBD.class);
			
		} catch (SQLException e) {
			System.out.println("Error al crear detalle de pedido personal");
			e.printStackTrace();
		}
		if (detallePedidoBD!= null){
			System.out.println("El id de pedidoPersonalSP es: "+detallePedidoBD.getID());
			//busco el producto en base para setearlo
			ProductoBD[] prod =null;
			try {
				prod= em.find(ProductoBD.class, "codigo =?", this.getProd().getCodigo());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (prod!= null){
				detallePedidoBD.setCantidad(cantidad);
				detallePedidoBD.setColor(color);
				detallePedidoBD.setTalle(talle);
				//calculo el precio por la cantidad del producto
				detallePedidoBD.setPrecio(cantidad*prod[0].getPrecio());

				//Hacer la busqueda del pedido
				PedidoPersonalBD ped[]=null;
				ped=em.find(PedidoPersonalBD.class,"numeroPedido=?",pedido.getNumeroPedido());
				detallePedidoBD.setPedidoPersonalBD(ped[0]);
				detallePedidoBD.setProductoBD(prod[0]);
				detallePedidoBD.save();

				//Actualizo el producto en base de datos, debería avisarle a Candela
				prod[0].setCantidadEnStock(prod[0].getCantidadEnStock()-cantidad);
				prod[0].save();



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
