<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!doctype html>
<html lang="en-US">

<head>
	<title>${statusCode} Error | Poker 999</title>
	<meta name="description" content=""/>
	<jsp:include page="/_head.jsp"/>
</head>

<body>
	<jsp:include page="/_header.jsp"/>
	<div class="div-root">
	<c:if test="${ME.isMaster}">
		<jsp:include page="/master/_menu.jsp"/>
	</c:if>
	<%--
	<c:if test="${ME.isEmployee}">
		<jsp:include page="/employee/_menu.jsp"/>
	</c:if> --%>
		<section class="p-4">
			<h3 class="text-muted m-0">${uri}</h3>
			<h1 class="text-danger display-3">${statusCode}</h1>
			<h5>${msg}</h5>
			<h5>${exceptionType}</h5>
			<h6>${exception}</h6>
		</section>
	</div>
</body>

<jsp:include page="/_script.jsp"/>
<script type="text/javascript">
	$("#menu-dashboard").addClass("active");
</script>
	
</html>