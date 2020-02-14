<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="en-US">

<head>
	<title>Login | Poker 999</title>
	<meta name="description" content=""/>
	<meta charset="UTF-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
	<link rel="icon" href="assets/images/logo.png"/>
	<link rel="stylesheet" href="lib/bootstrap4/css/bootstrap.min.css" />
	<link rel="stylesheet" href="assets/css/_common.css" />
	<link rel="stylesheet" href="assets/css/login.css"/>
</head>

<body>
	<section class="d-flex flex-wrap align-items-center">
		<div>
			<img class="img-logo" src="assets/images/logo.png" />
		</div>
		<div>
			<form action="login" method="POST">
				<p class="login-title">LOGIN</p>
				<input type="text" class="form-control" name="i" placeholder="USERNAME" required="required" autocomplete="on" autofocus="autofocus" />
				<br />
				<input type="password" class="form-control" name="p" placeholder="PASSWORD" required="required" autocomplete="on" />
				<br />
				<c:if test="${msg!=null}">
					<p class="p-msg">${msg}</p>
				</c:if>
				<button type="submit" class="btn btn-danger w-100">LOGIN</button>
			</form>
		</div>
	</section>
</body>

</html>