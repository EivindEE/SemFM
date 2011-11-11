package edu.uib.info310.search;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

import edu.uib.info310.model.Artist;
import edu.uib.info310.model.Event;
import edu.uib.info310.model.Record;
import edu.uib.info310.model.Track;
import edu.uib.info310.model.imp.ArtistImpl;
import edu.uib.info310.model.imp.EventImpl;
import edu.uib.info310.model.imp.RecordImp;
import edu.uib.info310.model.mock.MockRecord;
import edu.uib.info310.search.builder.OntologyBuilder;

@Component
public class SearcherImpl implements Searcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearcherImpl.class);
	private OntologyBuilder builder = new OntologyBuilder();
	private Model model;
	private ArtistImpl artist;


	public Artist searchArtist(String search_string) throws ArtistNotFoundException {
		this.artist = new ArtistImpl();
		this.model = builder.createArtistOntology(search_string);
		LOGGER.debug("Size of infered model: " + model.size());

		setArtistIdAndName();

		setSimilarArtist();

		setArtistEvents();

		setArtistDiscography();

		setArtistInfo();

		return this.artist;
	}

	private void setArtistIdAndName() {
		String getIdStr = "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX mo:<http://purl.org/ontology/mo/> PREFIX foaf:<http://xmlns.com/foaf/0.1/>  SELECT ?id ?name WHERE {?id foaf:name ?name; mo:similar-to ?something.}";
		QueryExecution execution = QueryExecutionFactory.create(getIdStr, model);
		ResultSet similarResults = execution.execSelect();
		if(similarResults.hasNext()){
			QuerySolution solution = similarResults.next();
			this.artist.setId(solution.get("id").toString());
			this.artist.setName(solution.get("name").toString());
		}
		LOGGER.debug("Artist id set to: " + this.artist.getId() + ", Artist name set to: " + this.artist.getName());

	}

	private void setArtistDiscography() {
		List<Record> discog = new LinkedList<Record>();
		Map<String,Record> uniqueRecord = new HashMap<String, Record>();

		String getDiscographyStr = 	"PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
				"PREFIX mo: <http://purl.org/ontology/mo/> " +
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
				"PREFIX dc: <http://purl.org/dc/terms/> " + 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				"SELECT DISTINCT " +
				" ?artistId ?albumId ?release ?title ?image ?year ?labelId ?labelName ?track ?artist  "+
				" WHERE { " +
				//				"?artistId foaf:name  \"" + artist.getName() + "\". "+
				//"<" + this.artist.getId() + "> foaf:made ?albumId."+ 
				"?artistId foaf:made ?albumId. " +
				"?albumId dc:title ?title." +
				"OPTIONAL {?albumId mo:publisher ?labelId. } "+
				"OPTIONAL {?albumId dc:issued ?year. }" +
				"OPTIONAL {?albumId foaf:depiction ?image. }" +
				"}";


		LOGGER.debug("Search for albums for artist with name: " + this.artist.getName() + ", with query:" + getDiscographyStr);
		QueryExecution execution = QueryExecutionFactory.create(getDiscographyStr, model);
		ResultSet albums = execution.execSelect();

		LOGGER.debug("Found records? " + albums.hasNext());
		while(albums.hasNext()){

			RecordImp recordResult = new RecordImp();
			QuerySolution queryAlbum = albums.next();
			recordResult.setId(queryAlbum.get("albumId").toString());
			recordResult.setName(queryAlbum.get("title").toString());
			if(queryAlbum.get("image") != null) {
				recordResult.setImage(queryAlbum.get("image").toString());
			}
			if(queryAlbum.get("year") != null) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy",Locale.US);
				recordResult.setYear(format.format(makeDate(queryAlbum.get("year").toString())));
			}
			if(recordResult.getImage() != null){
				uniqueRecord.put(recordResult.getName(), recordResult);
			}
		}
		for(Record record : uniqueRecord.values()){
			discog.add(record);
		}

		this.artist.setDiscography(discog);
		LOGGER.debug("Found "+ artist.getDiscography().size() +"  artist records");
	}

	private void setSimilarArtist() {
		List<Artist> similar = new LinkedList<Artist>();
		String similarStr = "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				"PREFIX mo:<http://purl.org/ontology/mo/>  " +
				"PREFIX foaf:<http://xmlns.com/foaf/0.1/> " +
				"SELECT ?name ?id ?image " +
				" WHERE { <" + this.artist.getId() + "> mo:similar-to ?id . " +
				"?id foaf:name ?name; " +
				" mo:image ?image } ";

		QueryExecution execution = QueryExecutionFactory.create(similarStr, model);
		ResultSet similarResults = execution.execSelect();

		while(similarResults.hasNext()){
			ArtistImpl similarArtist = new ArtistImpl();
			QuerySolution queryArtist = similarResults.next();
			similarArtist.setName(queryArtist.get("name").toString());
			similarArtist.setId(queryArtist.get("id").toString());
			similarArtist.setImage(queryArtist.get("image").toString());
			similar.add(similarArtist);
		}
		artist.setSimilar(similar);
		LOGGER.debug("Found " + this.artist.getSimilar().size() +" similar artists");
	}

	private void setArtistEvents(){
		List<Event> events = new LinkedList<Event>();
		String getArtistEventsStr = " PREFIX foaf:<http://xmlns.com/foaf/0.1/> PREFIX event: <http://purl.org/NET/c4dm/event.owl#> PREFIX v: <http://www.w3.org/2006/vcard/ns#> PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>" +
				"SELECT ?venueId ?venueName ?date ?lng ?lat ?location " +
				" WHERE {?preformance foaf:hasAgent <" + this.artist.getId() + ">; event:place ?venueId; event:time ?date. ?venueId v:organisation-name ?venueName; geo:lat ?lat; geo:long ?lng; v:locality ?location}";
		QueryExecution execution = QueryExecutionFactory.create(getArtistEventsStr, model);
		ResultSet eventResults = execution.execSelect();
		while(eventResults.hasNext()){
			EventImpl event = new EventImpl();
			QuerySolution queryEvent = eventResults.next();
			event.setId(queryEvent.get("venueId").toString());
			event.setVenue(queryEvent.get("venueName").toString());
			event.setLat(queryEvent.get("lat").toString());
			event.setLng(queryEvent.get("lng").toString());
			
			String dateString = queryEvent.get("date").toString();
			Date date = new Date();
			SimpleDateFormat stf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss",Locale.US);
			try {
				date = stf.parse(dateString);
				
			} catch (ParseException e) {
				LOGGER.error("Couldnt parse date");
			}
			
			event.setDate(date);
			event.setLocation(queryEvent.get("location").toString());

			events.add(event);
		}
		this.artist.setEvents(events);
		LOGGER.debug("Found "+ artist.getEvents().size() +"  artist events");
	}

	private void setArtistInfo() {

		String id = " <" + artist.getId() + "> ";
		String getArtistInfoStr = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
				"PREFIX mo: <http://purl.org/ontology/mo/> " +
				"PREFIX dbpedia: <http://dbpedia.org/property/> " +
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"PREFIX dbont: <http://dbpedia.org/ontology/> " +
				"SELECT DISTINCT * WHERE {" +
				"OPTIONAL { "+ id +" mo:fanpage ?fanpage.} " +
				"OPTIONAL { "+ id +" mo:imdb ?imdb. } " +
				"OPTIONAL { "+ id +" mo:myspace ?myspace. } " +
				"OPTIONAL { "+ id +" mo:homepage ?homepage. } " +
				"OPTIONAL { "+ id +" rdfs:comment ?shortDesc.}  " +
				"OPTIONAL { "+ id +" mo:image ?image}" +
				"OPTIONAL { "+ id +" mo:biography ?bio. } " +
				"OPTIONAL { "+ id +" dbont:birthname ?birthname} " +
				"OPTIONAL { "+ id +" dbont:hometown ?hometown. } " +
				"OPTIONAL { "+ id +" mo:origin ?origin. } " +
				"OPTIONAL { "+ id +" mo:activity_start ?start. } " +
				"OPTIONAL { "+ id +" mo:activity_end ?end. } " +
				"OPTIONAL { "+ id +" dbont:birthDate ?birthdate. } " +
				"OPTIONAL { "+ id +" dbont:deathDate ?deathdate. } " +
				"OPTIONAL { "+ id +" mo:wikipedia ?wikipedia. } " +
				"OPTIONAL { "+ id +" foaf:page ?bbcpage. }" +
				"OPTIONAL { "+ id +" dbont:bandMember ?memberOf. ?memberOf rdfs:label ?name1 }" +
				"OPTIONAL { "+ id +" dbont:formerBandMember ?pastMemberOf. ?pastMemberOf rdfs:label ?name2 }" +
				"OPTIONAL { "+ id +" dbpedia:currentMembers ?currentMembers. ?currentMembers rdfs:label ?name3 }" +
				"OPTIONAL { "+ id +" dbpedia:pastMembers ?pastMembers. ?pastMembers rdfs:label ?name4 }}" ;

		QueryExecution ex = QueryExecutionFactory.create(getArtistInfoStr, model);
		ResultSet results = ex.execSelect();
		HashMap<String,Object> metaMap = new HashMap<String,Object>();
		List<String> fanpages = new LinkedList<String>();
		List<String> bands = new LinkedList<String>();
		List<String> formerBands = new LinkedList<String>();
		List<String> currentMembers = new LinkedList<String>();
		List<String> pastMembers = new LinkedList<String>();
		while(results.hasNext()) {
			
			// TODO: optimize (e.g storing in variables instead of performing query.get several times?)
			QuerySolution query = results.next();
			if(query.get("image") != null){
				artist.setImage(query.get("image").toString());
			}
			if(query.get("fanpage") != null){
				String fanpage = "<a href=\"" + query.get("fanpage").toString().replace("@en", "") + "\">" + query.get("fanpage").toString().replace("@en", "") + "</a>";

				if(!fanpages.contains(fanpage)) {
					fanpages.add(fanpage);
				}
			}
			if(query.get("memberOf") != null){
				String memberOf = "<a href=\"" + query.get("name1").toString().replace("@en", "") + "\">" + query.get("name1").toString().replace("@en", "") + "</a>";

				if(!bands.contains(memberOf)) {
					bands.add(memberOf);
				}
			}
			if(query.get("pastMemberOf") != null){
				String pastMemberOf = "<a href=\"" + query.get("name2").toString().replace("@en", "") + "\">" + query.get("name2").toString().replace("@en", "") + "</a>";

				if(!formerBands.contains(pastMemberOf)) {
					formerBands.add(pastMemberOf);
				}
			}
			if(query.get("currentMembers") != null){
				String currentMember = "<a href=\"" + query.get("name3").toString().replace("@en", "") + "\">" + query.get("name3").toString().replace("@en", "") + "</a>";

				if(!currentMembers.contains(currentMember)) {
					currentMembers.add(currentMember);
				}
			}
			if(query.get("pastMembers") != null){
				String pastMember = "<a href=\"" + query.get("name4").toString().replace("@en", "") + "\">" + query.get("name4").toString().replace("@en", "") + "</a>";

				if(!pastMembers.contains(pastMember)) {
					pastMembers.add(pastMember);
				}
			}

			if(query.get("bio") != null) {
				artist.setBio(query.get("bio").toString());
			}

			if(query.get("wikipedia") != null) {
				metaMap.put("Wikipedia", ("<a href=\"" + query.get("wikipedia").toString() + "\">" + query.get("wikipedia").toString() + "</a>"));
			}

			if(query.get("bbcpage") != null) {
				metaMap.put("BBC Music", ("<a href=\"" + query.get("bbcpage").toString() + "\">" + query.get("bbcpage").toString() + "</a>"));
			}

			if(query.get("birthdate") != null) {
				SimpleDateFormat format = new SimpleDateFormat("EEE dd. MMM yyyy",Locale.US);	
				metaMap.put("Born", (format.format(makeDate(query.get("birthdate").toString()))));
			}

			if(query.get("homepage") != null) {
				metaMap.put("Homepage", ("<a href=\"" + query.get("homepage").toString() + "\">" + query.get("homepage").toString() + "</a>"));
			}

			if(query.get("imdb") != null) {
				metaMap.put("IMDB", ("<a href=\"" + query.get("imdb").toString() + "\">" + query.get("imdb").toString() + "</a>"));
			}

			if(query.get("myspace") != null) {
				metaMap.put("MySpace", ("<a href=\"" + query.get("myspace").toString() + "\">" + query.get("myspace").toString() + "</a>"));
			}

			if(query.get("shortDesc") != null) {
				artist.setShortDescription(query.get("shortDesc").toString());
			}

			if(query.get("birthname") != null) {
				metaMap.put("Name", (query.get("birthname").toString()));
			}
			
			
			if(query.get("deathdate") != null) {
SimpleDateFormat format = new SimpleDateFormat("EEE dd. MMM yyyy",Locale.US);	
					
				metaMap.put("Died", (format.format(makeDate(query.get("deathdate").toString()))));
				
			}

			if(query.get("origin") != null) {
				metaMap.put("From", (query.get("origin").toString()));
			}

			if(query.get("hometown") != null) {
				metaMap.put("Living", (query.get("hometown").toString()));
			}
			if(query.get("start") != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy",Locale.US);	
				
				String activityStart = format.format(makeDate(query.get("start").toString()));
				if(query.get("end") != null) {
					
					
					activityStart += "-" + format.format(makeDate(query.get("end").toString()));
					
				}
				metaMap.put("Active",activityStart);
			}
			
		}

		if(!fanpages.isEmpty()) {
			metaMap.put("Fanpages", fanpages.toString());
		}
		if(!bands.isEmpty()) {
			metaMap.put("Bandmembers", bands);
		}
		if(!formerBands.isEmpty()) {
			metaMap.put("Former bandmembers", formerBands);
		}
		if(!currentMembers.isEmpty()) {
			metaMap.put("Member of", currentMembers);
		}
		if(!pastMembers.isEmpty()) {
			metaMap.put("Past member of", pastMembers);
		}
		artist.setMeta(metaMap);
		LOGGER.debug("Found " + artist.getMeta().size() + " fun facts.");
	}

	public Event searchEvent(String search_string) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String,Record> searchRecords(String search_string) {
		Map<String, Record> map = new HashMap<String, Record>();
		for(int i = 0; i < search_string.length() ; i++){
			
			map.put(String.valueOf(search_string.charAt(i)), new MockRecord());
		}
		return map;
	}

	public Record searchRecord(String search_string) {
		return new MockRecord();
	}
	
	public Track searchTrack(String search_string) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) throws ArtistNotFoundException {
		Searcher searcher = new SearcherImpl();
		searcher.searchArtist("Guns N Roses");
	}
	
	public Date makeDate(String dateString){
		Date date = new Date();
		SimpleDateFormat stf = new SimpleDateFormat("yyyy-mm-dd",Locale.US);
		try {
			date = stf.parse(dateString);
			
			
		} catch (ParseException e) {
			LOGGER.error("Couldnt parse date");
		}
		return date;
	}

	
}
