package edu.uib.info310.model.mock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import edu.uib.info310.model.Artist;
import edu.uib.info310.model.Record;
import edu.uib.info310.model.Track;


public class MockRecord implements Record {

	public String getName() {
		return "Music of the Sun";
	}

	public String getImage() {
		return "http://images.uulyrics.com/cover/r/rihanna/album-music-of-the-sun.jpg";
	}
	
	public List<Track> getTracks(){
		List<Track> tracks = new LinkedList<Track>();
		tracks.add(new MockTrack());
		tracks.add(new MockTrack());
		tracks.add(new MockTrack());
		tracks.add(new MockTrack());
		return tracks;
	}

	public String getId() {
		return "http://dbpedia.org/resource/Music_of_the_Sun";
	}
	public String getItunesLink() {
		return "http://itunes.apple.com/";
	}
	public String getDescription() {
		return "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
	}
	
	public List<Artist> getArtist(){
		List<Artist> artists = new LinkedList<Artist>();
		artists.add(new MockArtist());
		return artists;
	}
	
	public List<Record> getRelatedRecords(){
		List<Record> records = new LinkedList<Record>();
		records.add(new MockRecord());
		records.add(new MockRecord());
		records.add(new MockRecord());
		records.add(new MockRecord());
		records.add(new MockRecord());
		records.add(new MockRecord());
		records.add(new MockRecord());
		records.add(new MockRecord());
		return records;
	}

	public List<String> getReviews() {
		LinkedList<String> reviews = new LinkedList<String>();
		reviews.add("A review is an evaluation of a publication, a product or a service, such as a movie (a movie review), video game, musical composition (music review of a composition or recording), book (book review); a piece of hardware like a car, home appliance, or computer; or an event or performance, such as a live music concert, a play, musical theater show or dance show. In addition to a critical evaluation, the review's author may assign the work a rating to indicate its relative merit. More loosely, an author may review current events, trends, or items in the news. A compilation of reviews may itself be called a review. The New York Review of Books, for instance, is a collection of essays on literature, culture, and current affairs. National Review, founded by William F. Buckley, Jr., is an influential conservative magazine, and Monthly Review is a long-running socialist periodical.");
		reviews.add("The Wireless Speed Wheel is marred by a couple of bad design decisions, and the effort of holding it in racing position can make longer racing sessions tiring. Yet it’s accurate and responsive, and it does make playing Forza 4 – and other racers – a more exciting and immersive experience. What’s more, it’s an awful lot more convenient to pick up, play and put away than a proper racing wheel setup. ");
		return reviews; 
	}

	public HashMap<String,String> getGenres() {
		HashMap<String,String> genres = new HashMap<String,String>();
		genres.put("Pop", "pop");
		genres.put("Classical", "classical");
		return genres;
	}

	public String getSpotifyUri() {
		return "http://open.spotify.com/track/1x6ACsKV4UdWS2FMuPFUiT";
	}

	public Map<String, Object> getMeta() {
		HashMap<String, Object> meta = new HashMap<String, Object>();
		meta.put("Producer", "Tim Alen");
		meta.put("Year", "2008");
		meta.put("Playtime", "1H 20M");
		meta.put("Label", "EMU");
		return meta;
	}

	public String getItunesPreview() {
		return "http://a1099.itunes.apple.com/r10/Music/f9/54/43/mzi.gqvqlvcq.aac.p.m4p";
	}

	public String getYear() {
		return "2008";
	}
	
	public String getLabel() {
		return "EMI";
	}
	
	public String getDiscogId() {
		return "sexydiscogid";
	}

	public void setId(String id) {}

	public void setName(String name) { /*ignore*/}

	public void setImage(String image) { /*ignore*/}

	public void setYear(String year) { /*ignore*/}

	public void setLabel(String label) { /*ignore*/}

	public void setDescription(String description) { /*ignore*/}

	public void setItunesLink(String itunesLink) { /*ignore*/}

	public void setArtist(List<Artist> artists) { /*ignore*/}

	public void setTracks(List<Track> tracks) { /*ignore*/}

	public void setRelatedRecords(List<Record> relatedRecords) { /*ignore*/}

	public void setReviews(List<String> reviews) { /*ignore*/}

	public void setGenres(HashMap<String,String> genres) { /*ignore*/}

	public void setSpotifyUri(String spotifyUri) { /*ignore*/}

	public void setMeta(Map<String, Object> meta) { /*ignore*/}

	public void setDiscogId(String discogId) { /*ignore*/}

	public void setItunesPreview(String itunesPreview) { /*ignore*/}

	public void setModel(String string) {
		// TODO Auto-generated method stub
		
	}

	public String getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public JSONObject getJson() {
		// TODO Auto-generated method stub
		return null;
	}

}
