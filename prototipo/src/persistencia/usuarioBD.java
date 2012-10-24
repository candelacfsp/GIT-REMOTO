package persistencia;

import java.util.Date;

import net.java.ao.Entity;
import net.java.ao.OneToMany;
import net.java.ao.OneToOne;

public interface usuarioBD extends Entity{

	public void setNombreUsr(String nombreUsr);
	
	public void setContrasenia (String contrasenia);
	
	public String getNombreUsr();
	
	public String getContrasenia();
	
	public void setNombre(String nombre);
	
	public void setApellido(String apellido);
	
	
	public void setDni(int dni);
	
	public void setFecha_nacimiento(Date fecha_nacimiento);
	
	public String getNombre();
	
	public String getApellido();
	
	public int getDni();
	
	public Date getFecha_nacimiento();


	public void setTipoDeUsrBD(TipoDeUsrBD tipoDeUsrBD);
	
	public TipoDeUsrBD getTipoDeUsrBD();
	
	@OneToMany
	
	public FacturaPersonalBD [] getColFacturas();
	
	
}
