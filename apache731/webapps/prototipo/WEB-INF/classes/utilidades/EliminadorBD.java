package utilidades;
 

import java.sql.SQLException;

import net.java.ao.EntityManager;
import net.java.ao.Query;
import net.java.ao.RawEntity;
import persistencia.*;


public class EliminadorBD {
	

	public static void main(String[] args) {
		
		EliminadorBD elim1= new EliminadorBD();
		elim1.eliminarProductoBD();
		elim1.eliminarProductoBD();
		elim1.eliminarTomoBD();
		elim1.eliminarCatalogoBD(); 
		/*elim1.eliminarUsuarioBD();
		elim1.eliminarTipoDeUsr();

		System.out.println("Base de datos borrada correctamente"); */
		elim1.dropSchemaBD();
		
		
		
	
	}
	
	public void dropSchemaBD(){
		
		EntityManager em= new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);
		try {
			em.migrate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			System.out.println("Error al borrar el esquema de BD");
			System.exit(1);
		}
		
		System.out.println("Esquema de BD borrado Correctamente!");
	}
	
	
	
	public void eliminarCatalogoBD(){
		EntityManager em = new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);
		
		
		CatalogoBD prods[]=null;
		try {
			prods = em.find(CatalogoBD.class);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Error al encontrar todos los Catalogos!!");
			System.exit(1);
		}
		
		for (int i = 0; i < prods.length; i++) {
			try {
				em.delete(prods[i]);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error al eliminar Catalogo de la BD!");
				System.exit(1);
			}
		}
			System.out.println("Se borro ProductoBD exitosamente!");
		
	}
	
	public void eliminarTipoDeUsr(){
		EntityManager em = new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);
		
		TipoDeUsrBD prods[]=null;
		try {
			prods = em.find(TipoDeUsrBD.class);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Error al encontrar todos los TiposDeUsrBD!!");
			System.exit(1);
		}
		
		for (int i = 0; i < prods.length; i++) {
			try {
				em.delete(prods[i]);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error al eliminar TipoDeUsrBD de la BD!");
				System.exit(1);
			}
		}
			System.out.println("Se borro TipoDeUsrBD exitosamente!");
		
	}
	public void eliminarTomoBD(){
		EntityManager em = new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);
		
		
		TomoBD prods[]=null;
		try {
			prods = em.find(TomoBD.class);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Error al encontrar todos los TOMOS!!");
			System.exit(1);
		}
		
		for (int i = 0; i < prods.length; i++) {
			try {
				em.delete(prods[i]);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error al eliminar Tomos de la BD!");
				System.exit(1);
			}
		}
			System.out.println("Se borro TomoBD exitosamente!");
		
	}
	public void eliminarUsuarioBD(){
		EntityManager em = new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);
		
		
		usuarioBD prods[]=null;
		try {
			prods = em.find(usuarioBD.class);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Error al encontrar todos los usuariosBD!!");
			System.exit(1);
		}
		
		for (int i = 0; i < prods.length; i++) {
			try {
				em.delete(prods[i]);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error al eliminar usuarios de la BD!");
				System.exit(1);
			}
		}
			System.out.println("Se borro usuarioBD exitosamente!");
		
	}
	
	
	
	public void eliminarProductoBD(){
		EntityManager em = new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);
		
		
		ProductoBD prods[]=null;
		try {
			prods = em.find(ProductoBD.class);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Error al encontrar todos los productos!!");
			System.exit(1);
		}
		
		for (int i = 0; i < prods.length; i++) {
			try {
				em.delete(prods[i]);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error al eliminar productos de la BD!");
				System.exit(1);
			}
		}
			System.out.println("Se borro ProductoBD exitosamente!");
		
	}
	

}
