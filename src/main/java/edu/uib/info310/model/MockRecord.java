package edu.uib.info310.model;

import java.util.LinkedList;
import java.util.List;

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
	
	public List<String> getTracks(){
		List<String> tracks = new LinkedList<String>();
		tracks.add("Some track");
		tracks.add("Some other track");
		tracks.add("Some third track");
		tracks.add("Some more tracks");
		return tracks;
	}

}
