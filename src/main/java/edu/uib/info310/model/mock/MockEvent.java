package edu.uib.info310.model.mock;

import java.util.Date;
import java.util.Random;

import org.json.JSONObject;

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

	public int compareTo(Event o) {/*ignore*/
		return 0;
	}

	public String getWebsite() {/*ignore*/
		return null;
	}

	public void setId(String id) {/*ignore*/}

	public void setDate(Date date) {/*ignore*/}

	public void setLocation(String location) {/*ignore*/}

	public void setVenue(String venue) {/*ignore*/}

	public void setWebsite(String website) {/*ignore*/}

	public void setLng(String lng) {/*ignore*/}

	public void setLat(String lat) {/*ignore*/}

	public void setStreetAddress(String streetAddress) {/*ignore*/}

	public void setCountry(String country) {/*ignore*/}

	public void setImage(String image) {/*ignore*/}

	public JSONObject getJson() {
		// TODO Auto-generated method stub
		return null;
	}

}
