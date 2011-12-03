<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
	<div class="full main_wrapper">
		<h2 class="full text_center standard_margin">Found <c:if test="${artist != null}"> 1 artist and </c:if> ${fn:length(records)} record result<c:if test="${fn:length(records) != 1 }">s</c:if> for "${q}"</h2>
		<c:if test="${artist != null}">
			<h3>Artists:</h3>
			<ul class="zebra_list search_result_list full">
				<li><a href="artist?q=${artist.name}">${artist.name}</a> <span class="right">(<a href="artist?q=${artist.name}&out=xml">Artist RDF</a>)</span></li>
			</ul>
		
		</c:if>
		<c:if test="${! empty records}">
		<h3>Records:</h3>
		<ul class="zebra_list search_result_list full">
			
			<c:forEach var="record" items="${records}">
				<li>
				
					<a href="album?q=${record.name}&amp;artist=${record.artist[0].name}">
						<strong>${record.name}</strong>(${record.year})
					</a>
					 by 
					 
						<c:forEach var="artist" items="${record.artist}">
							<a href="artist?q=${artist.name}">${artist.name}</a> 
						</c:forEach>
						<span class="right">(<a href="album?q=${record.name}&amp;artist=${record.artist[0].name}&out=xml">Album RDF</a> / <a href="artist?q=${record.artist[0].name}&out=xml">Artist RDF</a> )</span>
				</li>
			</c:forEach>
		</ul>
		</c:if>
	</div>
<jsp:include page="includes/footer.jsp" />