package persistencia;

import net.java.ao.Entity;
import net.java.ao.OneToMany;

public interface TipoDeProductoBD extends Entity {
	
	public int getCodTipoProd();
	
	public String getDescripcion();
	
	public void setCodTipoProd(int CodTipoProd);
	
	public void setDescripcion(String descripcion);
	

	
	@OneToMany
	public ProductoBD [] getColProductos();
	
	

}
