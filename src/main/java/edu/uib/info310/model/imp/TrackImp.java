package edu.uib.info310.model.imp;

import org.springframework.stereotype.Component;

import edu.uib.info310.model.Event;
import edu.uib.info310.model.Track;

@Component
public class TrackImp implements Track {
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

	public String getLength() {
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

	public int compareTo(Track o) {
		return this.trackNr.compareTo(o.getTrackNr());
	}

}
