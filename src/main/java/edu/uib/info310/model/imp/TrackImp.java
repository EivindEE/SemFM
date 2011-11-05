package edu.uib.info310.model.imp;

import java.util.List;

import edu.uib.info310.model.Track;

public class TrackImp {
	private String trackNr;
	private String name;
	private String length;
	private String artist;

	
	
	public String getTrackNr() {
		return this.trackNr;
	}

	public String getName() {
		return this.name;
	}

	public String getlenght() {
		return this.length;
	}

	public String getArtist() {
		return this.artist;
	}

	public void setTrackNr(String trackNr) {
		this.trackNr = trackNr;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

}
