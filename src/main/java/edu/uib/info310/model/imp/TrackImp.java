package edu.uib.info310.model.imp;

import edu.uib.info310.model.Track;

public class TrackImp implements Track {
	private Integer trackNr;
	private String name;
	private String length;
	private String artist;

	
	
	public Integer getTrackNr() {
		return this.trackNr;
	}

	public String getName() {
		return this.name;
	}

	public String getLength() {
		return this.length;
	}

	public String getArtist() {
		return this.artist;
	}

	public void setTrackNr(Integer trackNr) {
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
