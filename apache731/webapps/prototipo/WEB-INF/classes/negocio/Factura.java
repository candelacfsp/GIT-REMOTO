package negocio;

import java.util.Date;

import net.java.ao.EntityManager;

public abstract class Factura {
	
	private EntityManager em;
	private int numero;
	private int tipo;
	private Date fecha;
	
	public Factura(EntityManager em, int numero, int tipo, Date fecha){
		this.em=em;
		this.numero=numero;
		this.tipo=tipo;
		this.fecha=fecha;
	}
	
	 
	
	public EntityManager getEm() {
		return em;
	}



	public void setEm(EntityManager em) {
		this.em = em;
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
