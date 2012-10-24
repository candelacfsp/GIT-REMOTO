package persistencia;

import java.util.Date;

import net.java.ao.Entity;
import net.java.ao.OneToMany;

public interface PedidoFabricaBD extends Entity {

	
	public int getNumeroPedido();
	public void setNumeroPedido(int numeroPedido);
	
	public Date getFechaEmision();
	public void setFechaEmision(Date fechaEmision);
	
	public Date getFechaRecepcion();
	public void setFechaRecepcion(Date fechaRecepcion);
	
	//Recibido es el equivalente a "llego" en el diagrama ER
	public boolean getRecibido();
	public void setRecibido(boolean recibido); 
	
	@OneToMany
	public PedidoPersonalBD [] getColPedidoPersonalBD();
	
}
