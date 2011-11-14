package edu.uib.info310.model.imp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import edu.uib.info310.model.Artist;
import edu.uib.info310.model.Event;
import edu.uib.info310.model.Record;

@Component
public class ArtistImpl implements Artist {

	private String id;
	private String name;
	private String shortDescription;
	private String description;
	private String bio;
	private String image;
	private List<Record> discography;
	private List<Artist> similar;
	private List<Event> events;
	private Map<String, Object> meta;

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getShortDescription() {
		return this.shortDescription;
	}

	public String getDescription() {
		return this.description;
	}

	public String getBio() {
		return this.bio;
	}

	public String getImage() {
		return this.image;
	}

	public List<Record> getDiscography() {
		return this.discography;
	}

	public List<Artist> getSimilar() {
		return this.similar;
	}

	public List<Event> getEvents() {
		return this.events;
	}

	public Map<String, Object> getMeta() {
		return this.meta;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setDiscography(List<Record> discography) {
		this.discography = discography;
	}

	public void setSimilar(List<Artist> similar) {
		this.similar = similar;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
		Collections.sort(this.events);
	}

	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}

	public String getSafeName() {
		try {
			return URLEncoder.encode(name, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return name;
		}
	}

	public List<Event> getLocatedEvents() {
		List<Event> locatedEvents = new LinkedList<Event>();
		for(Event e: events){
			if((e.getLat() == null || e.getLng() == null) || (e.getLat().equals("") || e.getLng().equals(""))){
				continue;
		}
			locatedEvents.add(e);
		}
		return locatedEvents;
	}

}
