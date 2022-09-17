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
<title>LogIn</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="/css/main.css"/></head>
<body>
	<div class="topLink" >
		<a href="/register">Create new User</a> | <a href="/">Guest</a>
	</div>
	<h1 style="margin-bottom: 25px;" class="title">Welcome, back!</h1>
	<div class="form">
		
		<p class="errors"><c:out value="${ errors }"/><p>
		<form:form modelAttribute="loginEvent" action="/login/check" method="post">
			<h2>Log In</h2>
			<form:errors class="validation" path="email"/>
			<form:errors class="validation" path="password"/>
			<div class="mb-3">
	  			<form:label path="email" class="form-label">Email address: </form:label>
	  			<form:input type="email" class="input form-control" path="email" placeholder="name@example.com" required="true"/>
			</div>
			<div class="mb-3">
	  			<form:label path="password" class="form-label">Password: </form:label>
	  			<form:input type="password" class="input form-control" path="password" placeholder="Password" required="true"/>
			</div>
			<input type="submit" value="Submit">
		</form:form>
	</div>
</body>
</html>