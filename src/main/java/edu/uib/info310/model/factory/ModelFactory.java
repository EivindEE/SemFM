package edu.uib.info310.model.factory;

import org.springframework.stereotype.Controller;

import edu.uib.info310.model.Artist;
import edu.uib.info310.model.Event;
import edu.uib.info310.model.Record;
import edu.uib.info310.model.Track;

@Controller
public interface ModelFactory {
	
	public Artist createArtist();
	
	public Record createRecord();
	
	public Track createTrack();
	
	public Event createEvent();
}
