package negocio;

import java.sql.SQLException;

import persistencia.DetallePedidoPersonalBD;
import persistencia.PedidoPersonalBD;
import persistencia.ProductoBD;
import persistencia.TomoBD;
import net.java.ao.EntityManager;

public class DetallePedidoPersonal {

	
	private EntityManager em;
	private int cantidad;
	private Producto prod;
	private String talle;
	private String color;
	public DetallePedidoPersonal(EntityManager em, Producto prod){
		this.em=em;
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

	public void crearDetalle(PedidoPersonalBD pedido, DetallePedidoPersonal detallePedidoPersonal) {

		DetallePedidoPersonalBD detallePedidoBD= null;
		
		try {
			detallePedidoBD=em.create(DetallePedidoPersonalBD.class);
		} catch (SQLException e) {
			System.out.println("Error al crear detalle de pedido personal");
			e.printStackTrace();
		}
		if (detallePedidoBD!= null){
			//deberia aqui actualizar la cantidad en stock de los productos
			
			
		
			 
			//busco el producto en base para setearlo
			ProductoBD[] prod =null;
			try {
				prod= em.find(ProductoBD.class, "codigo =?", detallePedidoPersonal.getProd().getCodigo());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (prod!= null){
				detallePedidoBD.setCantidad(detallePedidoPersonal.getCantidad());
				detallePedidoBD.setColor(detallePedidoPersonal.getColor());
				//calculo el precio por la cantidad del producto
				
				detallePedidoBD.setPrecio(detallePedidoPersonal.getCantidad()*detallePedidoPersonal.getProd().getPrecio());
				
				detallePedidoBD.setPedidoPersonalBD(pedido);
				detallePedidoBD.setProductoBD(prod[0]);
				
				detallePedidoBD.save();
				
				//Actualizo el producto en base de datos, debería avisarle a Candela
				
				prod[0].setCantidadEnStock(prod[0].getCantidadEnStock()-detallePedidoPersonal.getCantidad());
				
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
