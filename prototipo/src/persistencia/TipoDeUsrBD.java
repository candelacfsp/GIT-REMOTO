package persistencia;

import java.util.ArrayList;

import net.java.ao.Entity;
import net.java.ao.OneToMany;

public interface TipoDeUsrBD extends Entity {

	
	@OneToMany
	public usuarioBD [] getColUsuarioBD();
	
	//TODO MODIFICADO RODRIGO
	
	public void setnroTipoUsr(int nroTipoUsr);
	public int getnroTipoUsr();
	
}
