<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<!DOCTYPE html>
<html dir="ltr" lang="en">
<head>
	<meta charset="UTF-8" />
	<title>${record.name} - SemFM</title>
	<link rel="shortcut icon" type="image/x-icon" href="resources/images/favicon.ico" />
	<link rel="icon" type="image/png" href="resources/images/favicon.png" />
	<link rel="stylesheet" type="text/css" href="resources/css/screen.css" />
</head>
<body class="album" itemscope itemtype="http://www.schema.org/MusicAlbum">
<jsp:include page="includes/header.jsp" />

<div class="headline_wrapper">
	<div class="headline">
		<img src="${record.image}" alt="" class="search_result_image" itemprop="image" />
		<div class="h1-wrapper">
			<h1 itemprop="name">${record.name}</h1>
			<meta content="${fn:length(record.tracks)}" itemprop="numTracks" />
			<meta content="${record.genres}" itemprop="genre" />
			<div class="h1-description">
				<c:if test="${! empty record.itunesPreview}">
					<audio controls="controls" itemprop="audio">
					  <source src="${record.itunesPreview}" type="audio/aac" />
					</audio>
				</c:if>
				<ul>
					<c:forEach var="genre" items="${record.genres}">
						<li>${genre}</li>
					</c:forEach>
				</ul><br />
				by
				 <ul itemprop="byArtist" itemscope itemtype="http://www.schema.org/MusicGroup">
					<c:forEach var="artist" items="${record.artist}">
						<li itemprop="name">${artist.name}</li>
					</c:forEach>
				</ul>
				
			</div>
		</div>
	</div>
	<ul class="headline_links">
		<li><a href="${record.itunesLink}" class="itunes">iTunes</a></li>
		<li><a href="${record.spotifyUri}" class="spotify">Spotify</a></li>
	</ul>
</div>
<div class="full main_wrapper">
	<div class="album_description left half" itemprop="about">
		<h2>Description</h2>
		${record.description};
	</div>
	<div class="album_meta meta_list right half">
		<h2>Meta Facts</h2>
		<ul>
				<li>Artist: 
					<ul>
						<c:forEach var="artist" items="${record.artist}">
							<li><a href="artist?q=${artist.name}">${artist.name}</a></li>
						</c:forEach>
					</ul>
				</li>
				<c:forEach var="metaTidbit" items="${record.meta}">
					<li>${metaTidbit.key}: 
						<ul>
							<c:forEach var="item" items="${metaTidbit.value}">
								<li>${item}</li>
							</c:forEach>
						</ul>
					</li>
				</c:forEach>
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
				<tr itemprop="tracks" itemscope itemtype="http://www.schema.org/MusicRecording">
					<td>${track.trackNr}</td>
					<td itemprop="name">${track.name}</td>
					<td itemprop="duration">${track.length}</td>
					<td>
						<c:if test="${! empty track.artist}">
							<a href="artist?q=${track.artist}" itemprop="byArtist">${track.artist}</a>
						</c:if>
						<c:if test="${empty track.artist}">
							<c:forEach var="artist" items="${record.artist}">
								<a href="artist?q=${artist.name}" itemprop="byArtist">${artist.name}</a> 
							</c:forEach>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="alternative_releases full">
		<h2>Alternative Releases</h2>
		<ul class="related_list">
		<c:forEach var="related" items="${record.relatedRecords}">
			<li><a href="album?q=${related.discogId}" class="related_name"><img src="${related.image}" alt="" /> ${related.name}</a></li>
		</c:forEach>
		</ul>
	</div>
</div>



<jsp:include page="includes/footer.jsp" />