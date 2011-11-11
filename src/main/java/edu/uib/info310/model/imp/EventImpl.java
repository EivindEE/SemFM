package edu.uib.info310.model.imp;

import java.util.Date;

import edu.uib.info310.model.Event;

public class EventImpl implements Event {

	
	private String id;
	private Date date;
	private String location;
	private String venue;
	private String lng;
	private String lat;
	private String streetAddress;
	private String country;
	private String image;

	public String getId() {
		return this.id;
	}

	public Date getDate() {
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

	public void setDate(Date date) {
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

	public String getStreetAddress() {
		return this.streetAddress;
	}

	public String getCountry() {
		return this.country;
	}

	public String getImage() {
		return this.image;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int compareTo(Event o) {
		return this.date.compareTo(o.getDate());
	}

}
