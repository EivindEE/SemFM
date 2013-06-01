<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html dir="ltr" lang="en">
<head>
<meta charset="UTF-8" />
<title>${record.name} - SemFM</title>
<link rel="shortcut icon" type="image/x-icon"
	href="resources/images/favicon.ico" />
<link rel="icon" type="image/png" href="resources/images/favicon.png" />
<link rel="stylesheet" type="text/css" href="resources/css/screen.css" />
<script type="text/javascript">
	var playing;
	function playClicked(element){
		var player = document.getElementById(element);
		var img = document.getElementById(element + "img");
		if(playing){

			player.pause();
			playing = false;
			img.src = "resources/images/play-button.png";
		}
		else{
			player.play();
			playing = true;
			img.src = "resources/images/pause-button.png";
		}
	}

</script>
</head>
<body class="album">
	<jsp:include page="includes/header.jsp" />

	<div class="headline_wrapper">
		<div class="headline">
		<c:if test="${! empty record.image}">
			<span resource="#recordImg" typeof="schema:ImageObject">
				<meta about="#record" property="schema:image" href="#recordImg" />
				<img src="${record.image}" alt="" class="search_result_image"
					property="schema:image" style="width: 200px; height: 200px;" />
			</span>
				</c:if>
			<div class="h1-wrapper" resource="#record" typeof="schema:MusicAlbum">
				<h1 property="schema:name">${record.name}</h1>
				<meta content="${fn:length(record.tracks)}" datatype="xsd:integer" property="schema:numTracks" />
				<div class="h1-description">
					<c:if test="${! empty record.itunesLink}">
						<audio controls="controls" itemprop="audio">
							<source
								src="${itunesLink}"
								type="audio/aac" />
						</audio>
					</c:if>
					<ul>
						<c:forEach var="genre" items="${record.genres}">
							<li about="#record" property="schema:genre">${genre.value}</li>
						</c:forEach>
					</ul>
					<br /> by
					<ul>
						<c:forEach var="artist" items="${record.artist}">
							<li>
								<link typeof="schema:MusicGroup" href="artist?q=${artist.name}">
									<meta about="artist?q=${artist.name}" property="schema:album" href="#record" />
									<span about="artist?q=${artist.name}" property="schema:name">${artist.name}</span>
								</link>
							</li>
						</c:forEach>
					</ul>

				</div>
			</div>
		</div>
	</div>
	<div class="full main_wrapper cf">
		<c:if test="${! empty record.description}">
			<div class="album_description left half" itemprop="about">
				<h2>Description</h2>
				${record.description}
			</div>
		</c:if>
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
				<li>Genre:
					<ul>
						<c:forEach var="genre" items="${record.genres}">
							<li>${genre.value}</li>
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
		<div
			class="tracks<c:if  test="${! empty record.description}"> full</c:if><c:if  test="${empty record.description}"> left half</c:if>">
			<h2>Tracks</h2>
			<table class="zebra_table">
				<tr>
					<th>#</th>
					<th>Song Title</th>
					<th>Play time</th>
					<th>Artist</th>
					<th>Preview</th>
				</tr>
				<c:forEach var="track" items="${record.tracks}">
					<tr resource="#track${track.trackNr}" typeof="schema:MusicRecording">
						<meta about="#record" property="schema:track" href="#track${track.trackNr}" />
						<td>${track.trackNr}</td>
						<td property="schema:name">${track.name}</td>
						<td>${track.length}</td>
						<td><c:if test="${! empty track.artist}">
								<a href="artist?q=${track.artist}" property="schema:byArtist">${track.artist}</a>
							</c:if> <c:if test="${empty track.artist}">
								<c:forEach var="artist" items="${record.artist}">
									<a href="artist?q=${artist.name}" property="schema:byArtist">${artist.name}</a>
								</c:forEach>
							</c:if></td>
						<td><c:if test="${! empty track.preview}">
								<audio id="${track.preview}" resource="#prev${track.trackNr}" typeof="schema:AudioObject">
									<meta about="#track${track.trackNr}" property="schema:associatedMedia" href="#prev${track.trackNr}" />
									<source about="#prev${track.trackNr}" property="schema:contentUrl" src="${track.preview}"> </source>
								</audio>
								<div>
									<a onclick="playClicked('${track.preview}')"><img id="${track.preview}img" alt="" src="resources/images/play-button.png"/></a>
								</div>
							</c:if></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<c:if test="${!empty record.relatedRecords}">
			<div class="alternative_releases full">
				<h2>Alternative Releases</h2>
				<ul class="related_list">
					<c:forEach var="related" items="${record.relatedRecords}">
						<li><a href="album?q=${related.discogId}"
							class="related_name"><img src="${related.image}" alt="" />
								${related.name}</a></li>
					</c:forEach>
				</ul>
			</div>
		</c:if>
	</div>



	<jsp:include page="includes/footer.jsp" />
