<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="http://fonts.googleapis.com/css?family=Ubuntu">
<meta charset="UTF-8">
<title>National Parks Geek</title>
<c:url value="/css/stylesheet.css" var="styleHref" />
<link rel="stylesheet" href="${styleHref}">
</head>

<body>
	<header>
		<div class="header center" id="myHeader">
			<c:url value="/" var="homePageHref" />
			<c:url value="/img/logo.png" var="logoSrc" />
			<a href="${homePageHref}"> <img class="logo" src="${logoSrc}"
				alt="National Park Geek Logo" />
			</a>
			<h1>Explore the National Parks!</h1>

			<nav>

				<c:url var="homeUrl" value="/" />
				<a href="${homeUrl}">Home Page</a>
				<c:url var="surveyUrl" value="/survey" />
				<a href="${surveyUrl}">Vote For Your Favorite Park!</a>
				<c:url var="surveyResUrl" value="/surveyResults" />
				<a href="${surveyResUrl}">Favorite Park Rankings</a>


			</nav>
		</div>
	</header>
<script>
		window.onscroll = function() {
			myFunction()
		};
		var header = document.getElementById("myHeader");
		var sticky = header.offsetTop;
		function myFunction() {
			if (window.pageYOffset >= sticky) {
				header.classList.add("sticky");
			} else {
				header.classList.remove("sticky");
			}
		}
	</script>