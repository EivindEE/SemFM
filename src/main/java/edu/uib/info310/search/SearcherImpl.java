package edu.uib.info310.search;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.datetime.DateFormatter;
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
import edu.uib.info310.model.imp.ArtistImp;
import edu.uib.info310.model.imp.EventImpl;
import edu.uib.info310.model.imp.RecordImp;
import edu.uib.info310.search.builder.OntologyBuilder;
@Component
public class SearcherImpl implements Searcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearcherImpl.class);
	private OntologyBuilder builder = new OntologyBuilder();


	public Artist searchArtist(String search_string) {
		ArtistImp artist = new ArtistImp();
		artist.setName(search_string);
		Model model = builder.createArtistOntology(search_string);
		LOGGER.debug("Size of infered model: " + model.size());

//		FileOutputStream out;
//		try {
//			out = new FileOutputStream(new File("log/out.ttl"));
//			model.write(out, "TURTLE");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		LOGGER.debug("Model written to file.");

		String queryStr = "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX mo:<http://purl.org/ontology/mo/> PREFIX foaf:<http://xmlns.com/foaf/0.1/>  SELECT ?id WHERE {?id foaf:name '"+search_string+"'; mo:similar-to ?something.}";
		QueryExecution execution = QueryExecutionFactory.create(queryStr, model);
		ResultSet similarResults = execution.execSelect();
		artist.setId(similarResults.nextSolution().get("id").toString());
		LOGGER.debug("Artist id set to " + artist.getId());
		artist.setSimilar(getSimilar(model, artist.getId()));
		LOGGER.debug("Found " + artist.getSimilar().size() +" similar artists");
		artist.setEvents(getEvents(model, artist.getId()));
		LOGGER.debug("Found "+ artist.getEvents().size() +"  artist events");
		artist.setDiscography(getDiscography(model, artist.getName()));
		LOGGER.debug("Found "+ artist.getDiscography().size() +"  artist records");
		setArtistInfo(model, artist);

		return artist;



		//artist.setDiscography(getDiscography(model, "AristID"));

		//return artist;

	}

	private List<Record> getDiscography(Model model, String name) {
		List<Record> discog = new LinkedList<Record>();
		Map<String,Record> uniqueRecord = new HashMap<String, Record>();
		String queryStr = 	"PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
				"PREFIX mo: <http://purl.org/ontology/mo/> " +
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
				"PREFIX dc: <http://purl.org/dc/terms/> " + 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				"SELECT DISTINCT " +
				" ?artistId ?albumId ?release ?title ?image ?year ?labelId ?labelName ?track ?artist  "+
				" WHERE { " +
				"?artistId foaf:name  '" + name + "'. "+
				"?artistId foaf:made ?albumId."+ 
				"?albumId dc:title ?title." +
				//									"?albumId mo:discogs ?release;" +
				//												"dc:title ?title."+
				"OPTIONAL {?albumId mo:publisher ?labelId. } "+
				"OPTIONAL {?albumId dc:issued ?year. }" +
				"OPTIONAL {?albumId foaf:depiction ?image. }" +
				"}";


		LOGGER.debug("Search for albums for artist with name: " + name + ", with query:" + queryStr);
		QueryExecution execution = QueryExecutionFactory.create(queryStr, model);
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
			if(recordResult.getImage() != null && !recordResult.getImage().startsWith("http://s.dsimg")){
				uniqueRecord.put(recordResult.getName(), recordResult);
			}
		}
		for(Record record : uniqueRecord.values()){
			discog.add(record);
		}
		
		return discog;
	}

	private List<Artist> getSimilar(Model model, String id) {
		List<Artist> similar = new LinkedList<Artist>();
		String queryStr = "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				"PREFIX mo:<http://purl.org/ontology/mo/>  " +
				"PREFIX foaf:<http://xmlns.com/foaf/0.1/> " +
				"SELECT ?name ?id ?image " +
				" WHERE { <"+id+"> mo:similar-to ?id . " +
				"?id foaf:name ?name; " +
				" mo:image ?image } ";

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

	private Artist setArtistInfo(Model model, ArtistImp artist) {

		String queryStr = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
				"PREFIX mo: <http://purl.org/ontology/mo/> " +
				"PREFIX dbpedia: <http://dbpedia.org/property/> " +
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"PREFIX dbont: <http://dbpedia.org/ontology/> " +
				"SELECT * WHERE{?artistId foaf:name '"+ artist.getName() + "'; mo:image ?image. ?artistId mo:fanpage ?fanpage. OPTIONAL { ?artistId mo:imdb ?imdb. } OPTIONAL { ?artistId mo:myspace ?myspace. } OPTIONAL { ?artistId foaf:homepage ?homepage. } OPTIONAL { ?artistId rdfs:comment ?shortDesc. }  ?artistId owl:sameAs ?artistDb. OPTIONAL { ?artistDb dbpedia:abstract ?bio. Filter (lang(?bio) = 'en').} OPTIONAL { ?artistDb dbont:birthname ?birthname} OPTIONAL {?artistDb dbpedia:origin ?origin. } OPTIONAL {?artistDb dbpedia:yearsActive ?yearsactive. } OPTIONAL {?artistDb dbpedia:dateOfBirth ?birthdate. } OPTIONAL {?artistDb foaf:page ?wikipedia. } OPTIONAL {?artistId foaf:page ?bbcpage. }}";

		QueryExecution ex = QueryExecutionFactory.create(queryStr, model);
		ResultSet results = ex.execSelect();
		HashMap<String,String> metaMap = new HashMap<String,String>();
		List<String> fanpages = new LinkedList<String>();


		while(results.hasNext()) {
			// TODO: optimize (e.g storing in variables instead of performing query.get several times?)
			QuerySolution query = results.next();
			artist.setImage(query.get("image").toString());
			if(!fanpages.contains(query.get("fanpage").toString())) {
				fanpages.add("<a href=\"" + query.get("fanpage").toString() + "\">" + query.get("fanpage").toString() + "</a>");
			}
//			LOGGER.debug(query.get("fanpage").toString());

			// foaf:page <http://en.wikipedia.org/wiki/Rihanna>
			//  <http://dbpedia.org/property/dateOfBirth> "1988-02-20"^^xsd:date ;

			if(query.get("bio") != null) {
				artist.setBio(query.get("bio").toString());
//				LOGGER.debug(query.get("bio").toString());
			}

			if(query.get("wikipedia") != null) {
				metaMap.put("Wikipedia", ("<a href=\"" + query.get("wikipedia").toString() + "\">" + query.get("wikipedia").toString() + "</a>"));
//				LOGGER.debug(query.get("wikipedia").toString());
			}

			if(query.get("bbcpage") != null) {
				metaMap.put("BBC Music", ("<a href=\"" + query.get("bbcpage").toString() + "\">" + query.get("bbcpage").toString() + "</a>"));
//				LOGGER.debug(query.get("bbcpage").toString());
			}

			if(query.get("birthdate") != null) {
				metaMap.put("Born", (query.get("birthdate").toString()));
//				LOGGER.debug(query.get("birthdate").toString());
			}

			if(query.get("homepage") != null) {
				metaMap.put("Homepage", ("<a href=\"" + query.get("homepage").toString() + "\">" + query.get("homepage").toString() + "</a>"));
//				LOGGER.debug(query.get("homepage").toString());
			}

			if(query.get("imdb") != null) {
				metaMap.put("IMDB", ("<a href=\"" + query.get("imdb").toString() + "\">" + query.get("imdb").toString() + "</a>"));
//				LOGGER.debug(query.get("imdb").toString());
			}

			if(query.get("myspace") != null) {
				metaMap.put("MySpace", ("<a href=\"" + query.get("myspace").toString() + "\">" + query.get("myspace").toString() + "</a>"));
//				LOGGER.debug(query.get("myspace").toString());
			}

			if(query.get("shortDesc") != null) {
				artist.setShortDescription(query.get("shortDesc").toString());
//				LOGGER.debug(query.get("shortDesc").toString());
			}

			if(query.get("birthname") != null) {
				metaMap.put("Name", (query.get("birthname").toString()));
//				LOGGER.debug(query.get("birthname").toString());
			}

			if(query.get("origin") != null) {
				metaMap.put("From", (query.get("origin").toString()));
//				LOGGER.debug(query.get("origin").toString());
			}

			if(query.get("yearsactive") != null) {
				metaMap.put("Active", (query.get("yearsactive").toString()));
//				LOGGER.debug(query.get("yearsactive").toString());
			}
		}

		if(!fanpages.isEmpty()) {
			metaMap.put("Fanpages", fanpages.toString());
		}
		artist.setMeta(metaMap);

		return artist;	

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
