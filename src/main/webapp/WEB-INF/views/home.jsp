<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<!DOCTYPE html>
<html dir="ltr" lang="en">
<head>
	<meta charset="UTF-8" />
	<title>SemFM - Semantic Music Search</title>
	<link rel="shortcut icon" type="image/x-icon" href="resources/images/favicon.ico" />
	<link rel="icon" type="image/png" href="resources/images/favicon.png" />
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="resources/css/screen.css" />
</head>

<body class="home">
	<div class="full main_wrapper">
		<a href="http://localhost:8080/SemFM" id="logo"><img src="resources/images/SemFM-large.png" alt="SemFM" /></a>
		<form id="search_form" action="artist" method="post">
			<input type="text" name="search_string" id="search_string" placeholder="Artist name" />
			<button>Submit Search</button>
		</form>
		<ul class="home_meta_link">
			<li><a href="">our sources</a></li>
			<li><a href="">help</a></li>
		</ul>
	</div>
</body>
</html>
