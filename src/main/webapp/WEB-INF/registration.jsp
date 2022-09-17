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
<title>Registration</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="/css/main.css"/></head>
</head>
<body>
	<div class="topLink">
		<a href="/login">Already Registered?</a> | <a href="/">Guest</a>
	</div>
	<h1 class="title">Welcome!</h1>
	<p style="margin-left: 50px;">Join our growing community now!</p>
	<div class="form">
		<p class="errors"><c:out value="${ errors }"/></p>
		<form:form modelAttribute="newUser" action="/createUser" method="post">
			<h2>Register</h2>
			<form:errors class="validation" path="firstName"/>
			<form:errors class="validation" path="lastName"/>
			<form:errors class="validation" path="email"/>
			<form:errors class="validation" path="password"/>
			<form:errors class="validation" path="confirmPassword"/>
			<div class="mb-3">
	  			<form:label path="firstName" class="form-label">First Name: </form:label>
	  			<form:input type="text" class="input form-control" path="firstName" placeholder="User First Name" required="true"/>
			</div>
			<div class="mb-3">
	  			<form:label path="lastName" class="form-label">Last Name: </form:label>
	  			<form:input type="text" class="input form-control" path="lastName" placeholder="User Last Name" required="true"/>
			</div>
			<div class="mb-3">
	  			<form:label path="email" class="form-label">Email address: </form:label>
	  			<form:input type="email" class="input form-control" path="email" placeholder="name@example.com" required="true"/>
			</div>
			<div class="mb-3">
	  			<form:label path="password" class="form-label">Password: </form:label>
	  			<form:input type="password" class="input form-control" path="password" placeholder="Password" required="true"/>
			</div>
			<div class="mb-3">
	  			<form:label path="confirmPassword" class="form-label">Confirm Password: </form:label>
	  			<form:input type="password" class="input form-control" path="confirmPassword" placeholder="Confirm Password" required="true"/>
			</div>
			<input type="submit" value="Submit">
		</form:form>
	</div>
</body>
</html>