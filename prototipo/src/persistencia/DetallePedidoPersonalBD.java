package persistencia;

import net.java.ao.Entity;
import net.java.ao.OneToOne;

public interface DetallePedidoPersonalBD extends Entity{

	public int getCantidad();
	public void setCantidad(int cantidad);
	
	
	//TODO: SACAR CAMPO PRECIO, que se obtiene por medio del producto con el que esta asociado
	//el detalle.
	public double getPrecio();
	public void setPrecio(double precio);
	
	public String getColor();
	public void setColor(String color);
	
	public String getTalle();
	public void setTalle(String talle);

	public void setPedidoPersonalBD(PedidoPersonalBD pedido);
	
	//NOTA: En las asociaciones 1-1 entre entidades, solamente 1 elemento debe ser el que añada al otro.
	//Si ambos se añaden mutuamente genera un error en tiempod e ejecucion: Error de Dependencia Circular.

	public void setProductoBD(ProductoBD prod);
	public ProductoBD getProductoBD();
}
