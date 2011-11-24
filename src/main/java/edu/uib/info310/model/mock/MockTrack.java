package edu.uib.info310.model.mock;

import org.json.JSONObject;

import edu.uib.info310.model.Track;


public class MockTrack implements Track {
	private static Integer tracknr = new Integer(0);
	public String getTrackNr(){
		tracknr++;
		return  tracknr.toString();
	}
	
	public String getName() {
		return "Pon De Replay";
	}

	public String getLength() {
		return "02:21";
	}

	public String getArtist() {
		return "";
	}

	public void setTrackNr(String trackNr) {}

	public void setName(String name) {}

	public void setLength(String length) {}

	public void setArtist(String artist) {}
	
	public int compareTo(Track o) {/*ignore*/
		return 0;
	}

	public String getPreview() {
		return "http://a1.mzstatic.com/us/r2000/013/Music/d2/26/fa/mzm.spkoqslk.aac.p.m4a";
	}

	public void setPreview(String preview) {}

	public JSONObject getJson() {
		// TODO Auto-generated method stub
		return null;
	}

}
