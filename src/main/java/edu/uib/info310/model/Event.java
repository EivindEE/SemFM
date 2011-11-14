package edu.uib.info310.model;

import java.util.Date;

public interface Event extends Comparable<Event> {
	
	public String getId();
	
	public Date getDate();
	
	public String getLocation();
	
	public String getVenue();
	
	public String getWebsite();
	
	public String getLng();
	
	public String getLat();
	
	public String getStreetAddress();
	
	public String getCountry();
	
	public String getImage();
	
	public void setId(String id);
	
	public void setDate(Date date);
	
	public void setLocation(String location);
	
	public void setVenue(String venue);
	
	public void setWebsite(String website);
	
	public void setLng(String lng);
	
	public void setLat(String lat);
	
	public void setStreetAddress(String streetAddress);
	
	public void setCountry(String country);
	
	public void setImage(String image);
}
