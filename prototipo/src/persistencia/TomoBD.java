package persistencia;

import net.java.ao.Entity;
import net.java.ao.OneToMany;

public interface TomoBD extends Entity{
	
	
	public int getCodigoTomo();
	public void setCodigoTomo(int codigoTomo);
	
	public String getDescripcion();
	public void setDescripcion(String descripcion);
	
	
	public void setCatalogoBD(CatalogoBD catalogoBD);
	
	@OneToMany
	public ProductoBD [] getProductos();
}
