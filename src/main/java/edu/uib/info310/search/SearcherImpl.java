package edu.uib.info310.search;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import edu.uib.info310.model.Artist;
import edu.uib.info310.model.Event;
import edu.uib.info310.model.Record;
import edu.uib.info310.model.Track;
import edu.uib.info310.model.imp.ArtistImp;
import edu.uib.info310.model.imp.EventImpl;
import edu.uib.info310.search.builder.OntologyBuilder;
@Component
public class SearcherImpl implements Searcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearcherImpl.class);
	private OntologyBuilder builder = new OntologyBuilder();

	
	public Artist searchArtist(String search_string) {
		ArtistImp artist = new ArtistImp();
		artist.setName(search_string);
		Model model = builder.createArtistOntology(search_string);
		
		FileOutputStream out;
		try {
			out = new FileOutputStream(new File("log/out.ttl"));
			model.write(out, "TURTLE");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		String queryStr = "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX mo:<http://purl.org/ontology/mo/> PREFIX foaf:<http://xmlns.com/foaf/0.1/>  SELECT ?id WHERE {?id foaf:name '"+search_string+"'; mo:similar-to ?something.}";
		QueryExecution execution = QueryExecutionFactory.create(queryStr, model);
		ResultSet similarResults = execution.execSelect();
		while(similarResults.hasNext()){
			artist.setId(similarResults.nextSolution().get("id").toString());
		}
		artist.setSimilar(getSimilar(model, artist.getId()));
		artist.setEvents(getEvents(model, artist.getId()));
		
		return artist;
	}

	private List<Artist> getSimilar(Model model, String id) {
		List<Artist> similar = new LinkedList<Artist>();
		String queryStr = "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX mo:<http://purl.org/ontology/mo/>  PREFIX foaf:<http://xmlns.com/foaf/0.1/> SELECT DISTINCT ?name ?id ?image WHERE {<"+id+">  foaf:name ?name. ?artist foaf:name ?name. ?artist  mo:similar-to ?id ; mo:image ?image .}";
		LOGGER.debug("Search for arist with id:" + id);
		QueryExecution execution = QueryExecutionFactory.create(queryStr, model);
		ResultSet similarResults = execution.execSelect();

		while(similarResults.hasNext()){
			ArtistImp similarArtist = new ArtistImp();
			QuerySolution queryArtist = similarResults.next();
			similarArtist.setName(queryArtist.get("name").toString());
			similarArtist.setId(queryArtist.get("id").toString());
			similarArtist.setImage(queryArtist.get("image").toString());
			similar.add(similarArtist);
		}
		
		return similar;
	}
	
	private List<Event> getEvents(Model model, String id){
	List<Event> events = new LinkedList<Event>();
	String queryStr = " PREFIX foaf:<http://xmlns.com/foaf/0.1/> PREFIX event: <http://purl.org/NET/c4dm/event.owl#> PREFIX v: <http://www.w3.org/2006/vcard/ns#> PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>" +
							"SELECT ?venueId ?venueName ?date ?lng ?lat ?location " +
								" WHERE {?preformance foaf:hasAgent <"+id+">; event:place ?venueId; event:time ?date. ?venueId v:organisation-name ?venueName; geo:lat ?lat; geo:long ?lng; v:locality ?location}";
	QueryExecution execution = QueryExecutionFactory.create(queryStr, model);
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
	return events;
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
