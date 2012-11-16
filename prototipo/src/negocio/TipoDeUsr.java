package negocio;

import java.util.ArrayList;

public class TipoDeUsr {
	
	
	private int ID;
	private   ArrayList colUsr =null;
	
	public void asociarUsuario(String nombreUsr){
		
		System.out.printf(" Estoy asociando  usuario %s \n",nombreUsr);
	}
	
	public void desAsociarUsuario(String nombreUsr){
		
		System.out.printf(" Estoy desasociando usuario %s \n",nombreUsr);
	}
	

}
