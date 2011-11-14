package edu.uib.info310.model.imp;

import java.util.List;
import java.util.Map;

import edu.uib.info310.model.Artist;
import edu.uib.info310.model.Record;
import edu.uib.info310.model.Track;

public class RecordImp implements Record{
	private String id;
	private String name;
	private String image;
	private String description;
	private List<Track> tracks;
	private List<Artist> artist;
	private List<Record> relatedRecords;
	private String itunesLink;
	private List<String> reviews;
	private List<String> genres;
	private String spotifyUri;
	private Map<String, Object> meta;
	private String itunesPreview;
	private String year;
	private String label;
	private String discogId;
	
	
	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getImage() {
		return this.image;
	}
	
	public String getDescription() {
		return this.description;
	}

	public List<Record> getRelatedRecords() {
		return this.relatedRecords;
	}
	
	public List<Artist> getArtist() {
		return this.artist;
	}
	
	public String getLabel() {
		return this.itunesLink;
	}
	
	public String getItunesLink() {
		return this.itunesLink;
	}

	public List<Track> getTracks() {
		return this.tracks;
	}
	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setImage(String image) {
		this.image = image;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setTrack(List<Track> track) {
		this.tracks = track;
	}
	
	public void setArtist(List<Artist> artist) {
		this.artist = artist;
	}
	
	public void setRelatedRecords(List<Record> relatedRecord) {
		this.relatedRecords = relatedRecord;
	}
	
	public void setItunesLink(String itunesLink) {
		this.itunesLink = itunesLink;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}

	public List<String> getReviews() {
		return this.reviews;
	}

	public List<String> getGenres() {
		return this.genres;
	}

	public String getSpotifyUri() {
		return this.spotifyUri;
	}

	public Map<String, Object> getMeta() {
		return this.meta;
	}

	public String getItunesPreview() {
		return this.itunesPreview;
	}

	public List<Record> getRelatedRecord() {
		return relatedRecords;
	}

	public void setReviews(List<String> reviews) {
		this.reviews = reviews;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	public void setSpotifyUri(String spotifyUri) {
		this.spotifyUri = spotifyUri;
	}

	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}

	public void setItunesPreview(String itunesPreview) {
		this.itunesPreview = itunesPreview;
	}
	public void setDiscogId(String discogId) {
		this.discogId = discogId;
	}

	public String getYear() {
		return this.year;
	}
	
	public String getDiscogId() {
		return this.discogId;
	}
	
	public void setYear(String year){
		this.year = year;
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}
}
