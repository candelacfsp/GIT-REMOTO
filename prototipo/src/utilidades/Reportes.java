package utilidades;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	public Reportes(){
		System.out.println("Creado reporte");
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

}
