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
			<a href="/dashboard">Dashboard</a> | <a href="/exchange">Exchange</a> | <a href="/clear">Logout</a>
		</div>
	</div>
	<h2 class="title">${ thisUser.firstName } ${ thisUser.lastName }'s Portfolio</h2>
	<div class="value">
		<p style="padding-right: 10px;"> Account Value: </p>  
		<p Style="width: 80px; border: 1px solid black; text-align: right;"> ${ thisUser.accountUSD }</p>
	</div>
	<h2 class="title">Portfolio Price Data: </h2>
		<table class="table table-dark table-hover display">
			<tr style="height: 25px !important; font-weight: bold !important; font-size: 20px !important;">
				<th>Symbol</th>
				<th>Amount Held</th>
				<th>Purchase Price</th>
			</tr>
			<c:choose>
				<c:when test="${ cryptos.size() == 0 }">
					<tr>
						<td>You don't</td>
						<td>Have any</td>
						<td>Crypto</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="item" items="${ cryptos }">
						<tr>
							<td>${ item.symbol }</td>
							<td>${ item.holdings }</td>
							<td>${ item.purchaseAt }</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			
		</table>
	
	
</body>
</html>