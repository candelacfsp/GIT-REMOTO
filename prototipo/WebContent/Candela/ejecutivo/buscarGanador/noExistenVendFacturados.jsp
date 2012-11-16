<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
		<%
			response.setHeader("Cache-Control",
					"no-cache, no-store, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
		%>
		<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=10.0.0.0"
			width="800" height="600">
			<param name="movie" value="noExistenVendFacturados.swf" />
			<param name="quality" value="high" />
			<embed src="noExistenVendFacturados.swf" quality="high"
				type="application/x-shockwave-flash" width="800" height="600"
				pluginspage="http://www.macromedia.com/go/getflashplayer"></embed>
		</object>
	</center>
</body>
</html>