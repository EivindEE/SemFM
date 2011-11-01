<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html dir="ltr" lang="en">
<head>
	<meta charset="UTF-8" />
	<title>SemFM - Semantic Music Search</title>
	<link rel="shortcut icon" type="image/x-icon" href="resources/images/favicon.ico" />
	<link rel="icon" type="image/png" href="resources/images/favicon.png" />
	<link rel="stylesheet" type="text/css" href="resources/css/screen.css" />
</head>

<body class="home">
	<a href="#" id="logo"><img src="resources/images/SemFM-large.png" alt="SemFM" /></a>
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
