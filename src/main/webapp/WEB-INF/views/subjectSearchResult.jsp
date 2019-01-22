<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Add Book</title>
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
		<h3 class="text-success">Searched subject details</h3>
		<br />
		<table class="table table-bordered">
			<tr>
				<th>Field</th>
				<th>Value</th>
			</tr>
			<tr>
				<td>Subject ID</td>
				<td>${searchedSubject.subjectId}</td>
			</tr>
			<tr>
				<td>Sub Title</td>
				<td>${searchedSubject.subtitle}</td>
			</tr>
			<tr>
				<td>Duration In Hours</td>
				<td>${searchedSubject.durationInHours}</td>
			</tr>

		</table>

		<a href="<%=request.getContextPath()%>/subject/search"
			class="btn btn-primary">Go Back</a>
	</div>
</body>
</html>