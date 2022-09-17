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
			<a href="/portfolio">Portfolio</a> | <a href="/dashboard">Dashboard</a> | <a href="/clear">Logout</a>
		</div>
	</div>
	<h2>Welcome to the Exchange!</h2>
	<div class="value">
		<p style="padding-right: 10px;"> Available Account Value: </p>  
		<p Style="width: 80px; border: 1px solid black; text-align: right;"> ${ thisUser.availableAccountUSD }</p>
	</div>
	<p class="errors"><c:out value="${ notEnough }"/><p>
	<form class="form" id="myForm" action="/exchange/trade" method="post">
	<label for="select">Select a Currency to Trade!</label>
	<select id="select" name="select" onchange="revealDetails()" class="form-select" aria-label="Default select example" required>
  		<option selected>Open this select menu</option>
  		<c:forEach var="item" items="${ result }">
  			<option value="${ item.symbol }"><c:out value="${ item.symbol }"></c:out> | <c:out value="$${ item.price }"></c:out></option>
  		</c:forEach>
	</select>
	
	<div id="selectInfo" style="visibility: hidden;">
		<div>
			<label><em><strong>Symbol: </strong></em></label>
			<input class="input" id="symbol" name="symbol" class="details" type="text" required>
		</div>
		<div>
			<label><em><strong>Price: </strong></em></label>
			<input class="input" id="price" name="price" class="details" type="text" required>
		</div>
		<div>
			<label>Purchase Amount: </label>
			<input class="input" id="purchaseAmount" name="purchaseAmount" class="details" type="text" placeholder="Only Whole #'s">
			<input id="hideAfter" onclick="revealEstimate()" type="button" value="Confirm Amount">
		</div>
		<div id="calcEstimate" style="visibility: hidden;">
			<label><em><strong>Estimated Crypto:</strong></em></label>
			<input class="input" id="estimate" name="estimate" class="details" type="text" required>
		</div>
		<input id="submit" style="visibility: hidden;" type="submit" value="Trade">
	</div>
	</form>
	
	<script src="/js/main.js"></script>
</body>
</html>