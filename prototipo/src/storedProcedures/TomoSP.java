package storedProcedures;

import java.sql.SQLException;

import net.java.ao.EntityManager;
import persistencia.ProductoBD;
import persistencia.TomoBD;
import utilidades.Constantes;

public class TomoSP {


	public static void AsignarProdTomo(int codDeProd,int codigoDeTomo,String descripcion) throws SQLException{

		EntityManager em= new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);

		//1.Leer el Producto de la BD
		ProductoBD prods[]=null;
		prods=em.find(ProductoBD.class,"codigo= ?",codDeProd);


		//2. Leer el tomo de la BD			

		TomoBD[] tomo=null;
		tomo = em.find(TomoBD.class,"codigotomo= ?",codigoDeTomo);


		//3.Asignar el Producto persistente al tomo
		if (tomo.length>0){ //Si es un alta tomo con un catVigente
			prods[0].setTomoBD(tomo[0]);

		}else{ //SI es un alta Catalogo, y se pasa un nuevo tomo
			TomoBD tomo1= em.create(TomoBD.class);
			tomo1.setCodigoTomo(codigoDeTomo);
			tomo1.setDescripcion(descripcion);
			tomo1.save();
			prods[0].setTomoBD(tomo1);
		}
		prods[0].save();

	}


	public static void desasignarProdTomo(int codDeProd, int codDeTomo) throws SQLException{
		EntityManager em= new EntityManager(Constantes.URL,Constantes.USUARIO,Constantes.PASS);

		// Buscar el ProductoBD y el tomoBD y desasignar el producto del tomoBD
		ProductoBD [] prods=null;
		prods= em.find(ProductoBD.class,"codigo = ?",codDeProd);
		prods[0].setTomoBD(null); //Seteo el tomoBD de producto en NULL
		prods[0].save();

	}



}
