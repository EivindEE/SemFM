package edu.uib.info310.model.factory;

import org.springframework.stereotype.Controller;

import edu.uib.info310.model.Artist;
import edu.uib.info310.model.Event;
import edu.uib.info310.model.Record;
import edu.uib.info310.model.Track;
import edu.uib.info310.model.imp.ArtistImpl;
import edu.uib.info310.model.imp.EventImpl;
import edu.uib.info310.model.imp.RecordImp;
import edu.uib.info310.model.imp.TrackImp;

@Controller
public class ModelFactoryImpl implements ModelFactory {

	public ModelFactoryImpl(){
		
	}
	
	public Artist createArtist() {
		return new ArtistImpl();
	}

	public Record createRecord() {
		return new RecordImp();
	}

	public Track createTrack() {
		return new TrackImp();
	}

	public Event createEvent() {
		return new EventImpl();
	}

}
