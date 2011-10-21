package edu.uib.info310.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class LastFM {
	private static String defaultURL = "http://ws.audioscrobbler.com/2.0/?method=";
	private static String api = "&api_key=a7123248beb0bbcb90a2e3a9ced3bee9";
	//defaultURL + object.event + api + objectID
	
	public static String prefix = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
			"PREFIX dc: <http://purl.org/dc/elements/1.1/>" +
			"PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
			"prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
			"PREFIX mo: <http://purl.org/ontology/mo/>" +
			"PREFIX rel: <http://purl.org/vocab/relationship/>" +
			"PREFIX terms: <http://purl.org/dc/terms/>" +
			"PREFIX rev: <http://purl.org/stuff/rev#>" +
			"PREFIX event: <http://purl.org/NET/c4dm/event.owl#>" +
			"PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>" +
			"PREFIX v: <http://www.w3.org/2006/vcard/ns#>" +
			"PREFIX music: <http://www.kanzaki.com/ns/music#> ";
	
	public static String artistGetEvents(String subject) throws MalformedURLException{
		URL queryUrl = new URL(createQueryURL("artist", "getEvents", subject));
		String test = queryUrl.toString();
		return test;

	}
	public static ResultSet artistEvents(String artist) {
		Model model = ModelFactory.createDefaultModel();
		model.read("http://lastfm.rdfize.com/?username=&eventID=&artistName=" +artist.replace(' ', '+') +"&venueID=&output=turtle", "Turtle");
		String queryString = prefix + "SELECT ?perform ?name ?date ?venue ?venueName ?adressName WHERE { " + 
		"?perform rdf:type mo:Performance. " +
		"?perform rdfs:label ?name. " +
 		"?perform terms:date ?date. " + 
//		"?perform foaf:homepage ?eventPage. " +
		"?perform event:place ?venue." +
		"?venue rdfs:label ?venueName. " +
//		"?venue geo:lat ?lat. " +
//		"?venue geo:long ?long. " +
//		"?venue foaf:homepage ?venuePage. " +
		"?venue terms:coverage ?adress. " +
		"?adress rdfs:label ?adressName. " +
		"} ";
		Query query = QueryFactory.create(queryString) ;
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		return results;
	}
	
	public static ResultSet eventArtist(String eventID) {
		Model model = ModelFactory.createDefaultModel();
		model.read("http://lastfm.rdfize.com/?username=&eventID=" +eventID.replace("http://lastfm.rdfize.com/events/", "") +"&artistName=&venueID=&output=turtle", "Turtle");
		String queryString = prefix + "SELECT ?artistName WHERE { " + 
		"?artist rdf:type mo:MusicArtist. " +
		"?artist rdfs:label ?artistName. " +
		"} ";
		Query query = QueryFactory.create(queryString) ;
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		return results;
	}
	
	public static String getSimilar(String subject) throws MalformedURLException, FileNotFoundException{
		URL queryUrl = new URL(createQueryURL("artist", "getsimilar", subject));
		String test = queryUrl.toString();
		
		return test;		
	}	
	
	
	/**
	 * @param args
	 */
	private static String createQueryURL(String object, String predicate, String subject) {
		String subject2 = subject.replace(' ', '+');
		return defaultURL + object + "." + predicate + "&artist=" + subject2 + api;
		}
	
	public static void main(String[] args) throws MalformedURLException, FileNotFoundException {
		System.out.println(artistGetEvents("Metallica"));
		System.out.println(getSimilar("Metallica"));
		
		ResultSet test = artistEvents("Metallica");

	        for ( ; test.hasNext() ; )
	       {

	        QuerySolution soln = test.nextSolution() ;
	        RDFNode x = soln.get("name") ;  
	        RDFNode y = soln.get("venueName");
	        RDFNode z = soln.get("adressName");
	        RDFNode a = soln.get("perform");
        
	        
	        System.out.println("'" + x + "'" + " is performed at " + y + " (" + z + "). ");
	        ResultSet artist = eventArtist(a.toString());
	        
	        System.out.println("Artister som opptrer");
	        for ( ; artist.hasNext() ; )
	       {

	        QuerySolution soln2 = artist.nextSolution() ;
	        RDFNode b = soln2.get("artistName") ; 
	        System.out.println(b);
	       }
	        //Ny spørring for artister på en event? 
	       }
	}


	

}
