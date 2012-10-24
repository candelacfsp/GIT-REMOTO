package negocio;

import java.sql.Connection;
import java.util.Date;



public abstract class Factura {
	
	private Connection conexion;
	private int numero;
	private int tipo;
	private Date fecha;
	
	public Factura(Connection conexion, int numero, int tipo, Date fecha){
		this.conexion=conexion;
		this.numero=numero;
		this.tipo=tipo;
		this.fecha=fecha;
	}
	
	 
	
	public Connection getEm() {
		return conexion;
	}



	public void setEm(Connection em) {
		this.conexion = em;
	}



	public int getNumero() {
		return numero;
	}



	public void setNumero(int numero) {
		this.numero = numero;
	}



	public int getTipo() {
		return tipo;
	}



	public void setTipo(int tipo) {
		this.tipo = tipo;
	}



	public Date getFecha() {
		return fecha;
	}



	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}



	public abstract float total();	

}
