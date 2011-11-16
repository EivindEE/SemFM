package edu.uib.info310.search;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import edu.uib.info310.model.Artist;
import edu.uib.info310.model.Event;
import edu.uib.info310.model.Record;
import edu.uib.info310.model.Track;
import edu.uib.info310.model.mock.MockArtist;
import edu.uib.info310.model.mock.MockEvent;
import edu.uib.info310.model.mock.MockRecord;
import edu.uib.info310.model.mock.MockTrack;

public class MockSearcher implements Searcher {

	public Artist searchArtist(String search_string) {
		
		return new MockArtist();
	}

	public Event searchEvent(String search_string) {
		return new MockEvent();
	}

	public Record searchRecord(String record_name, String artist_name) {
		return new MockRecord();
	}

	public Track searchTrack(String search_string) {
		return new MockTrack();
	}

	public List<Record> searchRecords(String q) {
		return Collections.emptyList();
	}
}
