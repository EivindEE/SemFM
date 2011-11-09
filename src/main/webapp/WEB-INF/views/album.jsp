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
<body class="album">
<jsp:include page="includes/header.jsp" />

<div class="headline_wrapper">
	<div class="headline">
		<img src="${record.image}" alt="" class="search_result_image" />
		<div class="h1-wrapper">
			<h1>${record.name}</h1>
			<span class="h1-description">
				<ul><c:forEach var="genre" items="${record.genres}"><li>${genre}</li></c:forEach></ul><br />
				by <ul><c:forEach var="artist" items="${record.artist}"><li>${artist.name}</li></c:forEach></ul><br />
			</span>
		</div>
	</div>
	<ul class="headline_links">
		<li><a href="${itunesLink}" class="itunes">iTunes</a></li>
		<li><a href="${spotifyUri}" class="spotify">Spotify</a></li>
	</ul>
</div>
<div class="full main_wrapper">
	<div class="album_description left half">
		<h2>Description</h2>
		${record.description};
	</div>
	<div class="album_meta right half">
		<h2>Meta Facts</h2>
		<ul>
			<li>Artist: ${record.artist}</li>

		</ul>	
	</div>
	<div class="tracks full">
		<h2>Tracks</h2>
		<table class="zebra_table">
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
		<ul class="related_list">
		<c:forEach var="related" items="${record.relatedRecords}">
			<li> <a href="" clas="related_name"><img src="${related.image}" alt="" /> ${related.name}</a></li>
		</c:forEach>
		</ul>
	</div>
</div>



<jsp:include page="includes/footer.jsp" />