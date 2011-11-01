package edu.uib.info310.model;

import java.util.Random;

public class MockEvent implements Event {

	public String getDate() {
		return "20.10.2011";
	}

	public String getLocation() {
		return "Bergen";
	}

	public String getVenue() {
		return "Hulen";
	}

	public String getLong() {
		Random r = new Random();
		
		return "60.39 " + r.nextInt()*7000;
	}

	public String getLat() {
		Random r = new Random();
		
		return "5.3" + r.nextInt()*7000;
	}

	public String getId() {
		return "http://www.last.fm/event/1767496+Rihanna+at+Oslo+Spektrum+on+30+October+2011";
	}

}
