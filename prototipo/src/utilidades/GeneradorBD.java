package utilidades;

import java.sql.SQLException;

import net.java.ao.EntityManager;

import persistencia.*;
 
public class GeneradorBD {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
					
			EntityManager em= new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);
			
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


