package edu.uib.info310.search;

import java.util.List;

import edu.uib.info310.exception.ArtistNotFoundException;
import edu.uib.info310.exception.MasterNotFoundException;
import edu.uib.info310.model.Artist;
import edu.uib.info310.model.Record;

public interface Searcher {

	public Artist searchArtist(String search_string) throws ArtistNotFoundException;
	
	public  Record searchRecord(String record_name,String artist_name) throws MasterNotFoundException;

	public List<Record> searchRecords(String q);

}
