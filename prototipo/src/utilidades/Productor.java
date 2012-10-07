package utilidades;

import java.sql.SQLException;

import negocio.Candela;

public class Productor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Candela candela = new Candela();
		
		try {
			candela.iniciar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Consumidor consumidor= new Consumidor(candela);
		consumidor.start();

	}

}
