package edu.uib.info310.model;

public interface Track extends Comparable<Track> {
	
	public String getTrackNr();
	
	public String getName();
	
	public String getLength();
	
	public String getArtist();
	
	public void setTrackNr(String trackNr);
	
	public void setName(String name);
	
	public void setLength(String length);
	
	public void setArtist(String artist);
}
