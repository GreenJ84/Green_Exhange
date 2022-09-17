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
<script src="/js/main.js"></script>
</head>
<body>
	<div class="greenBar">
		<h1 class="projectName">GreenEx</h1>
		<div class="topLink">
			<a href="/portfolio">Portfolio</a> | <a href="/exchange">Exchange</a> | <a href="/clear">Logout</a>
		</div>
	</div>
	<h2 class="title">Welcome ${ thisUser.firstName }</h2>
	<div class="value">
		<p style="padding-right: 10px;"> Total Account Value: </p>  
		<p Style="width: 80px; border: 1px solid black; text-align: right;"> ${ thisUser.accountUSD }</p>
	</div>
	<div class="value">
		<p style="padding-right: 10px;"> Available Value: </p>  
		<p Style="width: 80px; border: 1px solid black; text-align: right;"> ${ thisUser.availableAccountUSD }</p>
	</div>
	<div class="topLink2">
	<a onclick="revealDeposit()">Deposit USD</a> | <a onclick="revealWithdraw()">Withdraw</a>
	</div>
	<div id="deposit" style="visibility:hidden; position: fixed; top: 14%; right: 30px; background-color: gray; border: 2px solid black; border-radius: 10px;">
		<form action="/deposit" method="post">
			<h4>Deposit USD</h4>
			<div class="mb-3">
	  			<label for="deposit" class="form-label">Amount: </label>
	  			<input type="text" class="input form-control" name="deposit" placeholder="Numbers" required/>
				<input type="submit" value="submit">
			</div>
		</form>
	</div>
	<div id="withdraw" style="visibility:hidden; position: fixed; top: 14%; right: 30px; background-color: gray; border: 2px solid black; border-radius: 10px;">
		<form action="/withdraw" method="post">
			<h4>Withdraw USD</h4>
			<div class="mb-3">
				<p>Must leave a $5 Account buffer!</p>
	  			<label for="withdraw" class="form-label">Amount: </label>
	  			<input type="text" class="input form-control" name="withdraw" placeholder="Numbers" required/>
				<input type="submit" value="submit">
			</div>
		</form>
	</div>
	<p class="errors"><c:out value="${ overdraw }"/></p>
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