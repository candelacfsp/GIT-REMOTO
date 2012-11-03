package persistencia;

import java.util.Date;

import net.java.ao.Entity;
import net.java.ao.OneToMany;

public interface CatalogoBD extends Entity{
	
	public int getAnioVigencia();
	public void setAnioVigencia(int anio);
	
	@OneToMany
	
	public TomoBD [] getColTomos();
	

}
