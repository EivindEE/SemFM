package edu.uib.info310.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.hp.hpl.jena.rdf.model.Model;

import edu.uib.info310.exception.NoSuchFormatException;

public interface Artist {
	
	public String getId();
	
	public String getName();
	
	public String getSafeName();
	
	public String getShortDescription();
	
	public String getDescription();
	
	public String getBio();
	
	public String getImage();
	
	public List<Record> getDiscography();
	
	public List<Artist> getSimilar();
	
	public List<Event> getEvents();
	
	public List<Event> getLocatedEvents();
	
	public Map<String, Object> getMeta();
	
	public void setId(String id);
	
	public void setName(String name);
	
	public void setShortDescription(String shortDescription);
	
	public void setDescription(String Description);
	
	public void setBio(String bio);
	
	public void setImage(String image);
	
	public void setDiscography(List<Record> discography);
	
	public void setSimilar(List<Artist> similar);
	
	public void setEvents(List<Event> events);
	
	public void setMeta(Map<String, Object> meta);

	public void setModel(Model string);
	
	public String getModel(String format) throws NoSuchFormatException;

	public JSONObject getJson();

}
