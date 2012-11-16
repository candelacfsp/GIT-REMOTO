package utilidades;

import java.sql.SQLException;

import negocio.Candela;

public class Productor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Candela candela = null;

		try {
			candela = new Candela();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


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
