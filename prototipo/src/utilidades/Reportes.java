package utilidades;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import persistencia.DetallePedidoPersonalBD;
import persistencia.FacturaPersonalBD;
import persistencia.TipoDeUsrBD;
import persistencia.usuarioBD;

import negocio.Candela;
import negocio.DetallePedidoPersonal;
import negocio.FacturaPersonal;
import negocio.Producto;
import negocio.Tomo;
import negocio.Usuario;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class Reportes {
	public Reportes(){
		System.out.println("Reporte creado");
	}

	public String crearPDF(Candela candela) throws IOException{


		OutputStream out= new FileOutputStream("reportes.pdf");


		ArrayList<Producto> productos = candela.getColProductos();

		List datos = new ArrayList();
		for (int i = 0; i < productos.size(); i++) {
			datos.add(productos.get(i));
		}
		JasperReport reporte = null;
		try {
			reporte = (JasperReport)JRLoader.loadObject(candela.getDirectorio()+"reporteProductosBlue.jasper");
		} catch (JRException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}    
		JasperPrint jasperPrint = null;
		try {
			jasperPrint = JasperFillManager.fillReport(reporte, null, new JRBeanCollectionDataSource(datos));
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     

		JRExporter exporter = new JRPdfExporter();    
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);    
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(candela.getDirectorio()+"reporte.pdf"));     

		try {
			exporter.exportReport();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return candela.getDirectorio()+"reporte.pdf";

	}

	//Crea un ranking con los vendedores que facturaron un importe mayor.
	
	
	public String crearRankingVend(Candela candela) throws IOException{

		List datos= new ArrayList();
		
		
		OutputStream out= new FileOutputStream("reportes.pdf");
		ArrayList<tuplaVendedores> rankingVend = new ArrayList<tuplaVendedores>();
		//Se leen todos los usuarios que son vendedores.
		ArrayList<TipoDeUsrBD> tiposUsr=candela.getColTipoUsr();
		
		for (int i = 0; i < tiposUsr.size(); i++) {
			//SI el tipo de Usr es vendedor, entonces obtengo la coleccion de todos los vendedores.
			
			if(tiposUsr.get(i).getnroTipoUsr()==Constantes.VENDEDOR){
				
				usuarioBD [] vendedores= tiposUsr.get(i).getColUsuarioBD();	
				if(vendedores.length>0){//SI existen vendedores cargados
					//Se crea un arrayLIst de vendedores tentativos que son los que poseen al menos una factura pagada asociada
					
					ArrayList<usuarioBD> vendedoresTentativos=new ArrayList<usuarioBD>();
					for (int j = 0; j < vendedores.length; j++) {
							//Se obtiene de cada vendedor, las facturas que figuren como pagadas y 
							//se calcula el importe total facturado
							double importe=0;
							FacturaPersonalBD [] colfacts= vendedores[j].getColFacturas();
							for (int k = 0; k < colfacts.length; k++) {
								//Se obtienen los detalles del pedido asociado con la factura
								//Si esta pagada se suma el importe
								if(colfacts[k].getPagada()){
									DetallePedidoPersonalBD [] dets=colfacts[k].getPedidoPersonalBD().getColDetallesPedidoPersonalBD();
									for (int l = 0; l < dets.length; l++) {
										importe+=dets[l].getPrecio();
									}//FIn del for de calculo de importe
									
								}
							}//FIn del for que recorre las facturas
						
							if(importe>0){ //Si tiene un importe mayor a cero (tiene al menos una factura pagada asociada)
								//Se crea una tupla importe-nombre vendedor
								tuplaVendedores v1= new tuplaVendedores(importe, vendedores[j].getApellido()+ " "+vendedores[j].getNombre());
								
								//Se añade a la lista de vendedores la tupla del vendedor
								rankingVend.add(v1);
								
							}
						 
					}//Fin del for de los vendedores
					// se ordenan por importe
					//SI se tiene al menos un vendero agregado a la coleccion de vendedoresTentativos
					if(rankingVend.size()>0){
						java.util.Collections.sort(rankingVend);
						datos=rankingVend.subList(0, 9);
							
					}
					
				}
			}
			
		}// FIn de generacion de List datos
		
		String resultado=null;
		if(rankingVend.size()>0){//Si tengo al menos un vendedor para el ranking genero el reporte.
			
			JasperReport reporte = null;
			try {
				reporte = (JasperReport)JRLoader.loadObject(candela.getDirectorio()+"rankingVendedores.jasper");
			} catch (JRException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}    
			JasperPrint jasperPrint = null;
			try {
				jasperPrint = JasperFillManager.fillReport(reporte, null, new JRBeanCollectionDataSource(datos));
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}     
	
			JRExporter exporter = new JRPdfExporter();    
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);    
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(candela.getDirectorio()+"reporte.pdf"));     
	
			try {
				exporter.exportReport();
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//Fin del IF
		
		return resultado;

	}
	
	public class tuplaVendedores implements Comparable<tuplaVendedores>{
		
		double importe;
		String nombre;
		
		public tuplaVendedores(double imp, String nomb){
			importe=imp;
			nombre=nomb;
		}

		public double getImporte() {
			return importe;
		}

		public void setImporte(double importe) {
			this.importe = importe;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}


		@Override
		public int compareTo(tuplaVendedores o) {
			if(this.getImporte()<o.getImporte()){
				return -1;
				
			}else{
				if(this.getImporte()==o.getImporte()){
					return 0;
				}else{
					return 1;
				}
			}
		}

		
		
	}
	
	
	
	
	
	public String crearPDFCatalogoVigente(Candela candela) throws IOException{

		ArrayList<Tomo> tomos = candela.getCatalogoVigente().getTomos();

		List tuplasTomos = new ArrayList();	
		List tuplasProducto= new ArrayList();
		// recorro los tomos de memoria
		for (int i = 0; i < tomos.size(); i++) {
			Tomo t1 = tomos.get(i);
			tuplaTomo tomoT= new tuplaTomo(t1.getCodigoTomo(), t1.getDescripcion(), tuplasProducto);
			//asigno la lista de tomos que posee
			ArrayList<Producto> productos = tomos.get(i).getProductos();
			//por cada producto creo una tuplaProducto y asigno a tuplaTomos
			for (int j = 0; j < productos.size(); j++) {
				Producto prod = productos.get(j);
				tuplaProducto tp1 = new tuplaProducto(prod.getCodigo(), prod.getPrecio(), prod.getDescripcion(), prod.getCantidadEnStock(), prod.getTipoProducto());
				tomoT.addTuplaTomoDS(tp1);
			}
			tuplasTomos.add(tomoT);

		}




		JasperReport reporte = null;
		try {
			reporte = (JasperReport)JRLoader.loadObject(candela.getDirectorio()+"reportes/catalogoVigente.jasper");
		} catch (JRException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}    
		JasperPrint jasperPrint = null;
		try {
			jasperPrint = JasperFillManager.fillReport(reporte, null, new JRBeanCollectionDataSource(tuplasTomos));
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     

		JRExporter exporter = new JRPdfExporter();    
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);    
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(candela.getDirectorio()+"catalogoVigente.pdf"));     

		try {
			exporter.exportReport();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return candela.getDirectorio()+"reporteCatalogoVigente.pdf";

	}


	//clases necesarias para mapear los datos a utilizar en el jasper

	public class tuplaTomo{
		int codigoTomo;
		String descripcionTomo;
		List tuplaTomoDS;

		public tuplaTomo(int codigoTomo, String descripcionTomo, List tuplaProducto){
			this.codigoTomo=codigoTomo;
			this.descripcionTomo= descripcionTomo;
			this.tuplaTomoDS= tuplaProducto;
		}

		public int getCodigoTomo() {
			return codigoTomo;
		}

		public void setCodigoTomo(int codigoTomo) {
			this.codigoTomo = codigoTomo;
		}

		public String getDescripcionTomo() {
			return descripcionTomo;
		}

		public void setDescripcionTomo(String descripcionTomo) {
			this.descripcionTomo = descripcionTomo;
		}

		public List getTuplaTomo() {
			return tuplaTomoDS;
		}

		public void setTuplaTomoDS(List tuplaProducto) {
			this.tuplaTomoDS = tuplaProducto;
		}
		public void addTuplaTomoDS(tuplaProducto p1){
			this.tuplaTomoDS.add(p1);
		}
		public JRDataSource getTuplaTomoDS(){
			return new JRBeanCollectionDataSource(tuplaTomoDS);
		}

	}
	public class tuplaProducto{

		int codigoProducto;
		double precio;
		String descripcionProducto;
		int cantidadEnStock;
		String tipoProducto;

		public tuplaProducto(int codigoProducto, double precio, String descripcionProducto, int cantidadEnStock, int tipoProducto){
			this.codigoProducto= codigoProducto;
			this.precio= precio;
			this.descripcionProducto = descripcionProducto;
			this.cantidadEnStock= cantidadEnStock;
			if (tipoProducto <0){
				this.tipoProducto= "NO Asig.";
			}else{
				this.tipoProducto= Integer.toString(tipoProducto);
			}
			

		}

		public int getCodigoProducto() {
			return codigoProducto;
		}

		public void setCodigoProducto(int codigoProducto) {
			this.codigoProducto = codigoProducto;
		}

		public double getPrecio() {
			return precio;
		}

		public void setPrecio(double precio) {
			this.precio = precio;
		}

		public String getDescripcionProducto() {
			return descripcionProducto;
		}

		public void setDescripcionProducto(String descripcionProducto) {
			this.descripcionProducto = descripcionProducto;
		}

		public int getCantidadEnStock() {
			return cantidadEnStock;
		}

		public void setCantidadEnStock(int cantidadEnStock) {
			this.cantidadEnStock = cantidadEnStock;
		}


	}
	public class tuplaFactura{
		int numero;
		Date fecha;
		int tipo;
		String pagada;
		int dni;
		String nombre;
		String apellido;
		List detallesDS;

		public tuplaFactura(int numero, Date fecha, int tipo, String pagada,
				ArrayList detallesDS, int dni, String nombre, String apellido) {
			this.numero= numero;
			this.fecha= fecha;
			this.tipo=tipo;
			this.pagada= pagada;
			this.detallesDS= detallesDS;
			this.dni= dni;
			this.apellido=apellido;
			this.nombre=nombre;
		}

		public int getNumero() {
			return numero;
		}

		public void setNumero(int numero) {
			this.numero = numero;
		}

		public Date getFecha() {
			return fecha;
		}

		public void setFecha(Date fecha) {
			this.fecha = fecha;
		}

		public int getTipo() {
			return tipo;
		}

		public void setTipo(int tipo) {
			this.tipo = tipo;
		}

	
		public String getPagada() {
			return pagada;
		}

		public void setPagada(String pagada) {
			this.pagada = pagada;
		}

		public void setDetallesDS(List detallesDS){
			this.detallesDS= detallesDS;
		}
		public void addDetalleDS(tuplaDetalle d1){
			this.detallesDS.add(d1);
		}
		public JRDataSource getDetallesDS(){
			return new JRBeanCollectionDataSource(detallesDS);
		}

		public int getDni() {
			return dni;
		}

		public void setDni(int dni) {
			this.dni = dni;
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


	}
	public class tuplaDetalle{

		int cantidad,codigoProducto,tipoProducto;
		String color,talle,descripcion;
		double precio;


		public tuplaDetalle(int cantidad, String color, String talle,
				int codigoProducto, String descripcion, double precio, int tipoProducto) {
			this.cantidad=cantidad;
			this.codigoProducto=codigoProducto;
			this.color= color;
			this.talle= talle;
			this.descripcion= descripcion;
			this.precio= precio;
			this.tipoProducto= tipoProducto;

		}


		public int getCantidad() {
			return cantidad;
		}


		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}


		public int getCodigoProducto() {
			return codigoProducto;
		}


		public void setCodigoProducto(int codigoProducto) {
			this.codigoProducto = codigoProducto;
		}


		public int getTipoProducto() {
			return tipoProducto;
		}


		public void setTipoProducto(int tipoProducto) {
			this.tipoProducto = tipoProducto;
		}


		public String getColor() {
			return color;
		}


		public void setColor(String color) {
			this.color = color;
		}


		public String getTalle() {
			return talle;
		}


		public void setTalle(String talle) {
			this.talle = talle;
		}


		public String getDescripcion() {
			return descripcion;
		}


		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}


		public double getPrecio() {
			return precio;
		}


		public void setPrecio(double precio) {
			this.precio = precio;
		}


	}
	public void CrearPDFFacturaPeriodo(Candela candela, Date inicio, Date fin, int dni, String nombre, String apellido){

		//busco el usuario en memoria

		ArrayList<Usuario> colUsuarios = candela.getcolUSRSOFTWARE();

		for (int i = 0; i < colUsuarios.size(); i++) {
			if (colUsuarios.get(i).getDni() == dni){
				//encontre el usuario entonces devuelvo todas las facturas

				ArrayList<FacturaPersonal> facUsr = colUsuarios.get(i).ObtenerFacturas(inicio,fin);
				if (facUsr !=null){
					//si tengo facturas creo las tuplas para jasper

					List tuplasFacturas= new ArrayList();

					for (int j = 0; j < facUsr.size(); j++) {
						//recorro la coleccion de facturas y creo tuplas
						String msgPagada="NO";
						if (facUsr.get(j).getPagada()){
							msgPagada="SI";
						}
						tuplaFactura tf = new tuplaFactura(facUsr.get(j).getNumero(),facUsr.get(j).getFecha(),facUsr.get(j).getTipo(),msgPagada,new ArrayList(),dni,nombre,apellido);
						ArrayList<DetallePedidoPersonal> colDetalles = facUsr.get(j).getPedidoPers().getDetalles();
						for (int k = 0; k < colDetalles.size(); k++) {
							//por cada detalle creo tuplas y las agrego a tuplaDetalle

							tuplaDetalle td= new tuplaDetalle(colDetalles.get(k).getCantidad(), colDetalles.get(k).getColor(), colDetalles.get(k).getTalle(), colDetalles.get(k).getProd().getCodigo(), colDetalles.get(k).getProd().getDescripcion(), colDetalles.get(k).getProd().getPrecio(), colDetalles.get(k).getProd().getTipoProducto());
							//agrego a la tuplafactura la tupla detalle
							tf.addDetalleDS(td);
						}//termino de recorrer los detalles
						//cuando salga voy a  buscar otra factura asi que agrego la tupla al list

						tuplasFacturas.add(tf);

					}// termino de recorrer las facturas
					JasperReport reporte = null;
					try {
						reporte = (JasperReport)JRLoader.loadObject(candela.getDirectorio()+"reportes/facturasPeriodo.jasper");
					} catch (JRException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}    
					JasperPrint jasperPrint = null;
					try {
						jasperPrint = JasperFillManager.fillReport(reporte, null, new JRBeanCollectionDataSource(tuplasFacturas));
					} catch (JRException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}     

					JRExporter exporter = new JRPdfExporter();    
					exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);    
					exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(candela.getDirectorio()+"FacturasPeriodo.pdf"));     

					try {
						exporter.exportReport();
					} catch (JRException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


				}
			}
		}
	}


}







