package edu.uib.info310.model.mock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	
	public Map<String, Track> getTracks(){
		Map<String, Track> tracks = new HashMap<String, Track>();
		tracks.put(((Integer) tracks.size()).toString(),new MockTrack());
		tracks.put(((Integer) tracks.size()).toString(),new MockTrack());
		tracks.put(((Integer) tracks.size()).toString(),new MockTrack());
		tracks.put(((Integer) tracks.size()).toString(),new MockTrack());
		return tracks;
	}

	public String getId() {
		return "http://dbpedia.org/resource/Music_of_the_Sun";
	}

}
