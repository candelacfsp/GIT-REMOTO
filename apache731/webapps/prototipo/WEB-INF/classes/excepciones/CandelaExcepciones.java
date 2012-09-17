package excepciones;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class CandelaExcepciones extends Exception {
//TODO rodrigo le puso Throweable
	public void mensajeDialogo(String mensaje){
		
		JOptionPane.showMessageDialog(null, "Error:"+mensaje);
	}
}
