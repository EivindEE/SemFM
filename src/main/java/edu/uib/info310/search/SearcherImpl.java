package edu.uib.info310.search;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
import edu.uib.info310.search.builder.OntologyBuilder;

@Component
public class SearcherImpl implements Searcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearcherImpl.class);
	private OntologyBuilder builder = new OntologyBuilder();
	private Model model;
	private ArtistImpl artist;


	public Artist searchArtist(String search_string) {
		this.artist = new ArtistImpl();
		this.artist.setName(search_string);
		this.model = builder.createArtistOntology(search_string);
		LOGGER.debug("Size of infered model: " + model.size());
		// Write to file causes crash.
//		writeModelToFile();
		
		setArtistId();
	
		setSimilarArtist();
		
		setArtistEvents();
		
		setArtistDiscography();
		
		
		setArtistInfo();

		return this.artist;
	}

	private void setArtistId() {
		String getIdStr = "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX mo:<http://purl.org/ontology/mo/> PREFIX foaf:<http://xmlns.com/foaf/0.1/>  SELECT ?id WHERE {?id foaf:name '" + this.artist.getName() + "'; mo:similar-to ?something.}";
		QueryExecution execution = QueryExecutionFactory.create(getIdStr, model);
		ResultSet similarResults = execution.execSelect();
		this.artist.setId(similarResults.nextSolution().get("id").toString());
		LOGGER.debug("Artist id set to " + this.artist.getId());
		
	}

	private void writeModelToFile() {
		FileOutputStream out;
		try {
			LOGGER.debug("Tried to write file with " + model.size() + " statements to log");
			out = new FileOutputStream(new File("log/out.ttl"));
			model.write(out, "TURTLE");
			
		} catch (FileNotFoundException e) {
			LOGGER.error("Error writing model to file");
		}
		LOGGER.debug("Model written to file.");
		
		
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
				"?artistId foaf:name  '" + this.artist.getName() + "'. "+
				"?artistId foaf:made ?albumId."+ 
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
				recordResult.setYear(queryAlbum.get("year").toString());
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
			event.setDate(queryEvent.get("date").toString());
			event.setLocation(queryEvent.get("location").toString());

			events.add(event);
		}
		this.artist.setEvents(events);
		LOGGER.debug("Found "+ artist.getEvents().size() +"  artist events");
	}

	private void setArtistInfo() {

		String getArtistInfoStr = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
				"PREFIX mo: <http://purl.org/ontology/mo/> " +
				"PREFIX dbpedia: <http://dbpedia.org/property/> " +
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"PREFIX dbont: <http://dbpedia.org/ontology/> " +
				"SELECT * WHERE{?artistId foaf:name '"+ artist.getName() + "'; mo:image ?image. ?artistId mo:fanpage ?fanpage. OPTIONAL { ?artistId mo:imdb ?imdb. } OPTIONAL { ?artistId mo:myspace ?myspace. } OPTIONAL { ?artistId foaf:homepage ?homepage. } OPTIONAL { ?artistId rdfs:comment ?shortDesc. }  ?artistId owl:sameAs ?artistDb. OPTIONAL { ?artistDb dbpedia:abstract ?bio. Filter (lang(?bio) = 'en').} OPTIONAL { ?artistDb dbont:birthname ?birthname} OPTIONAL {?artistDb dbpedia:origin ?origin. } OPTIONAL {?artistDb dbpedia:yearsActive ?yearsactive. } OPTIONAL {?artistDb dbpedia:dateOfBirth ?birthdate. } OPTIONAL {?artistDb foaf:page ?wikipedia. } OPTIONAL {?artistId foaf:page ?bbcpage. }}";

		QueryExecution ex = QueryExecutionFactory.create(getArtistInfoStr, model);
		ResultSet results = ex.execSelect();
		HashMap<String,String> metaMap = new HashMap<String,String>();
		List<String> fanpages = new LinkedList<String>();


		while(results.hasNext()) {
			// TODO: optimize (e.g storing in variables instead of performing query.get several times?)
			QuerySolution query = results.next();
			artist.setImage(query.get("image").toString());
			String fanpage = "<a href=\"" + query.get("fanpage").toString() + "\">" + query.get("fanpage").toString() + "</a>";

			if(!fanpages.contains(fanpage)) {
				fanpages.add(fanpage);
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
				metaMap.put("Born", (query.get("birthdate").toString()));
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

			if(query.get("origin") != null) {
				metaMap.put("From", (query.get("origin").toString()));
			}

			if(query.get("yearsactive") != null) {
				metaMap.put("Active", (query.get("yearsactive").toString()));
			}
		}

		if(!fanpages.isEmpty()) {
			metaMap.put("Fanpages", fanpages.toString());
		}
		artist.setMeta(metaMap);
	}

	public Event searchEvent(String search_string) {
		// TODO Auto-generated method stub
		return null;
	}

	public Record searchRecord(String search_string) {
		// TODO Auto-generated method stub
		return null;
	}

	public Track searchTrack(String search_string) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		Searcher searcher = new SearcherImpl();
		searcher.searchArtist("Rihanna");
	}

}
