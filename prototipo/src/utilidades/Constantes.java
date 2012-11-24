package utilidades;

public class Constantes {

	public final static int VENDEDOR = 1;
	
	public final static int DIRECTOR = 2;
	
	public static final int EJECUTIVO = 3;
	
	public final static int OPERADORDEDATOS = 4;
	
	public final static String URL = "jdbc:postgresql://localhost/prototipo";
	
	public final static String USUARIO = "postgres";
	
	public final static String PASS = "postgres";

	public final static int ERROR = -1;
	
	public final static int VERDADERO=0;
	
	public final static int FALSO=1;
	
	//Constantes Alta de Tomo
	
	public final static int ALTA_TOMO_OK=58;
	
	//Constantes de Tomo

	public static final int PRODUCTO_ASIGNADO=52;
	
	public static final int ERROR_ASIGNAR_PRODUCTO_BD=51;
	
	public static final int TOMO_VIGENTE_ACTUALIZADO=50;
	
	
	//Desasignar Producto de Tomo
	
	public static final int PRODUCTO_DESASIGNADO_OK=53;
	
	public static final int PRODUCTO_NO_EXISTE=54;
	
	public static final int PRODUCTO_NO_ESTA_ASIGNADO=55;
	
	//Baja de Tomo
	
	public static final int TOMO_DADO_BAJA_OK=56;
	
	public static final int ERROR_BAJA_TOMO=57;
	
	
	//Excepcion SQL
	
	public static final int EXCEPCION_SQL=1;
	
	//Formato decimal del ranking de vendedores
	public static final int MAXIMO_CARACTERES=7;
	
	public static final int CORRIMIENTO_DECIMAL=100;
	
}
