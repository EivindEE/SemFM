package edu.uib.info310.model.imp;

import java.util.List;

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

}
