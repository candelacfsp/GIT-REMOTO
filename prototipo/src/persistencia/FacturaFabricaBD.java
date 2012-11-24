package persistencia;

import java.util.Date;

import net.java.ao.Entity;
import net.java.ao.OneToMany;

public interface FacturaFabricaBD extends Entity {

	public int getTipo();
	public void setTipo(int tipo);
	
	public int getNumero();
	public void setNumero(int numero);
	
	public Date getFecha();
	public void setFecha(Date fecha);
	
	public void setPagada(boolean pagada);
	public boolean getPagada();
	
	//TODO MODIFICADO RODRIGO 22-11-2012
	public void setCodigoFactura(int codigofactura);
	public int getCodigoFactura();
	
	@OneToMany
	public PedidoPersonalBD [] getColPedidosPersonalBD();
	
	public void setPedidoFabricaBD(PedidoFabricaBD ped);
	public PedidoFabricaBD getPedidoFabricaBD();

}
