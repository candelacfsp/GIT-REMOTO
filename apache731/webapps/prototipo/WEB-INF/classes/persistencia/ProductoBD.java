package persistencia;

import net.java.ao.Entity;

public interface ProductoBD extends Entity {
	
	
	public void setDescripcion(String descripcion);
	public String getDescripcion();
	
	public int getCantidadEnStock();
	public void setCantidadEnStock(int cantidadEnStock);
	
	public void setPrecio(double precio);
	public double getPrecio();
	
	public void setCodigo(int codigo);
	public int getCodigo();
	
	
	public void setTomoBD(TomoBD tomoBD);
	public int getTomoBD();
	
	public void setTipoDeProductoBD(TipoDeProductoBD tipoDeProductoBD);
	
	public TipoDeProductoBD getTipoDeProductoBD();
	
	

}