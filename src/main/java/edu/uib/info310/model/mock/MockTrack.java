package edu.uib.info310.model.mock;

import edu.uib.info310.model.Track;


public class MockTrack implements Track {
	private static Integer tracknr = new Integer(0);
	public Integer getTrackNr(){
		return tracknr++;
	}
	
	public String getName() {
		return "Pon De Replay";
	}

	public String getLength() {
		return "02:21";
	}

	public String getArtist() {
		return "";
	}

	public void setTrackNr(Integer trackNr) {}

	public void setName(String name) {}

	public void setLength(String length) {}

	public void setArtist(String artist) {}

}
