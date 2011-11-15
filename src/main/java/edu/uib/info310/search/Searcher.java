package edu.uib.info310.search;

import java.util.Map;

import edu.uib.info310.model.Artist;
import edu.uib.info310.model.Event;
import edu.uib.info310.model.Record;
import edu.uib.info310.model.Track;

public interface Searcher {

	public Artist searchArtist(String search_string) throws ArtistNotFoundException;
	
	public Event searchEvent(String search_string);
	
	public  Record searchRecord(String record_name,String artist_name) throws MasterNotFoundException;
	
	public Track searchTrack(String search_string);

	public Map<String, Record> searchRecords(String q);

}
