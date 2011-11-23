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
		<a href="" id="logo"><img src="resources/images/SemFM-large.png" alt="SemFM" /></a>
		<form id="search_form" action="search" method="get">
			<input type="text" name="q" id="q" placeholder="Search" />
			<button>Submit Search</button>
		</form>
		<ul class="home_meta_link">
			<li><a href="#sources">our sources</a></li>
			<li><a href="#help">help</a></li>
		</ul>
		<div id="sources" class="full main_wrapper">
			<h2>Our Sources</h2>
			<a href="#" class="close">Close</a>
			<ol>
				<li><a href="hhtp://last.fm">Last.FM</a> (API/XML): Related artists and events</li>
				<li><a href="http://itunes.apple.com">Apple iTunes</a> (API/JSON): Discography</li>
				<li><a href="http://bbc.co.uk/music">BBC Music</a> (SPARQL): Artist Info</li>
				<li><a href="http://dbpedia.org">DBPedia</a> (SPARQL): Artist Info</li>
				<li><a href="http://discogs.com">Discogs</a> (SPARQL): Album info</li>
			</ol>
		</div>
		<div id="help" class="full main_wrapper">
			<h2>Help</h2>
			<a href="#" class="close">Close</a>
			<p>We allow searches for artists or albums.</p>
			<h3>Examples:</h3>
			<ul>
				<li><a href="album?q=Curtain%20Call&artist=Eminem">Curtain Call by Eminem (album)</a></li>
				<li><a href="artist?q=Rihanna">Rihanna (artist)</a></li>
			</ul>
		</div>
	</div>
</body>
</html>
