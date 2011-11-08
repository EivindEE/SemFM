package edu.uib.info310.model;

import java.util.List;


public interface Record {
	
	public String getId();
	
	public String getName();
	
	public String getImage();
	
	public String getYear();
	
	public String getLabel();
	
	public String getDescription();
	
	public List<Track> getTracks();

}
