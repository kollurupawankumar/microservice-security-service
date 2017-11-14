

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
 
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	<title>Product Details</title>
</head>
 
<body>
 
<h1>Product</h1>
<form:form action="/auth" method="POST" commandName="jwtAuthenticationDto"> 	
	<table>
		<tr>
			<td>Enter Seller Id:</td>
                        <td><form:input id="username" path="username" size="100"/></td>
		</tr>
		<tr>
			<td>Enter Product Name:</td>
                        <td><form:input id="password" path="password" size="100"/></td>
		</tr>
		
		<tr>
			<td colspan="2" align="center">
				<input type="submit" value="Submit" />	
			</td>
		</tr>
	</table>
</form:form>
 
</body>
</html>

