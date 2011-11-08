package edu.uib.info310.model.mock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

	public String getYear() {
		return "2005";
	}

	public String getLabel() {
		
		return "Def Jam Recordings";
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
	
	public List<Record> getRelatedRecord(){
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

}
