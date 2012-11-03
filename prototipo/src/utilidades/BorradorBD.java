package utilidades;

import java.sql.SQLException;

import net.java.ao.EntityManager;

public class BorradorBD {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		EntityManager em= new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);
		
		try {
				em.migrate(EntidadBorradoBD.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al borrar la BD");
			System.exit(Constantes.EXCEPCION_SQL);
		}
		
		System.out.println("Se borro exitosamente la BD!!!");
	}

}
