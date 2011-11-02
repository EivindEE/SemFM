package edu.uib.info310.search;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javassist.bytecode.ByteArray;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import edu.uib.info310.model.Artist;
import edu.uib.info310.model.Event;
import edu.uib.info310.model.Record;
import edu.uib.info310.model.Track;
import edu.uib.info310.model.imp.ArtistImp;
import edu.uib.info310.search.builder.OntologyBuilder;
import edu.uib.info310.transformation.XslTransformer;
@Component
public class SearcherImpl implements Searcher {

	private OntologyBuilder builder = new OntologyBuilder();
	public Artist searchArtist(String search_string) {
		ArtistImp artist = new ArtistImp();
		artist.setName(search_string);
		Model model = builder.createArtistOntology(search_string);
		String queryString = "PREFIX mo:<http://purl.org/ontology/mo/> PREFIX foaf:<http://xmlns.com/foaf/0.1/> SELECT ?name WHERE{?artist foaf:name ?name}";
		Query query = QueryFactory.create(queryString);
		QueryExecution execution = QueryExecutionFactory.create(query, model);
		ResultSet results = execution.execSelect();
		while(results.hasNext()){
			QuerySolution sol = results.nextSolution();
			System.out.println(sol.get("name").toString());
		}
		
		
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
