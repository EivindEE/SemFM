package edu.uib.info310.search;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uib.info310.sparql.QueryEndPoint;
import edu.uib.info310.sparql.QueryEndPointImp;
import edu.uib.info310.transformation.XslTransformer;

public class DiscogSearch {
	private static final String searchAlbum = "http://api.discogs.com/search?q=";
	private static final String searchEnd = "&f=xml";
	private static final String release = "http://api.discogs.com/release/1264945";
	private static final String kasabi = "http://api.kasabi.com/dataset/discogs/apis/sparql?apikey=fe29b8c58180640f6db16b9cd3bce37c872c2036&output=xml&query=";

	private static String prefix = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
			"PREFIX dc: <http://purl.org/dc/elements/1.1/>" +
			"PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
			"PREFIX mo: <http://purl.org/ontology/mo/>" +
			"PREFIX dateIssued: <http://purl.org/dc/terms/issued>" +
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
			"PREFIX dc: <http://purl.org/dc/terms/>";
	


	
	public static void main(String[] args) throws Exception {
		String artist = "Michael Jackson";
		String searchString =" PREFIX INPUT: <http://www.discogs.com/artist/"+artist.replace(' ', '+')+"> "+

 			"DESCRIBE ?disc where{ ?artist1 mo:discogs INPUT:." +
 			"?artist1 foaf:made ?allReleases. ?allReleases rdf:type mo:Record; mo:discogs ?disc }";
		
		Query query = QueryFactory.create(prefix + searchString);
		System.out.println(query.toString());
		QueryEngineHTTP queryExecution = QueryExecutionFactory.createServiceRequest("http://api.kasabi.com/dataset/discogs/apis/sparql", query);
		
		queryExecution.addParam("apikey", "fe29b8c58180640f6db16b9cd3bce37c872c2036");
		

		Model model = queryExecution.execDescribe();
		
		FileOutputStream outPut = new FileOutputStream(new File("log/disc.ttl"));
		
		model.write(outPut, "TURTLE");
		
		
		
		

	}	
	public String makeWebSafeString(String unsafe){
		try {
			return URLEncoder.encode(unsafe, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return unsafe;
		}
	}
}
