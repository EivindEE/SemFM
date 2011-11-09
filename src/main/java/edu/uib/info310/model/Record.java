package edu.uib.info310.model;

import java.util.List;
import java.util.Map;


public interface Record {
	
	public String getId();
	
	public String getName();
	
	public String getImage();
	
	public String getYear();
	
	public String getDescription();
	
	public String getItunesLink();
	
	public List<Artist> getArtist();
	
	public List<Track> getTracks();
	
	public List<Record> getRelatedRecords();
	
	public List<String> getReviews();
	
	public List<String> getGenres();
	
	public String getSpotifyUri();
	
	public Map<String,Object> getMeta();
	
	public String getItunesPreview();
	
	

}
