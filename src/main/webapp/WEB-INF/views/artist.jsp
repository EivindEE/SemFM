<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<!DOCTYPE html>
<html dir="ltr" lang="en">
<head>
	<meta charset="UTF-8" />
	<title>${artist.name} - SemFM</title>
	<link rel="shortcut icon" type="image/x-icon" href="resources/images/favicon.ico" />
	<link rel="icon" type="image/png" href="resources/images/favicon.png" />
	<link rel="stylesheet" type="text/css" href="resources/css/screen.css" />
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
	
	<c:if test="${! empty artist.discography}">
	<script type="text/javascript" src="resources/javascript/thune.scroller.js"></script>
	<script type="text/javascript">
		$(document).ready(function()  {
			$(".album_list_carousel").thuneScroller({
				btnNext: ".next",
				btnPrev: ".prev",
				speed: 200,
				visible: 4,
				scroll: 4
			});
		});
	</script>
	</c:if>

	<c:if test="${! empty artist.locatedEvents}">
		<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=true"></script>
		<script type="text/javascript">
			$(document).ready(function() {
			    var latlng = new google.maps.LatLng(35, 10);
			    var myOptions = {
			      zoom: 2,
			      center: latlng,
			      mapTypeId: google.maps.MapTypeId.ROADMAP
			    };
			    var map = new google.maps.Map(document.getElementById("concert_map"),
			        myOptions);
			    <c:forEach var="eventMarker" items="${artist.locatedEvents}">
			  	  var latlng = new google.maps.LatLng(${eventMarker.lat},${eventMarker.lng});
				  var marker = new google.maps.Marker({
				      position: latlng, 
				      map: map, 
				      title:"<strong>${artist.name}</strong><br />${eventMarker.venue} @ ${eventMarker.venue}"
				  }); 	
		  		</c:forEach>
			});
		</script>
	</c:if>
</head>
<body class="artist" itemscope itemtype="http://schema.org/MusicGroup">

<jsp:include page="includes/header.jsp" />

<div class="headline_wrapper<c:if test="${! empty artist.locatedEvents}"> tour</c:if>">
	<div class="headline">
		<c:if test="${! empty artist.image}">
		<img src="${artist.image}" alt="" class="search_result_image" itemprop="image" />
		</c:if>
		<div class="h1-wrapper">
			<h1 itemprop="name">${artist.name}</h1>
			<span class="h1-description">${artist.shortDescription}</span>
		</div>
	</div>
</div>

<div class="full main_wrapper">
	<c:if test="${! empty artist.bio}">
	<div class="biography left half" itemprop="description">
		<h2>Bio</h2>
		${artist.bio}
	</div>
	</c:if>
	<c:if test="${! empty artist.meta}">
	<div class="artist_meta meta_list right half">
		<h2>Meta Facts</h2>
		<ul>
			<c:forEach var="metaTidbit" items="${artist.meta}">
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
	</c:if>
	<c:if test="${! empty artist.discography}">
	<div class="discography full">
		<h2>Discography</h2>
		<div class="album_list_wrapper">
			<button class="prev">Previous</button>
			<button class="next">Next</button>
			<div class="album_list_carousel">
				<ul class="album_list">
					<c:forEach var="album" items="${artist.discography}">
						<li class="album" itemprop="albums" itemscope itemtype="http://schema.org/MusicAlbum">
							<h3><a href="album?q=${album.name}&artist=${artist.name}" itemprop="name">${album.name}</a></h3>
							<img src="${album.image}" alt="" />
							<span class="album_year" itemprop="datePublished">${album.year}</span><br />
							<span class="album_publisher" itemprop="publisher">${album.label}</span>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	</c:if>
	<c:if test="${! empty artist.locatedEvents}">
	<div class="concert full">
		<h2>Upcoming concerts</h2>
		<div class="concert_map">
			<div id="concert_map"></div>
		</div>
		<table class="concert_table zebra_table full">
			<tr>
				<th>Date</th>
				<th>Location</th>
				<th>Venue</th>
			</tr>
			<c:forEach var="event" items="${artist.events}">
				<tr itemprop="events" itemscope itemtype="http://schema.org/Event">
					<td itemprop="startDate">${event.date}</td>
					<td itemprop="location">${event.location}</td>
					<td itemprop="name">${event.venue}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	</c:if>
	
	<div class="related_artists full">
		<h2>Related Artists</h2>
		<ul class="artist_list">
			<c:forEach var="relatedArtist" items="${artist.similar}">
				<li>
					<a href="artist?q=${relatedArtist.safeName}">
						<img src="${relatedArtist.image}" alt="" /> 
						<span class="artist_name">${relatedArtist.name}</span><br />
						<span class="artist_short_desc">${relatedArtist.shortDescription}</span>
					</a>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>

<jsp:include page="includes/footer.jsp" />
