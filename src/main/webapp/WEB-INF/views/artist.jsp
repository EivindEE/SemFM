<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<jsp:include page="includes/header.jsp" />

<c:if test="${not empty artist}">
	hey
</c:if>

<div id="header" class="cf">
	<a href="#" id="logo"><img src="resources/images/SemFM-small.png" alt="" /></a>
	<form id="search_form" action="search" method="post">
		<input type="text" name="search_string" id="search_string" value="Search term" />
		<button>Submit Search</button>
	</form>
	<ul class="header_meta_links">
		<li><a href="">our sources</a></li>
		<li><a href="">help</a></li>
	</ul>
</div>

<div class="headline_wrapper">
	<div class="headline">
		<img src="${artist.image}" alt="" class="search_result_image" />
		<div class="h1-wrapper">
			<h1>${artist.name}</h1>
			<span class="h1-description">${artist.shortDescription}</span>
		</div>
	</div>
</div>

<div class="full main_wrapper">
	
	<div class="biography left half">
		<h2>Bio</h2>
		${artist.bio}
	</div>
	
	<div class="artist_meta right half">
		<h2>Meta Facts</h2>
		<ul>
			<c:forEach var="metaTidbit" items="${artist.meta}">
				<li>${metaTidbit.key}: ${metaTidbit.value}</li>
			</c:forEach>
		</ul>	
	</div>
	
	<div class="discography full">
		<h2>Discography</h2>
		hoppet over :D:D:D:D:D
		<div class="album_list_wrapper">
			<button class="prev">Previous</button>
			<button class="next">Next</button>
			<div class="album_list_carousel">
				<ul class="album_list">
					<c:forEach var="album" items="${artist.discography}">
						<li class="album">
							<h3><a href="">${album.name}</a></h3>
							<img src="${album.image}" alt="" />
							<span class="album_year">${album.year}</span>
							<span class="album_publisher">${album.label}</span>
							<div class="album_tracks">
								<h4>Tracks</h4>
								<ul class="track_list">
									<c:forEach var="track" items="${album.tracks}">
										<li><a href="">${track}</a></li>
									</c:forEach>
								</ul>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	
	<div class="concert full">
		<h2>Upcoming concerts</h2>
		<div class="concert_map">
			<div id="concert_map"></div>
		</div>
		<table class="concert_table full">
			<tr>
				<th>Date</th>
				<th>Location</th>
				<th>Venue</th>
			</tr>
			<tr>
				<td>20.10.2011</td>
				<td>London, UK</td>
				<td>Some Venue</td>
			</tr>
			<tr>
				<td>20.10.2011</td>
				<td>London, UK</td>
				<td>Some Venue</td>
			</tr>
			<tr>
				<td>20.10.2011</td>
				<td>London, UK</td>
				<td>Some Venue</td>
			</tr>
			<tr>
				<td>20.10.2011</td>
				<td>London, UK</td>
				<td>Some Venue</td>
			</tr>
			<tr>
				<td>20.10.2011</td>
				<td>London, UK</td>
				<td>Some Venue</td>
			</tr>
		</table>
	</div>
	
	<div class="related_artists full">
		<h2>Related Artists</h2>
		<ul class="artist_list">
			<c:forEach var="relatedArtist" items="${artist.similar}">
				<li><a href="search/${relatedArtist.name}">${relatedArtist.name}</a></li>
			</c:forEach>
		</ul>
	</div>
</div>

<jsp:include page="includes/footer.jsp" />
