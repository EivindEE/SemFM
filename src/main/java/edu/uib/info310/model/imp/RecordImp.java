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
	private String year;
	private String label;
	private String description;
	private List<Track> track;
	private List<Artist> artist;
	private List<Record> relatedRecord;
	private String itunesLink;
	private String playTime;
	private List<String> reviews;
	private List<String> genres;
	private String spotifyUri;
	private Map<String, Object> meta;
	private String itunesPreview;

	
	
	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getImage() {
		return this.image;
	}

	public String getYear() {
		return this.year;
	}

	public String getLabel() {
		return this.label;
	}
	
	public String getDescription() {
		return this.description;
	}

	public List<Record> getRelatedRecords() {
		return this.relatedRecord;
	}
	
	public List<Artist> getArtist() {
		return this.artist;
	}
	
	public String getItunesLink() {
		return this.itunesLink;
	}

	public List<Track> getTracks() {
		return this.track;
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

	public void setYear(String year) {
		this.year = year;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setTrack(List<Track> track) {
		this.track = track;
	}
	
	public void setArtist(List<Artist> artist) {
		this.artist = artist;
	}
	
	public void setRelatedRecord(List<Record> relatedRecord) {
		this.relatedRecord = relatedRecord;
	}
	
	public void setItunesLink(String itunesLink) {
		this.itunesLink = itunesLink;
	}

	public void setPlaytime(String time) {
		this.playTime = time;
	}

	
	public String getPlaytime() {
		return this.playTime;
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

	public String getPlayTime() {
		return playTime;
	}

	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}

	public List<Track> getTrack() {
		return track;
	}

	public List<Record> getRelatedRecord() {
		return relatedRecord;
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

}
