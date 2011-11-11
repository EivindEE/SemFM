package edu.uib.info310.search;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelFactoryBase;
import com.hp.hpl.jena.sparql.algebra.Transformer;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

import edu.uib.info310.transformation.XslTransformer;
import edu.uib.info310.vocabulary.MO;

public class DiscogSearch {
//	private static final String searchAlbum = "http://api.discogs.com/search?q=";
//	private static final String searchEnd = "&f=xml";
//	private static final String kasabi = "http://api.kasabi.com/dataset/discogs/apis/sparql?apikey=fe29b8c58180640f6db16b9cd3bce37c872c2036&output=xml&query=";
	private static final String release = "http://api.discogs.com/release/";
	private static final String master =  "http://api.discogs.com/master/";
	private static final Logger LOGGER = LoggerFactory.getLogger(DiscogSearch.class);
	private static final String format = "?f=xml";
	private static String PREFIX = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
			"PREFIX dc: <http://purl.org/dc/elements/1.1/>" +
			"PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
			"PREFIX mo: <http://purl.org/ontology/mo/>" +
//			"PREFIX dateIssued: <http://purl.org/dc/terms/issued>" +
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
			"PREFIX dc: <http://purl.org/dc/terms/>" +
			"PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>";
	
	
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
	public Model getTracks(String search_string){
		String safe_search = "";
		try {
			safe_search = URLEncoder.encode(search_string, "UTF-8");
		} catch (UnsupportedEncodingException e) {/*ignore*/}
		
		String searchString =" PREFIX INPUT: <http://data.kasabi.com/dataset/discogs/artist/" + safe_search.toLowerCase().replace("+", "-") + ">" +

	 			"DESCRIBE * where{ ?release foaf:maker INPUT:." +
	 			"?release rdf:type mo:Track.}";
		
		Query query = QueryFactory.create(PREFIX + searchString);
		QueryEngineHTTP queryExecution = QueryExecutionFactory.createServiceRequest("http://api.kasabi.com/dataset/discogs/apis/sparql", query);
		
		queryExecution.addParam("apikey", "fe29b8c58180640f6db16b9cd3bce37c872c2036");
		
		return queryExecution.execDescribe();
	}
	
	public Model getAlbum(String search_string){
		String safe_search = "";
		try {
			safe_search = URLEncoder.encode(search_string, "UTF-8");
		} catch (UnsupportedEncodingException e) {/*ignore*/}
		
		String searchString =
	 			"DESCRIBE ?album WHERE{?album dc:title \""+ search_string + "\" ; rdf:type mo:Record; mo:publisher ?publisher. ?maker foaf:name 'Michael Jackson'. ?album foaf:maker ?maker}";
		String constructStr = "CONSTRUCT {?artist foaf:made ?record;" +
												 "foaf:name ?artistName;" +
												 "rdf:type mo:MusicArtist ." +
										 "?record rdfs:comment ?comment;" +
										 		 "dc:issued ?issued;" +
										 		 "rdf:type mo:Record;" +
										 		 "mo:discogs ?discogs;" +
										 		 "foaf:name ?recordName;" +
										 		 "mo:publisher ?publisher;" +
										 		 "mo:track ?track";
		
		
		String whereStr = "} WHERE {?record dc:title \""+ search_string + "\" ;" +
										   "rdf:type mo:Record;" +
										   "foaf:maker ?artist;" +
										   "dc:issued ?issued;" +
										   "mo:discogs ?discogs;" +
										   "dc:title ?recordName;" +
										   "mo:publisher ?publisher;" +
										   "mo:track ?track." +
									"?artist foaf:name ?artistName}";
		
		
		Query query = QueryFactory.create(PREFIX + constructStr + whereStr);
		QueryEngineHTTP queryExecution = QueryExecutionFactory.createServiceRequest("http://api.kasabi.com/dataset/discogs/apis/sparql", query);
		
		queryExecution.addParam("apikey", "fe29b8c58180640f6db16b9cd3bce37c872c2036");
		

		
		return queryExecution.execConstruct();
	}
	
	
	public Model getAlbums(String search_string){
		String safe_search = "";
		try {
			safe_search = URLEncoder.encode(search_string, "UTF-8");
		} catch (UnsupportedEncodingException e) {/*ignore*/}
		
		String constructStr = "CONSTRUCT {?artist foaf:made ?record;" +
												 "foaf:name ?artistName;" +
												 "rdf:type mo:MusicArtist ." +
										 "?record rdf:type mo:Record;" +
										 		 "mo:discogs ?discogs;" +
										 		 "dc:title ?record.";
		
		
		String whereStr = "} WHERE {?record dc:title \""+ safe_search + "\" ;" +
										   "rdf:type mo:Record;" +
										   "foaf:maker ?artist;" +
										   "mo:discogs ?discogs;" +
										   "dc:title ?recordName." +
									"?artist foaf:name ?artistName}";
		
		
		Query query = QueryFactory.create(PREFIX + constructStr + whereStr);
		QueryEngineHTTP queryExecution = QueryExecutionFactory.createServiceRequest("http://api.kasabi.com/dataset/discogs/apis/sparql", query);
		
		queryExecution.addParam("apikey", "fe29b8c58180640f6db16b9cd3bce37c872c2036");
		

		
		return queryExecution.execConstruct();
	}
	
	private InputStream getAlbumInputStream(String uri, String releaseId) throws MasterNotFoundException{
		InputStream in = null;
		try{ 
			URL discogRequest = new URL(uri + releaseId + format);
			LOGGER.debug("Discogs request URL: " + discogRequest.toExternalForm());

			URLConnection discogConnection = discogRequest.openConnection();
			//LOGGER.debug(discogConnection.getContentType());
			discogConnection.setRequestProperty("Accept-Encoding", "gzip,deflate");
			in = discogConnection.getInputStream();
		} catch(IOException ioexc){ 
			ioexc.printStackTrace();
			throw new MasterNotFoundException("No master found");
		}
		
		return in;
	}

	private Document docBuilder(String releaseId) throws MasterNotFoundException {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		Document doc = null;
		DocumentBuilder builder = null;
		try {
			builder = domFactory.newDocumentBuilder();
		} catch (Exception e) {/*ignore*/}
		try {
			LOGGER.debug("" + releaseId + "");
			doc = builder.parse(getAlbumInputStream(release,releaseId));
		} catch (Exception a) {
			a.printStackTrace();
			throw new MasterNotFoundException("Doc couldn't be built");
		}
		LOGGER.debug("DOM object with " + releaseId + " master ID");
		return doc;
	}
	
	public InputStream getAlbumURI(String releaseId) throws MasterNotFoundException {
		try{
			Document release = docBuilder(releaseId);
			NodeList nameList = release.getElementsByTagName("master_id");
			Node nameNode = nameList.item(0);
			Element element = (Element) nameNode;
			NodeList name = element.getChildNodes();
			Document mainrelease = docBuilder((name.item(0).getNodeValue()));
			NodeList mainList = mainrelease.getElementsByTagName("main_release");
			Node mainNode = mainList.item(0);
			Element elementm = (Element) mainNode;
			NodeList main = elementm.getChildNodes();
			
			//LOGGER.debug("Get node value of correct artist: " + (name.item(0)).getNodeValue());
			return getAlbumInputStream(master, (main.item(0)).getNodeValue());
		}
		catch (NullPointerException e) {
			return getAlbumInputStream(release,releaseId);
		
		}
	}
	
	public static void main(String[] args) throws MasterNotFoundException, IOException, TransformerException {
		
		File xsl = new File("src/main/resources/XSL/AlbumXSLT.xsl");
		
		DiscogSearch album = new DiscogSearch();
		
		XslTransformer transform = new XslTransformer();
		
		
		
		
		transform.setXml(album.getAlbumURI("338967"));
		transform.setXsl(xsl);
		
		Model model = ModelFactory.createDefaultModel();
		InputStream in = new ByteArrayInputStream(transform.transform().toByteArray());
		model.read(in,null);
		try {
			FileOutputStream out = new FileOutputStream(new File("log/albumout.ttl"));
			model.write(out,"TURTLE");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
