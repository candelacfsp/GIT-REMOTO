import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.xml.internal.ws.wsdl.writer.document.Types;


public class Cliente {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int anioVig=2011;
		
		String URL = "jdbc:postgresql://localhost:5432/prototipo";
		String user="postgres";
		String password="postgres";
		Connection conn=null;
		CallableStatement sentencia=null;
		try {
			conn = DriverManager.getConnection(URL, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			 sentencia= conn.prepareCall("{call holamundo(?)}");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Seteo los argumentos de la funcion.
		try {
			sentencia.setInt(1, anioVig);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Seteo parametro de salida
		
		try {
			sentencia.registerOutParameter(1, java.sql.Types.VARCHAR);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//Character Varying
	
		
		try {
			sentencia.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String resultado=null;
		try {
			resultado= sentencia.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(resultado==null){
			System.out.println("EL anio ingresado no existe :( ");
		}else{
			System.out.println(resultado);
		}
		
	}

}
