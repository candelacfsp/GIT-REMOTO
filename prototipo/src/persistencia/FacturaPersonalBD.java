package persistencia;

import java.util.Date;

import net.java.ao.Entity;
import net.java.ao.OneToMany;
import net.java.ao.OneToOne;

public interface FacturaPersonalBD extends Entity {
	
	
	public int getTipo();
	
	public void setTipo(int tipo);
	
	public int getNumero();
	
	public void setNumero(int numero);
	
	public Date getFecha();
	
	public void setFecha(Date fecha);
	
	public void setPagada(boolean pagada);
	public boolean getPagada();
		
	public void setUsuario(usuarioBD usuario);
	
	public void setPedidoPersonalBD(PedidoPersonalBD ped);	
	public PedidoPersonalBD getPedidoPersonalBD();
	

}
