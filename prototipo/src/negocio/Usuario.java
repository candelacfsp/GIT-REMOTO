package negocio;

import java.util.*;
import java.sql.SQLException;

import java.util.ArrayList;

import persistencia.FacturaPersonalBD;
import persistencia.usuarioBD;

import utilidades.Constantes;
/**
 * Esta clase contiene los datos relacionados con los tipos de usuario. Esta clase realiza las altas, bajas y modificaciones
 * de los usuarios.
 * @author Huincalef Rodrigo-Damian Mansilla.
 *
 */
public class Usuario {

	private int dni;
	private String nombre=null;
	private String apellido=null;
	private Date fecha_nacimiento=null;
	private String nombreUsuario=null;
	private String contrasenia=null;
	private ArrayList<FacturaPersonal> facturaPers;



	//TODO: CORREGIR ESTO! Alta de usuario tiene que a�adir el usuario en la coleccion de usuarios del sistema.

	public Usuario(String nombreUsuario, String contrasenia, int dni,  String nombre, String apellido, Date fechanac,ArrayList<FacturaPersonal> fp){

		this.dni=dni;
		this.nombreUsuario=nombreUsuario;
		this.contrasenia=contrasenia;
		this.nombre=nombre;
		this.apellido=apellido;
		this.fecha_nacimiento=fechanac;
		this.facturaPers=fp;
	}
	/**
	 * altaUsuario
	 * Realiza el alta de un usuario en el sistema.
	 * @param nombreUsuario: nombre del usuario que se creara
	 * @param contrasenia: contrasenia del usuario.
	 * @param dni: numero de dni del usuario.
	 * @param nombre: nombre del usuario.
	 * @param apellido: apellido del usuario.
	 * @param fechanac: fecha de nacimiento del usuario.
	 */
	public void altaUsuario(String nombreUsuario, String contrasenia, int dni,  String nombre, String apellido, Date fechanac){

		this.dni=dni;
		this.nombreUsuario=nombreUsuario;
		this.contrasenia=contrasenia;
		this.nombre=nombre;
		this.apellido=apellido;
		this.fecha_nacimiento=fechanac;
		this.facturaPers=new ArrayList<FacturaPersonal>();

	}
	/**
	 * bajaUsuario
	 * Realiza la baja de un usuario en el sistema.
	 * @param nombreUsuario: nombre de usuario del usuario que se dara de baja.
	 */
	public void bajaUsuario(String nombreUsuario){

		System.out.println("haciendo baja de Usuario!!!\n");

	}

	/**
	 * modificarContrasenia
	 * Este metodo realiza una modificacion de la constrasenia de un usuario determinado.
	 * @param nombreUsuario: nombre de usuario del usuario al que se le modificara la prueba.
	 * @param contrasenia: constrasenia nueva del usuario.
	 */
	public void modificarContrasenia(String nombreUsuario, String contrasenia ){
		System.out.println("M0dificando contrasenia del usuario !!! \n");

	}
	/**
	 * getDni
	 * Obtiene el dni de un usuario.
	 * @return: un numero que representa el dni del usuario.
	 */
	public int getDni() {
		return dni;
	}


	/**
	 * Este metodo retorna una coleccion de facturas impagas asociadas al usuario.
	 * @return: Retorna una coleccion de facturas impagas asociadas con el usuario. Si el usaurio no tiene niguna
	 * factura imapaga retorna null o si no tiene facturas asociadas.
	 */
	public ArrayList<FacturaPersonal> obtenerFactImpagas(){


		ArrayList<FacturaPersonal> factsPersImp= new ArrayList<FacturaPersonal>();


		//Si existe, pedir las facturas asociadas al usuario, y buscar la factura que esta impaga.

		//FacturaPersonalBD [] facturasUsr=candela.getColUsuarios().get(posUsuario).getColFacturas();

		//Si el usuario tiene facturas asociadas se muestran solo las que estan impagas
		int posFacturaImpaga;
		if(facturaPers.size()>0){ //Si el usuario cuenta con facturas Impagas
			//Se guardan en una coleccion las facturas que estan impagas. La coleccion es recuperada desde la sesion de candela
			// y son mostradas por otro .jsp
			//	ArrayList<FacturaPersonalBD> facturasImpagasUsr=new ArrayList<FacturaPersonalBD>();

			for(posFacturaImpaga=0; posFacturaImpaga <facturaPers.size();posFacturaImpaga++){
				if(facturaPers.get(posFacturaImpaga).getPagada()==false){
					factsPersImp.add(facturaPers.get(posFacturaImpaga));
				}
			}


		}
		if(factsPersImp.size()<0){

			return null;
		}else{
			return factsPersImp;	
		}

	}

	/***
	 * Devuelve toda slas facturas asociadas a un usuario
	 * @param fin 
	 * @param inicio 
	 * @return
	 */
	public ArrayList<FacturaPersonal> ObtenerFacturas(Date inicio, Date fin){


		ArrayList<FacturaPersonal> factsPersImp= new ArrayList<FacturaPersonal>();


		//Si existe, pedir las facturas asociadas al usuario, y buscar la factura que esta impaga.

		//FacturaPersonalBD [] facturasUsr=candela.getColUsuarios().get(posUsuario).getColFacturas();

		//Si el usuario tiene facturas asociadas se muestran solo las que estan impagas
		int posFactura;
		if(facturaPers.size()>0){ //Si el usuario cuenta con facturas Impagas
			//Se guardan en una coleccion las facturas que estan impagas. La coleccion es recuperada desde la sesion de candela
			// y son mostradas por otro .jsp
			//	ArrayList<FacturaPersonalBD> facturasImpagasUsr=new ArrayList<FacturaPersonalBD>();

			for(posFactura=0; posFactura <facturaPers.size();posFactura++){
				//busco entre la fecha de inicio  y fin
				if (facturaPers.get(posFactura).getFecha().after(inicio) && facturaPers.get(posFactura).getFecha().before(fin)){
					factsPersImp.add(facturaPers.get(posFactura));
				}
			}


		}
		if(factsPersImp.size()<0){

			return null;
		}else{
			return factsPersImp;	
		}

	}

	/**
	 * Este metodo obtiene las facturas Impagas del usuario y verifica que el numero
	 * de factura sea valido.
	 * @return : Retorna la posicion de la facturaImpaga en la coleccion de facturas impagas del usuario en caso
	 * de que sea valida, o -1 si la factura no es valida.
	 */

	public int verificarFacturaImpaga(int nroFactImp){

		int resultado=Constantes.ERROR;
		ArrayList<FacturaPersonal> factsImpagas= this.obtenerFactImpagas();

		//ArrayList<FacturaPersonalBD> factsImpagas=(ArrayList<FacturaPersonalBD>)candela_sesion.getAttribute("colFacturaImpaga");
		int posFactImp=0;
		for(posFactImp=0; posFactImp<factsImpagas.size();posFactImp++){
			System.out.printf("Nro de factura %s : %d\n",posFactImp,factsImpagas.get(posFactImp).getNumero());
			if (nroFactImp==factsImpagas.get(posFactImp).getNumero()){
				resultado=posFactImp;
				break;
			}
		}

		return resultado;
	}

	/**
	 * Este metodo marca una factura Personal impaga, como paga en memoria principal y en base de datos.
	 * @param posFactImp: es la posicion de la factura dentro de la coleccion de facturas Impagas del usuario.
	 */
	public void usrMarcarFactImp(int posFactImp) throws SQLException{

		ArrayList<FacturaPersonal> factsImpagas= null;

		factsImpagas=this.obtenerFactImpagas();

		//Se marca la factura como paga en memoria y en BD
		try{

			factsImpagas.get(posFactImp).setPagada(true);	
			factsImpagas.get(posFactImp).almacenar_factImpaga(factsImpagas.get(posFactImp).getNumero());

		}catch(SQLException sql){
			//Si ocurre una excepcion SQL, se  tiene que desmarcar la factura impaga en memoria.
			factsImpagas.get(posFactImp).setPagada(false);
			throw new SQLException();
		}

		//Obtener el numero de factura desde la BD y Cambiar el estado de la facturasPersonal en memoria como "PAGA"
		//int nroFact= factsImpagas.get(posFactImp).getNumero();

		//ArrayList<FacturaPersonal> facturasMemoria=candela.getFacturasPersonal();
		//for(int i=0;i<facturasMemoria.size();i++){
		//if(facturasMemoria.get(i).getNumero()==nroFact){
		//facturasMemoria.get(i).setPagada(true);
		//}
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}
	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	public ArrayList<FacturaPersonal> getFacturaPers() {
		return facturaPers;
	}
	public void setFacturaPers(ArrayList<FacturaPersonal> facturaPers) {
		this.facturaPers = facturaPers;
	}
	public void setDni(int dni) {
		this.dni = dni;
	}





}
