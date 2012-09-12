<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%

//NOTA: Verificar si en linux una aplicacion en Java tiene permisos para borrar archivos??

			File f= new File("C:/ProyectosJEE/prototipo-27-06-12/WebContent/PagWebCandela/PaginaWeb/"+"datosProductoAsocTomo.xml");
			if(f.exists()==true){
				f.delete();			
				System.out.println("Se borro correctamente el XML!");
			}		


%>
</body>
</html>