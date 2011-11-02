package edu.uib.info310.search;

import java.util.LinkedList;
import java.util.List;

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
import edu.uib.info310.search.builder.OntologyBuilder;
@Component
public class SearcherImpl implements Searcher {

	private OntologyBuilder builder = new OntologyBuilder();

	
	public Artist searchArtist(String search_string) {
		ArtistImp artist = new ArtistImp();
		artist.setName(search_string);
		Model model = builder.createArtistOntology(search_string);
		
		String queryStr = "PREFIX mo:<http://purl.org/ontology/mo/> PREFIX foaf:<http://xmlns.com/foaf/0.1/>  SELECT ?id WHERE {?id foaf:name '"+search_string+"'}";
		QueryExecution execution = QueryExecutionFactory.create(queryStr, model);
		ResultSet similarResults = execution.execSelect();
		while(similarResults.hasNext()){
			artist.setId(similarResults.nextSolution().get("id").toString());
		}
		System.out.println(artist.getId());
		artist.setSimilar(getSimilar(model, artist.getId()));
		
		return artist;
	}

	private List<Artist> getSimilar(Model model, String id) {
		List<Artist> similar = new LinkedList<Artist>();
		
		String queryStr = "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX mo:<http://purl.org/ontology/mo/>  PREFIX foaf:<http://xmlns.com/foaf/0.1/> SELECT ?similar ?x ?y WHERE {<"+ id + "> rdf:about ?similar. }";
		System.out.println(queryStr);
		QueryExecution execution = QueryExecutionFactory.create(queryStr, model);
		ResultSet similarResults = execution.execSelect();
		while(similarResults.hasNext()){
			QuerySolution similarArtist = similarResults.next();
			System.out.println(similarArtist.get("similar") + ", predicate :" + similarArtist.get("x") + ", object :" + similarArtist.get("y"));
		}
		
		return similar;
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
