package utilidades;

import java.sql.SQLException;

import net.java.ao.EntityManager;

import persistencia.*;

public class GeneradorBD {

	private String url;
	private String usuario;
	private String pass;
	public GeneradorBD(String url, String usuario, String pass){
		this.url = url;
		this.usuario = usuario;
		this.pass = pass;
		
	}
	public static void main(String[] args) {
		GeneradorBD gb= new GeneradorBD(Constantes.URL, Constantes.USUARIO, Constantes.PASS);
		gb.generarEsquema();
	}
	public void generarEsquema(){
		EntityManager em= new EntityManager(url,usuario,pass);
		
		try {
			em.migrate(usuarioBD.class,TipoDeUsrBD.class,TomoBD.class,CatalogoBD.class,ProductoBD.class,TomoBD.class,DetallePedidoPersonalBD.class,
					FacturaFabricaBD.class,FacturaPersonalBD.class,PedidoFabricaBD.class,PedidoPersonalBD.class	);
		} catch (SQLException e) {
			System.out.println("ocurrio error al generar el esquema de BD \n"); 
			e.printStackTrace();
		}
		System.out.println(" Se genero correctamente esquema \n");
	}



}


