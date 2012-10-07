package utilidades;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import negocio.Candela;
import negocio.Producto;
import negocio.Tomo;
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
		int tipoProducto;

		public tuplaProducto(int codigoProducto, double precio, String descripcionProducto, int cantidadEnStock, int tipoProducto){
			this.codigoProducto= codigoProducto;
			this.precio= precio;
			this.descripcionProducto = descripcionProducto;
			this.cantidadEnStock= cantidadEnStock;
			this.tipoProducto= tipoProducto;
			
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

		public int getTipoProducto() {
			return tipoProducto;
		}

		public void setTipoProducto(int tipoProducto) {
			this.tipoProducto = tipoProducto;
		}

	}

}





