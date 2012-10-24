package storedProcedures;
import java.sql.SQLException;

import net.java.ao.EntityManager;

import persistencia.CatalogoBD;
import persistencia.ProductoBD;
import persistencia.TomoBD;
import utilidades.Constantes;
public class CatalogoSP {


	//TODO BORRAR ESTE METODO DE PRUEBA
	public static String holaMundo(int anioCat){

		EntityManager em= new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);

		CatalogoBD [] cats=null;
		try {
			cats= em.find(CatalogoBD.class,"aniovigencia=?",anioCat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "El numero de mi catalogo es"+cats[0].getAnioVigencia();
	}

	public static void altaTomo(int codigo, String descripcion, boolean esCatalogoNuevo, int anioVigencia) throws SQLException{


		EntityManager em= new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);


		//2.Se crea el tomo en la BD
		TomoBD tomoNuevo=null;
		tomoNuevo = em.create(TomoBD.class);

		tomoNuevo.setCodigoTomo(codigo);
		tomoNuevo.setDescripcion(descripcion);
		tomoNuevo.save();

		//3.Se asigna el tomo al catalogo en BD. Para ello se detecta si es un catalogoNuevo o Viejo.
		//Si es nuevo se crea un nuevo catalogo en la BD. Sino se busca el ultimo catalogo en la BD.
		CatalogoBD[] cat1= new CatalogoBD[1];
		if(esCatalogoNuevo==true){

			cat1[0]=em.create(CatalogoBD.class);																	
			cat1[0].setAnioVigencia(anioVigencia);
			cat1[0].save();
		}else{
			//Defino una var temporal catalogoBD para recuperar el ID del catalogoVigente
			CatalogoBD catTemp[]= em.find(CatalogoBD.class);
			int COD_CAT_VIGENTE= catTemp[catTemp.length-1].getID();

			cat1=em.find(CatalogoBD.class,"id=?", COD_CAT_VIGENTE);

		}
		tomoNuevo.setCatalogoBD(cat1[0]);
		tomoNuevo.save();			

	}

	public static void bajaTomo(int codigo) throws SQLException{
		EntityManager em= new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);

		//2. Luego  en Baja de Tomo hay que buscar  el tomo en la BD y setearle null a su catalogo.

		TomoBD [] tom1=null;
		//Buscar el tomo
		tom1= em.find(TomoBD.class,"codigoTomo =?",codigo);
		
		//Eliminar los productos asociados al tomo
		ProductoBD [] productostom= tom1[0].getProductos();
		for (int i = 0; i < productostom.length; i++) {
			productostom[i].setTomoBD(null);
			productostom[i].save();
		}
		tom1[0].setCatalogoBD(null); 
		tom1[0].save();

		//Borro el tomo
		em.delete(tom1[0]);



	}



}
