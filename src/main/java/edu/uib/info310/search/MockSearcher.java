package edu.uib.info310.search;

import org.springframework.stereotype.Component;

import edu.uib.info310.model.Artist;
import edu.uib.info310.model.Event;
import edu.uib.info310.model.Record;
import edu.uib.info310.model.Track;
import edu.uib.info310.model.mock.MockArtist;
import edu.uib.info310.model.mock.MockEvent;
import edu.uib.info310.model.mock.MockRecord;
import edu.uib.info310.model.mock.MockTrack;
@Component
public class MockSearcher implements Searcher {

	public Artist searchArtist(String search_string) {
		return new MockArtist();
	}

	public Event searchEvent(String search_string) {
		return new MockEvent();
	}

	public Record searchRecord(String search_string) {
		return new MockRecord();
	}

	public Track searchTrack(String search_string) {
		return new MockTrack();
	}
}
