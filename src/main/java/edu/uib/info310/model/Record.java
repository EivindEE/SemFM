package edu.uib.info310.model;

import java.util.List;
import java.util.Map;


public interface Record {
	
	public String getId();
	
	public String getName();
	
	public String getImage();
	
	public String getYear();
	
	public String getLable();
	
	public Map<String,Track> getTracks();

}
