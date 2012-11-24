<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<%
	response.setHeader("Cache-Control",
			"no-cache, no-store, must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);

	HttpSession candela_sesion = request.getSession(false);
	if (candela_sesion != null){
		candela_sesion.removeAttribute("nombreUsr");
		candela_sesion.removeAttribute("tipoUsr");
		candela_sesion.invalidate();
	
	}
	response.sendRedirect("index.jsp");
	
%>
<body>

</body>
</html>