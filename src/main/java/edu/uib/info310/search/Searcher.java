package edu.uib.info310.search;

import edu.uib.info310.model.Artist;
import edu.uib.info310.model.Event;
import edu.uib.info310.model.Record;
import edu.uib.info310.model.Track;

public interface Searcher {

	public Artist searchArtist(String search_string);
	
	public Event searchEvent(String search_string);
	
	public Record searchRecord(String search_string);
	
	public Track searchTrack(String search_string);

}
