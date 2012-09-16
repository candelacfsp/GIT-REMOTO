package utilidades;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import negocio.Candela;
import negocio.Producto;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class Reportes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Candela candela= new Candela();
		try {
			candela.iniciar();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		ArrayList<Producto> productos = candela.getColProductos();
		
		List datos = new ArrayList();
		for (int i = 0; i < productos.size(); i++) {
			datos.add(productos.get(i));
		}
		 JasperReport reporte = null;
		try {
			reporte = (JasperReport)JRLoader.loadObject("WebContent/Candela/reporteProductosBlue.jasper");
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
	        exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File("reporteProductos.pdf"));     

	        try {
				exporter.exportReport();
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

}
