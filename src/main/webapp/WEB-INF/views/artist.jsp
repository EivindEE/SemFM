<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<jsp:include page="includes/header.jsp" />
<div id="header" class="cf">
	<a href="#" id="logo"><img src="resources/images/SemFM-small.png" alt="" /></a>
	<form id="search_form" action="search" method="post">
		<input type="text" value="Search term" />
		<button>Submit Search</button>
	</form>
	<ul class="header_meta_links">
		<li><a href="">our sources</a></li>
		<li><a href="">help</a></li>
	</ul>
</div>

<div class="headline_wrapper">
	<div class="headline">
		<img src="rihanna.jpg" alt="" class="search_result_image" />
		<div class="h1-wrapper">
			<h1><!--  get name  -->Rihanna</h1>
			<span class="h1-description"><!-- get short desc -->Singer, songwriter</span>
		</div>
	</div>
</div>

<div class="full main_wrapper">
	
	<div class="biography left half">
		<h2>Bio</h2>
		<!--  get description -->
		<p>Robyn Rihanna Fenty (born February 20, 1988), better known as simply Rihanna, is a Barbadian R&B recording artist. Born in Saint Michael, Barbados, Rihanna moved to the United States at the age of 16 to pursue a recording career under the guidance of record producer Evan Rogers. She subsequently signed a contract with Def Jam Recordings after auditioning for then-label head Jay-Z.</p>
		<p>In 2005, Rihanna released her debut studio album, Music of the Sun, which peaked in the top ten of the Billboard 200 chart and features the Billboard Hot 100 hit single “Pon de Replay.” Less than a year later, she released her second studio album, A Girl Like Me (2006), which peaked within the top five of the Billboard albums chart, and produced her first Hot 100 number one single, “SOS”. Rihanna’s third studio album, Good Girl Gone Bad (2007), spawned four chart-topping singles “Umbrella”, “Take a Bow”, “Disturbia” and “Don’t Stop the Music”, and was nominated for nine Grammy Awards, winning Best Rap/Sung Collaboration for “Umbrella,” which features Jay-Z. Her fourth studio album Rated R, released in November 2009, produced the top 10 singles “Russian Roulette”, “Hard” and “Rude Boy”, which achieved the number-one spot on the Billboard Hot 100. Loud (2010), her fifth studio album, contains the number-one hits “Only Girl (In the World)”, “What’s My Name?” and “S&M”. “We Found Love” was released in September 2011 as the lead single from her sixth studio album, Talk That Talk, which is set to be released in November 2011.</p>
	</div>
	
	<div class="artist_meta right half">
		<h2>Meta Facts</h2>
		<ul>
			<!--  loop through meta hash table -->
			<li>Age: Lorem</li>
			<li>Sex: Ipsum</li>
			<li>Something: Dolorem</li>
			<li>Something completely different: Sit</li>
			<li>Bleh: Amet</li>
			<li>Age: Lorem</li>
			<li>Sex: Ipsum</li>
			<li>Something: Dolorem</li>
			<li>Something completely different: Sit</li>
			<li>Bleh: Amet</li>
			<li>Age: Lorem</li>
			<li>Sex: Ipsum</li>
			<li>Something: Dolorem</li>
			<li>Something completely different: Sit</li>
		</ul>	
	</div>
	
	<div class="discography full">
		<h2>Discography</h2>
		<div class="album_list_wrapper">
			<button class="prev">Previous</button>
			<button class="next">Next</button>
			<div class="album_list_carousel">
				<ul class="album_list">
					<li class="album">
						<h3><a href="">Album 1 med flott tittel</a></h3>
						<img src="OasisDefinitelyMaybealbumcover.jpg" alt="" />
						
						<span class="album_year">2011</span>
						<span class="album_publisher">EMI</span>
						<div class="album_tracks">
							<h4>Tracks</h4>
							<ul class="track_list">
								<li><a href="">Song 1</a> - 240s - <a href="">Oasis</a> </li>
								<li><a href="">Song 2</a> - 400s - <a href="">Oasis</a></li>
								<li><a href="">Song 3</a> - 120s - <a href="">Oasis</a></li>
							</ul>
						</div>
					</li>
					<li class="album">
						<h3><a href="">Album 2 med flott tittel</a></h3>
						<img src="OasisDefinitelyMaybealbumcover.jpg" alt="" />
						
						<span class="album_year">2011</span>
						<span class="album_publisher">EMI</span>
						<div class="album_tracks">
							<h4>Tracks</h4>
							<ul class="track_list">
								<li><a href="">Song 1</a> - 240s - <a href="">Oasis</a> </li>
								<li><a href="">Song 2</a> - 400s - <a href="">Oasis</a></li>
								<li><a href="">Song 3</a> - 120s - <a href="">Oasis</a></li>
							</ul>
						</div>
					</li>
					<li class="album">
						<h3><a href="">Album 3 med flott tittel</a></h3>
						<img src="OasisDefinitelyMaybealbumcover.jpg" alt="" />
						
						<span class="album_year">2011</span>
						<span class="album_publisher">EMI</span>
						<div class="album_tracks">
							<h4>Tracks</h4>
							<ul class="track_list">
								<li><a href="">Song 1</a> - 240s - <a href="">Oasis</a> </li>
								<li><a href="">Song 2</a> - 400s - <a href="">Oasis</a></li>
								<li><a href="">Song 3</a> - 120s - <a href="">Oasis</a></li>
							</ul>
						</div>
					</li>
					<li class="album">
						<h3><a href="">Album 4 med flott tittel</a></h3>
						<img src="OasisDefinitelyMaybealbumcover.jpg" alt="" />
						
						<span class="album_year">2011</span>
						<span class="album_publisher">EMI</span>
						<div class="album_tracks">
							<h4>Tracks</h4>
							<ul class="track_list">
								<li><a href="">Song 1</a> - 240s - <a href="">Oasis</a> </li>
								<li><a href="">Song 2</a> - 400s - <a href="">Oasis</a></li>
								<li><a href="">Song 3</a> - 120s - <a href="">Oasis</a></li>
							</ul>
						</div>
					</li>
					<li class="album">
						<h3><a href="">Album 5 med flott tittel</a></h3>
						<img src="OasisDefinitelyMaybealbumcover.jpg" alt="" />
						
						<span class="album_year">2011</span>
						<span class="album_publisher">EMI</span>
						<div class="album_tracks">
							<h4>Tracks</h4>
							<ul class="track_list">
								<li><a href="">Song 1</a> - 240s - <a href="">Oasis</a> </li>
								<li><a href="">Song 2</a> - 400s - <a href="">Oasis</a></li>
								<li><a href="">Song 3</a> - 120s - <a href="">Oasis</a></li>
							</ul>
						</div>
					</li>
					<li class="album">
						<h3><a href="">Album 6 med flott tittel</a></h3>
						<img src="OasisDefinitelyMaybealbumcover.jpg" alt="" />
						
						<span class="album_year">2011</span>
						<span class="album_publisher">EMI</span>
						<div class="album_tracks">
							<h4>Tracks</h4>
							<ul class="track_list">
								<li><a href="">Song 1</a> - 240s - <a href="">Oasis</a> </li>
								<li><a href="">Song 2</a> - 400s - <a href="">Oasis</a></li>
								<li><a href="">Song 3</a> - 120s - <a href="">Oasis</a></li>
							</ul>
						</div>
					</li>
					<li class="album">
						<h3><a href="">Album 7 med flott tittel</a></h3>
						<img src="OasisDefinitelyMaybealbumcover.jpg" alt="" />
						
						<span class="album_year">2011</span>
						<span class="album_publisher">EMI</span>
						<div class="album_tracks">
							<h4>Tracks</h4>
							<ul class="track_list">
								<li><a href="">Song 1</a> - 240s - <a href="">Oasis</a> </li>
								<li><a href="">Song 2</a> - 400s - <a href="">Oasis</a></li>
								<li><a href="">Song 3</a> - 120s - <a href="">Oasis</a></li>
							</ul>
						</div>
					</li>
					<li class="album">
						<h3><a href="">Album 8 med flott tittel</a></h3>
						<img src="OasisDefinitelyMaybealbumcover.jpg" alt="" />
						
						<span class="album_year">2011</span>
						<span class="album_publisher">EMI</span>
						<div class="album_tracks">
							<h4>Tracks</h4>
							<ul class="track_list">
								<li><a href="">Song 1</a> - 240s - <a href="">Oasis</a> </li>
								<li><a href="">Song 2</a> - 400s - <a href="">Oasis</a></li>
								<li><a href="">Song 3</a> - 120s - <a href="">Oasis</a></li>
							</ul>
						</div>
					</li><li class="album">
						<h3><a href="">Album 9 med flott tittel</a></h3>
						<img src="OasisDefinitelyMaybealbumcover.jpg" alt="" />
						
						<span class="album_year">2011</span>
						<span class="album_publisher">EMI</span>
						<div class="album_tracks">
							<h4>Tracks</h4>
							<ul class="track_list">
								<li><a href="">Song 1</a> - 240s - <a href="">Oasis</a> </li>
								<li><a href="">Song 2</a> - 400s - <a href="">Oasis</a></li>
								<li><a href="">Song 3</a> - 120s - <a href="">Oasis</a></li>
							</ul>
						</div>
					</li>
					<li class="album">
						<h3><a href="">Album 10 med flott tittel</a></h3>
						<img src="OasisDefinitelyMaybealbumcover.jpg" alt="" />
						
						<span class="album_year">2011</span>
						<span class="album_publisher">EMI</span>
						<div class="album_tracks">
							<h4>Tracks</h4>
							<ul class="track_list">
								<li><a href="">Song 1</a> - 240s - <a href="">Oasis</a> </li>
								<li><a href="">Song 2</a> - 400s - <a href="">Oasis</a></li>
								<li><a href="">Song 3</a> - 120s - <a href="">Oasis</a></li>
							</ul>
						</div>
					</li>
					
					<li class="album">
						<h3><a href="">Album 11 med flott tittel</a></h3>
						<img src="OasisDefinitelyMaybealbumcover.jpg" alt="" />
						<span class="album_year">2011</span>
						<span class="album_publisher">EMI</span>
						<div class="album_tracks">
							<h4>Tracks</h4>
							<ul class="track_list">
								<li><a href="">Song 1</a> - 240s - <a href="">Oasis</a> </li>
								<li><a href="">Song 2</a> - 400s - <a href="">Oasis</a></li>
								<li><a href="">Song 3</a> - 120s - <a href="">Oasis</a></li>
								<li><a href="">Song 1</a> - 240s - <a href="">Oasis</a> </li>
								<li><a href="">Song 2</a> - 400s - <a href="">Oasis</a></li>
								<li><a href="">Song 3</a> - 120s - <a href="">Oasis</a></li>
								<li><a href="">Song 1</a> - 240s - <a href="">Oasis</a> </li>
								<li><a href="">Song 2</a> - 400s - <a href="">Oasis</a></li>
								<li><a href="">Song 3</a> - 120s - <a href="">Oasis</a></li>
								<li><a href="">Song 1</a> - 240s - <a href="">Oasis</a> </li>
								<li><a href="">Song 2</a> - 400s - <a href="">Oasis</a></li>
								<li><a href="">Song 3</a> - 120s - <a href="">Oasis</a></li>
							</ul>
						</div>
					</li>
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
			<li><a href="">Some artist</a></li>
			<li><a href="">Some artist</a></li>
			<li><a href="">Some artist</a></li>
			<li><a href="">Some artist</a></li>
			<li><a href="">Some artist</a></li>
			<li><a href="">Some artist</a></li>
			<li><a href="">Some artist</a></li>
			<li><a href="">Some artist</a></li>
		</ul>
	</div>
</div>

<jsp:include page="includes/footer.jsp" />
