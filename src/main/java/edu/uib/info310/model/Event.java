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
}
