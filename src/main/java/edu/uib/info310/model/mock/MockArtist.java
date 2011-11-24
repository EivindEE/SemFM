package edu.uib.info310.model.mock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import edu.uib.info310.model.Artist;
import edu.uib.info310.model.Event;
import edu.uib.info310.model.Record;

public class MockArtist implements Artist {

	public String getName() {return "Rihanna";
	}

	public String getBio() {return "Robyn Rihanna Fenty (born February 20, 1988), better known as simply Rihanna, is a Barbadian R&B recording artist. Born in Saint Michael, Barbados, Rihanna moved to the United States at the age of 16 to pursue a recording career under the guidance of record producer Evan Rogers. She subsequently signed a contract with Def Jam Recordings after auditioning for then-label head Jay-Z.\nIn 2005, Rihanna released her debut studio album, Music of the Sun, which peaked in the top ten of the Billboard 200 chart and features the Billboard Hot 100 hit single “Pon de Replay.” Less than a year later, she released her second studio album, A Girl Like Me (2006), which peaked within the top five of the Billboard albums chart, and produced her first Hot 100 number one single, “SOS”. Rihanna’s third studio album, Good Girl Gone Bad (2007), spawned four chart-topping singles “Umbrella”, “Take a Bow”, “Disturbia” and “Don’t Stop the Music”, and was nominated for nine Grammy Awards, winning Best Rap/Sung Collaboration for “Umbrella,” which features Jay-Z. Her fourth studio album Rated R, released in November 2009, produced the top 10 singles “Russian Roulette”, “Hard” and “Rude Boy”, which achieved the number-one spot on the Billboard Hot 100. Loud (2010), her fifth studio album, contains the number-one hits “Only Girl (In the World)”, “What’s My Name?” and “S&M”. “We Found Love” was released in September 2011 as the lead single from her sixth studio album, Talk That Talk, which is set to be released in November 2011.";
	}

	public String getImage() {return "http://konsepter.thunemedia.no/SemFM/skisse/rihanna.jpg";
	}

	public List<Record> getDiscography() {
return createDiscography();
	}

	public List<Artist> getSimilar() {return createSimilar();
	}

	public List<Event> getEvents() {return createEvents();
	}

	public Map<String, Object> getMeta() {return createMeta();
	}

	private Map<String, Object> createMeta() {/*ignore*/	Map<String, Object> meta = new HashMap<String, Object>();/*ignore*/	meta.put("Age", "22");/*ignore*/	meta.put("Sex", "Female");/*ignore*/	meta.put("Born", "Alebama");/*ignore*/	meta.put("Lives", "New York");/*ignore*/	meta.put("Favorite food", "Taco");return meta;
	}

	private List<Record> createDiscography() {/*ignore*/	List<Record> records = new LinkedList<Record>();/*ignore*/	records.add(new MockRecord());/*ignore*/	records.add(new MockRecord());/*ignore*/	records.add(new MockRecord());/*ignore*/	records.add(new MockRecord());/*ignore*/	records.add(new MockRecord());return records;
	}

	private List<Artist> createSimilar() {/*ignore*/	List<Artist> similar = new LinkedList<Artist>();/*ignore*/	similar.add(new MockArtist());/*ignore*/	similar.add(new MockArtist());/*ignore*/	similar.add(new MockArtist());/*ignore*/	similar.add(new MockArtist());/*ignore*/	similar.add(new MockArtist());return similar;
	}

	private List<Event> createEvents() {/*ignore*/	List<Event> events = new LinkedList<Event>();/*ignore*/	events.add(new MockEvent());/*ignore*/	events.add(new MockEvent());/*ignore*/	events.add(new MockEvent());/*ignore*/	events.add(new MockEvent());/*ignore*/	events.add(new MockEvent());/*ignore*/	events.add(new MockEvent());return events;
	}

	public String getShortDescription() {return "Singer, Songwriter";
	}

	public String getDescription() {return getBio();
	}

	public String getId() {return "http://dbpedia.org/page/Rihanna";
	}

	public String getSafeName() {return this.getName();
	}

	public List<Event> getLocatedEvents() {return getEvents();}

	public void setId(String id) {/*ignore*/}

	public void setName(String name) {/*ignore*/	}

	public void setShortDescription(String shortDescription) {/*ignore*/	}

	public void setDescription(String Description) {/*ignore*/	}

	public void setBio(String bio) {/*ignore*/	}

	public void setImage(String image) {/*ignore*/	}

	public void setDiscography(List<Record> discography) {/*ignore*/	}

	public void setSimilar(List<Artist> similar) {/*ignore*/	}

	public void setEvents(List<Event> events) {/*ignore*/	}

	public void setMeta(Map<String, Object> meta) {/*ignore*/	}

	public void setModel(String string) {
		// TODO Auto-generated method stub
		
	}

	public String getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public JSONObject getJson() {
		// TODO Auto-generated method stub
		return null;
	}
}
