package edu.uib.info310.model.imp;

import edu.uib.info310.model.Event;

public class EventImpl implements Event {

	
	private String id;
	private String date;
	private String location;
	private String venue;
	private String lng;
	private String lat;

	public String getId() {
		return this.id;
	}

	public String getDate() {
		return this.date;
	}

	public String getLocation() {
		return this.location;
	}

	public String getVenue() {
		return this.venue;
	}

	public String getLng() {
		return this.lng;
	}

	public String getLat() {
		return this.lat;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

}
