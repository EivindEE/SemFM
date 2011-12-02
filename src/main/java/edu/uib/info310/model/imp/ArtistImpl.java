package edu.uib.info310.model.imp;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openjena.atlas.json.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hp.hpl.jena.rdf.model.Model;

import edu.uib.info310.exception.NoSuchFormatException;
import edu.uib.info310.model.Artist;
import edu.uib.info310.model.Event;
import edu.uib.info310.model.Record;

@Component
public class ArtistImpl implements Artist {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArtistImpl.class);
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
	private Model model;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ArtistImpl other = (ArtistImpl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public String getModel(String format) throws NoSuchFormatException {
		if(format.equals("json"))
			return this.getJson().toString();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		if(format.equals("ttl")|| format.equals("turtle")){
			model.write(out, "TURTLE");
		}
		else if(format.equals("xml")|| format.equals("true")){
			model.write(out, "RDF/XML");
		}
		else if(format.equals("n3")){
			model.write(out, "N3");
		}
		else if(format.equals("xml-abr")){
			model.write(out, "RDF/XML-ABBREV");
		}
		else{
			throw new NoSuchFormatException();
		}
		return out.toString();
	}

	public JSONObject getJson(){
		JSONObject json = new JSONObject();
		try {
			json.accumulate("id", id);
			json.accumulate("name", name);
			json.accumulate("shortDescription", shortDescription);
			json.accumulate("description", description);
			json.accumulate("bio", bio);
			json.accumulate("image", image);
			if(discography != null && !discography.isEmpty()){
				JSONArray jdiscography = new JSONArray();
				for(Record rec : discography){
					jdiscography.put(rec.getJson());
				}
				json.accumulate("discography", jdiscography);
			}
			if(similar != null && !similar.isEmpty()){
				JSONArray jsimilar = new JSONArray();
				for(Artist sim : similar){
					jsimilar.put(sim.getJson());
				}
				json.accumulate("similar", jsimilar);
			}

			if(events != null && !events.isEmpty()){
				JSONArray jevents = new JSONArray();
				for(Event event : events){
					jevents.put(event.getJson());
				}
				json.accumulate("events", jevents);
			}

			json.accumulate("meta", meta);
		} catch (JSONException e) {
			LOGGER.error("Caught a JSONException: " + e.getStackTrace().toString());
		}
		return json;

	}

}
