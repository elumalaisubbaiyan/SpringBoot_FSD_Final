<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<c:if test="${not empty error}">
			<div class="row alert alert-danger">${error}</div>
		</c:if>
		<div class="row alert alert-info">Enter values below to Add a
			new Book</div>
		<form action="book" method="post">
			<div class="form-group">
				<label for="name"> Book ID </label> <input type="text"
					class="form-control" required placeholder="Book Id" id="bookId"
					name="bookId" pattern="[0-9]{1,7}" value="" maxlength="10"
					autocomplete="off" />
			</div>
			<div class="form-group">
				<label for="bookTitle">Book Title</label> <input type="text"
					name="bookTitle" class="form-control" required
					placeholder="Book Title" id="bookTitle" maxlength="40"
					autocomplete="off" />
			</div>
			<div class="form-group">
				<label for="phoneNumber">Book Price</label> <input type="text"
					class="form-control" required placeholder="Price" id="price"
					name="price" maxlength="10" autocomplete="off" /> <small
					id="priceHelp" class="form-text text-muted">Enter only
					numbers</small>
			</div>

			<div class="form-group">
				<label for="volume">Volume</label> <input type="text"
					class="form-control" required placeholder="Book Volume" id="volume"
					autocomplete="off" name="volume" maxlength="10" /> <small
					id="volumeHelp" class="form-text text-muted">Enter only
					numbers</small>
			</div>
			<div class="form-group">
				<label for="phoneNumber">Publish Date</label> <input type="date"
					class="form-control" required placeholder="Publish Date"
					id="publishDate" name="publishDate" maxlength="10" />
			</div>
			<button type="submit" value="Submit" class="btn btn-primary">Add
				Book</button>
		</form>
	</div>
</body>
</html>