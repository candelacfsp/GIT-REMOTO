package negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import persistencia.usuarioBD;

public class Main {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		//Prueba de venta en local

		Candela candela=new Candela();
		
		candela.iniciar();
		
		ArrayList<DetallePedidoPersonal> colDetalles=new ArrayList<DetallePedidoPersonal>();
		
		//Deberia sacar los productos actualizarlos
		for (int i = 0; i < candela.getColProductos().size(); i++) {
			if (candela.getColProductos().get(i).getCantidadEnStock() >5){
				Producto prod = new Producto(candela.getEm());
				prod= candela.getColProductos().get(i);
				DetallePedidoPersonal detalle= new DetallePedidoPersonal(candela.getEm(),prod );
				detalle.setColor("VERDE");
				detalle.setTalle("X");
				detalle.setCantidad(2);
				colDetalles.add(detalle);
			}
					
		}
		for (int i = 0; i < candela.getColUsuarios().size(); i++) {
			if (candela.getColUsuarios().get(i).getNombreUsr().equals("Damian")){
				candela.ventaEnLocal(candela.getColUsuarios().get(i), colDetalles);
			}
			
		}
						
	}

}
