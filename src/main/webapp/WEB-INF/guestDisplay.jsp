<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@ page isErrorPage="true" %>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Guest Display</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="/css/main.css"/>
</head>
<body>
	<div class="greenBar">
	<h1 class="projectName">GreenEx</h1>
	<div class="topLink">
	<a href="/register">Register</a> | <a href="/login">Login</a>
	</div>
	</div>
	<h2 class="title">Current Price Data: </h2>
	<table class="table table-dark table-hover display">
		<tr style="height: 25px !important; font-weight: bold !important; font-size: 20px !important;">
			<th>Symbol</th>
			<th>% Change</th>
			<th>Price</th>
			<th>Market Cap</th>
		</tr>
		<c:forEach var="item" items="${ result }">
		<tr>
			<td><c:out value='${ item.symbol }'></c:out></td>
			<td><c:out value='${ item.percentChange}'></c:out></td>
			<td><c:out value='$${ item.price }'></c:out></td>
			<td><c:out value='${ item.marketCap }'></c:out></td>
		</tr>
		</c:forEach>
	</table>
	
	
</body>
</html>