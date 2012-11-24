package utilidades;

import java.sql.SQLException;

import negocio.Candela;

public class Consumidor extends Thread {
	long time_start, time_end;
	Candela candela;
	public Consumidor (Candela candela){
		this.candela= candela;
	}
	
	public void run(){
		
		GeneradorXML xml = new GeneradorXML(candela);
		for (int i = 0; i < 100; i++) {
			time_start = System.currentTimeMillis();
			System.out.println("saturador "+i);
			xml.generarProductosNoAsociados(0);
			xml.generarXMLFacturasFabrica(0);
			xml.generarTomosVigentes(0);
			time_end = System.currentTimeMillis();
			System.out.println("Termine de consumir");
			System.out.println("Consumidor "+i+"termino en"+ (time_end - time_start)/1000 +" segundos");
		}

	}

}
