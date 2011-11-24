package edu.uib.info310.model.imp;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import edu.uib.info310.model.Artist;
import edu.uib.info310.model.Record;
import edu.uib.info310.model.Track;

@Component
public class RecordImp implements Record{
	private static final Logger LOGGER = LoggerFactory.getLogger(RecordImp.class);
	private String id;
	private String name;
	private String image;
	private String description;
	private List<Track> tracks;
	private List<Artist> artists;
	private List<Record> relatedRecords;
	private String itunesLink;
	private List<String> reviews;
	private HashMap<String, String> genres;
	private String spotifyUri;
	private Map<String, Object> meta;
	private String itunesPreview;
	private String year;
	private String label;
	private String discogId;
	private String model;


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
		return this.artists;
	}

	public String getLabel() {
		return this.label;
	}

	public String getItunesLink() {
		return this.itunesLink;
	}

	public List<Track> getTracks() {
		Collections.sort(this.tracks);
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
		this.artists = artist;
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

	public HashMap<String,String> getGenres() {
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

	public void setGenres(HashMap<String,String> genres) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artists == null) ? 0 : artists.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecordImp other = (RecordImp) obj;
		if (artists == null) {
			if (other.artists != null)
				return false;
		} else if (!artists.equals(other.artists))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public void setModel(String model) {
		this.model = model;

	}

	public String getModel() {
		return model;
	}

	public JSONObject getJson() {
		JSONObject json = new JSONObject();
		try{
		json.accumulate("id",id);
		json.accumulate("name", name);
		json.accumulate("image",image);
		json.accumulate("description", description);
		if( tracks != null && !tracks.isEmpty()){
			JSONArray jtracks = new JSONArray();
			for(Track track : tracks){
				jtracks.put(track.getJson());
			}
			json.accumulate("tracks",jtracks);
		}

		if(artists != null && !artists.isEmpty()){
			JSONArray jartist = new JSONArray();
			for(Artist artist: artists){
				jartist.put(artist.getJson());
			}
			json.accumulate("artists",jartist);
		}

		if(relatedRecords != null && !relatedRecords.isEmpty()){
			JSONArray jrecords = new JSONArray();
			for(Record rec: relatedRecords){
				jrecords.put(rec.getJson());
			}
			json.accumulate("relatedRecords",jrecords);
		}

		json.accumulate("itunesLink",itunesLink);

		if(reviews != null && !reviews.isEmpty()){
			JSONArray jreviews= new JSONArray(reviews);
			json.accumulate("reviews",jreviews);
		}

		if(genres != null && !genres.isEmpty()){
			JSONArray jgenres = new JSONArray(genres);
			json.accumulate("genres", genres);
		}

		json.accumulate("spotifyUri", spotifyUri);

		if(meta != null && !meta.isEmpty()){
			JSONArray jmeta = new JSONArray(meta);
			json.accumulate("meta", meta);
		}
		
		json.accumulate("itunesPreview", itunesPreview);
		json.accumulate("year", year);
		json.accumulate("label", label);
		
		json.accumulate("discogId", discogId);
		}
		catch (JSONException e) {
			LOGGER.error("Caught a JSONException: " + e.getStackTrace().toString());
		}
		return json;
	}


}
