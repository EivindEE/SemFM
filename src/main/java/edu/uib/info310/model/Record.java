package edu.uib.info310.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface Record {

	public String getId();

	public String getName();

	public String getImage();

	public String getYear();

	public String getLabel();

	public String getDescription();

	public String getItunesLink();

	public List<Artist> getArtist();

	public List<Track> getTracks();

	public List<Record> getRelatedRecords();

	public List<String> getReviews();

	public HashMap<String,String> getGenres();

	public String getSpotifyUri();

	public Map<String,Object> getMeta();

	public String getDiscogId();

	public String getItunesPreview();

	public void setId(String id);

	public void setName(String name);

	public void setImage(String image);

	public void setYear(String year);

	public void setLabel(String label);

	public void setDescription(String description);

	public void setItunesLink(String itunesLink);

	public void setArtist(List<Artist> artists);

	public void  setTracks(List<Track> tracks);

	public  void setRelatedRecords(List<Record> relatedRecords);

	public void setReviews(List<String> reviews);

	public void setGenres(HashMap<String,String> genres);

	public void setSpotifyUri(String spotifyUri);

	public void setMeta(Map<String,Object> meta);

	public void setDiscogId(String discogId);

	public void setItunesPreview(String itunesPreview);



}
