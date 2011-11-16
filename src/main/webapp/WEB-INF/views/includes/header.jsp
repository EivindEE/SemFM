<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- <script type="text/javascript"> -->
// 		$(document).ready(function() {
// 			console.log("Document ready");
// 		});
// 		$('#search_form').submit(function() {
// 			console.log("search submit");
// 			alert("HEEELOOO");
// 		});
<!-- 	</script> -->
<div id="header" class="cf">
	
	<a href="http://localhost:8080/SemFM" id="logo"><img src="http://localhost:8080/SemFM/spring/resources/images/SemFM-small.png" alt="" /></a>
	<form id="search_form" action="search" method="get">
		<input type="text" name="q" id="q" value="${q}" />
		<button class="search_button">Submit Search</button>
	</form>
	<ul class="header_meta_links">
		<li><a href="#sources" class="close">our sources</a></li>
		<li><a href="#help">help</a></li>
	</ul>
</div>

<div id="sources" class="full main_wrapper">
	<h2>Our Sources</h2>
	<a href="#" class="close">Close</a>
	<ol>
		<li>Last.FM (API/XML): Related artists and events</li>
		<li>Apple iTunes (API/JSON): Discography</li>
		<li>BBC Music (SPARQL): Artist Info</li>
		<li>DBPedia (SPARQL): Artist Info</li>
	</ol>
</div>
<div id="help" class="full main_wrapper">
	<h2>Help</h2>
	<a href="#" class="close">Close</a>
	<p>We allow searches for artists.</p>
</div>
