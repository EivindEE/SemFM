<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html dir="ltr" lang="en">
<head>
	<meta charset="UTF-8" />
	<title>SemFM - Semantic Music Search</title>
	<link rel="shortcut icon" type="image/x-icon" href="http://localhost:8080/SemFM/spring/resources/images/favicon.ico" />
	<link rel="icon" type="image/png" href="http://localhost:8080/SemFM/spring/resources/images/favicon.png" />
	<link rel="stylesheet" type="text/css" href="http://localhost:8080/SemFM/spring/resources/css/screen.css" />
	<script type="text/javascript" src="resources/javascript/jquery.min.js">
	function postMessage() {
		$.get("search", $('#search_form').serialize());
	}
	</script>

</head>

<body class="home">
	<a href="http://localhost:8080/SemFM" id="logo"><img src="http://localhost:8080/SemFM/spring/resources/images/SemFM-large.png" alt="SemFM" /></a>
	<form id="search_form" action="search" method="post">
		<input type="text" name="search_string" id="search_string" placeholder="Artist name" />
		<button>Submit Search</button>
	</form>
	<ul class="header_meta_links">
		<li><a href="">our sources</a></li>
		<li><a href="">help</a></li>
	</ul>
	
</body>
</html>
