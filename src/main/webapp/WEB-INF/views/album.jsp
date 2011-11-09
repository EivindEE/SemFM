<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html dir="ltr" lang="en">
<head>
	<meta charset="UTF-8" />
	<title>${album.name} - SemFM</title>
	<link rel="shortcut icon" type="image/x-icon" href="http://localhost:8080/SemFM/spring/resources/images/favicon.ico" />
	<link rel="icon" type="image/png" href="http://localhost:8080/SemFM/spring/resources/images/favicon.png" />
	<link rel="stylesheet" type="text/css" href="http://localhost:8080/SemFM/spring/resources/css/screen.css" />
</head>
<body>
<jsp:include page="includes/header.jsp" />

<div class="headline_wrapper">
	<div class="headline">
		<img src="${record.image}" alt="" class="search_result_image" />
		<div class="h1-wrapper">
			<h1>${record.name}</h1>
			<span class="h1-description">by <ul><c:forEach var="artist" items="${record.artist}"> <li>${artist.name}</li></c:forEach></ul></span>
		</div>
	</div>
</div>
<div class="full main_wrapper">
	<div class="album_description left half">
		<h2>Description</h2>
		${record.description};
	</div>
	<div class="meta right half">
		<h2>Meta Facts</h2>
		<ul>
			<li>Artist: ${record.artist}</li>
			<li>Released: ${record.year}</li>
			<li>Play time: ${record.playtime}</li>
			<li>Label: ${record.label}</li>
		</ul>	
	</div>
	<div class="tracks full">
		<h2>Tracks</h2>
		<table>
			<tr>
				<th>#</th>
				<th>Song Title</th>
				<th>Play time</th>
				<th>Artist</th>
			</tr>
			<c:forEach var="track" items="${record.tracks}">
				<tr>
					<td>${track.trackNr}</td>
					<td>${track.name}</td>
					<td>${track.length}</td>
					<td>${track.artist}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="alternative_releases full">
		<h2>Alternative Releases</h2>
		<c:forEach var="related" items="${record.relatedRecords}">
			<li>${related.image} ${related.name}</li>
		</c:forEach>
	</div>
</div>



<jsp:include page="includes/footer.jsp" />