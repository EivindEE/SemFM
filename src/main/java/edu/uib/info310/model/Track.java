package edu.uib.info310.model;

public interface Track {
	
	public Integer getTrackNr();
	
	public String getName();
	
	public String getLength();
	
	public String getArtist();
	
	public void setTrackNr(Integer trackNr);
	
	public void setName(String name);
	
	public void setLength(String length);
	
	public void setArtist(String artist);
}
