package edu.uib.info310.search;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

public class DiscogSearch {
//	private static final String searchAlbum = "http://api.discogs.com/search?q=";
//	private static final String searchEnd = "&f=xml";
//	private static final String release = "http://api.discogs.com/release/1264945";
//	private static final String kasabi = "http://api.kasabi.com/dataset/discogs/apis/sparql?apikey=fe29b8c58180640f6db16b9cd3bce37c872c2036&output=xml&query=";

	private static String PREFIX = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
			"PREFIX dc: <http://purl.org/dc/elements/1.1/>" +
			"PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
			"PREFIX mo: <http://purl.org/ontology/mo/>" +
			"PREFIX dateIssued: <http://purl.org/dc/terms/issued>" +
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
			"PREFIX dc: <http://purl.org/dc/terms/>";
	
	
	public Model getDiscography(String search_string){
		String safe_search = "";
		try {
			safe_search = URLEncoder.encode(search_string, "UTF-8");
		} catch (UnsupportedEncodingException e) {/*ignore*/}
		
		String searchString =" PREFIX INPUT: <http://www.discogs.com/artist/" + safe_search + ">" +
	 			"DESCRIBE * where{ ?artist1 mo:discogs INPUT:." +
	 			"?artist1 foaf:made ?allReleases. ?allReleases rdf:type mo:Record.}";
		
		Query query = QueryFactory.create(PREFIX + searchString);
		QueryEngineHTTP queryExecution = QueryExecutionFactory.createServiceRequest("http://api.kasabi.com/dataset/discogs/apis/sparql", query);
		
		queryExecution.addParam("apikey", "fe29b8c58180640f6db16b9cd3bce37c872c2036");
		

		
		return queryExecution.execDescribe();
	}
}
