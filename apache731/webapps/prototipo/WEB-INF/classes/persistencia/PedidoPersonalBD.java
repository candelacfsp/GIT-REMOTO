package persistencia;

import java.util.Date;

import net.java.ao.Entity;
import net.java.ao.OneToMany;

public interface PedidoPersonalBD extends Entity{

	
	public int getNumeroPedido();
	public  void setNumeroPedido(int numeroPedido);
	
	public Date getFechaEmision();
	public void setFechaEmision(Date fechaEmision);
	
	public Date getFechaRecepcion();
	public void setFechaRecepcion(Date fechaRecepcion);
	public void setPedidoFabricaBD(PedidoFabricaBD pedidoFabrica);
	
	@OneToMany
	public DetallePedidoPersonalBD [] getColDetallesPedidoPersonalBD();
	
	

	
}
