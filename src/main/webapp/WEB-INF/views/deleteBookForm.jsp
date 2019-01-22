<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Delete a Book</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
</head>

<body>
	<jsp:include page="header.jsp" />
	<div class="container">
		<br />
		<c:if test="${not empty error}">
			<div class="row alert alert-danger">${error}</div>
		</c:if>
		<div class="row">
			<h1>Enter values below to Delete a book</h1>
		</div>
		<form action="<%=request.getContextPath()%>/book/delete" method="post">
			<div class="form-group">
				<label for="name"> Book ID </label> <input type="text"
					class="form-control" required placeholder="Enter Book Id to Delete"
					id="bookId" name="bookId" pattern="[0-9]{1,7}" value=""
					autocomplete="off" maxlength="30" />
			</div>
			<button type="submit" value="Submit" class="btn btn-primary">Delete
				Book</button>
		</form>
	</div>
</body>
</html>