<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<!DOCTYPE html>
<html dir="ltr" lang="en">
<head>
	<meta charset="UTF-8" />
	<title>Search - SemFM</title>
	<link rel="shortcut icon" type="image/x-icon" href="resources/images/favicon.ico" />
	<link rel="icon" type="image/png" href="resources/images/favicon.png" />
	<link rel="stylesheet" type="text/css" href="resources/css/screen.css" />
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
</head>
<body class="search-results">
<jsp:include page="includes/header.jsp" />
	<div class="full main_wrapper text_center">
		<h2 class="search_results text_center">Couldn't find "${q}".</h2>
		<img src="resources/images/csabacat.png" alt="Sowwy" />
		<p>Our residential semantic search cat couldn't find the thing you are looking for.</p>
		<p>Sowwy ='(</p>
	</div>
<jsp:include page="includes/footer.jsp" />