package edu.uib.info310.model.mock;

import java.util.Date;
import java.util.Random;

import edu.uib.info310.model.Event;

public class MockEvent implements Event {

	public Date getDate() {
		return new Date();
	}

	public String getLocation() {
		return "Bergen";
	}

	public String getVenue() {
		return "Hulen";
	}

	public String getLng() {
		Random r = new Random();
		
		return "5.3" + r.nextInt(100)*7000;
	}

	public String getLat() {
		Random r = new Random();
		
		return "60.39" + r.nextInt(100)*7000; 
	}

	public String getId() {
		return "http://www.last.fm/event/1767496+Rihanna+at+Oslo+Spektrum+on+30+October+2011";
	}

	public String getStreetAddress() {
		return "Kalvedalsveien 47";
	}

	public String getCountry() {
		return "Norway";
	}

	public String getImage() {
		return "http://userserve-ak.last.fm/serve/252/53005163.jpg";
	}

	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getWebsite() {
		// TODO Auto-generated method stub
		return null;
	}

}
