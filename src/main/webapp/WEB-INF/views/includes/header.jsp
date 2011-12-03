<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="header" class="cf">
	<a href="/SemFM" id="logo"><img
		src="resources/images/SemFM-small.png" alt="" /></a>
	<form id="search_form" action="search" method="get">
		<input type="text" name="q" id="q" value="${q}" />
		<button class="search_button">Submit Search</button>
	</form>
	<ul class="header_meta_links">
		<c:if test="${! empty artist  || ! empty record}">
			<c:if test="${! empty artist}">
				<li><a href="?q=${q}&out=xml">as rdf</a></li>
			</c:if>
			<c:if test="${! empty record}">
				<li><a href="?q=${q}&artist=${record.artist[0].name}&out=xml">as rdf</a></li>
			</c:if>
		</c:if>
		<li><a href="#sources" class="close">our sources</a></li>
		<li><a href="#help">help</a></li>
	</ul>
</div>


<div id="sources" class="full main_wrapper">
	<h2>Our Sources</h2>
	<a href="#" class="close">Close</a>
	<ol>
		<li><a href="hhtp://last.fm">Last.FM</a> (API/XML): Related
			artists and events</li>
		<li><a href="http://itunes.apple.com">Apple iTunes</a>
			(API/JSON): Discography</li>
		<li><a href="http://bbc.co.uk/music">BBC Music</a> (SPARQL):
			Artist Info</li>
		<li><a href="http://dbpedia.org">DBPedia</a> (SPARQL): Artist
			Info</li>
		<li><a href="http://discogs.com">Discogs</a> (SPARQL): Album info</li>
	</ol>
</div>
<div id="help" class="full main_wrapper">
	<h2>Help</h2>
	<a href="#" class="close">Close</a>
	<p>We allow searches for artists or albums.</p>
	<h3>Examples:</h3>
	<ul>
		<li><a href="album?q=Curtain%20Call&artist=Eminem">Curtain
				Call by Eminem (album)</a></li>
		<li><a href="artist?q=Rihanna">Rihanna (artist)</a></li>
	</ul>
</div>
