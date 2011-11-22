package edu.uib.info310.search.builder.ontology.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.uib.info310.exception.ArtistNotFoundException;
import edu.uib.info310.search.builder.ontology.LastFMOntology;

@Component
public class LastFMOntologyImpl implements LastFMOntology {
	private static final String similarArtistRequest = "http://ws.audioscrobbler.com/2.0/?method=artist.getsimilar&artist=";
	private static final String artistEvents = "http://ws.audioscrobbler.com/2.0/?method=artist.getevents&artist=";
	private static final String artistCorrection = "http://ws.audioscrobbler.com/2.0/?method=artist.getcorrection&artist=";
	private static final String apiKey = "&api_key=a7123248beb0bbcb90a2e3a9ced3bee9";
	private static final Logger LOGGER = LoggerFactory.getLogger(LastFMOntologyImpl.class);

	private InputStream artistCorrection(String search_string) throws ArtistNotFoundException  {
		InputStream in = null;
		try{ 
			URL lastFMRequest = new URL(artistCorrection + search_string + apiKey);
			LOGGER.debug("LastFM correction request URL: " + lastFMRequest.toExternalForm());
			URLConnection lastFMConnection = lastFMRequest.openConnection();
			in = lastFMConnection.getInputStream();
		} catch(IOException ioexc){ 
			throw new ArtistNotFoundException("No correction found");
		}
		return in;
	}

	private Document docBuilder(String artist) throws ArtistNotFoundException {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		Document doc = null;
		String safe_search = "";
		DocumentBuilder builder = null;
		try {
			builder = domFactory.newDocumentBuilder();
			safe_search = URLEncoder.encode(artist, "UTF-8");
		} catch (Exception e) {/*ignore*/}
		try {
			doc = builder.parse(artistCorrection(safe_search));  
		} catch (Exception a) {
			throw new ArtistNotFoundException ("Doc couldn't be built");
		}
		LOGGER.debug("DOM object with " + artist + " as corrected by LastFM.");

		return doc;
	}
	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.ontology.LastFMOntology#correctArtist(java.lang.String)
	 */
	public String correctArtist(String artist) throws ArtistNotFoundException {
		try{
			Document correction = docBuilder(artist);
			NodeList nameList = correction.getElementsByTagName("name");
			Node nameNode = nameList.item(0);
			Element element = (Element) nameNode;
			NodeList name = element.getChildNodes();
			LOGGER.debug("Get node value of correct artist: " + (name.item(0)).getNodeValue());
			return (name.item(0)).getNodeValue();
		}
		catch (NullPointerException e) {
			return artist;
		}
	}


	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.ontology.LastFMOntology#getArtistEvents(java.lang.String)
	 */
	public InputStream getArtistEvents (String artist) throws Exception {
		String safeArtist = makeWebSafeString(artist);
		URL lastFMRequest = new URL(artistEvents + safeArtist + apiKey);
		URLConnection lastFMConnection = lastFMRequest.openConnection();

		return lastFMConnection.getInputStream();
	}

	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.ontology.LastFMOntology#getSimilarArtist(java.lang.String)
	 */
	public InputStream getSimilarArtist(String artist) throws Exception {
		String safeArtist = makeWebSafeString(artist);
		URL lastFMRequest = new URL(similarArtistRequest + safeArtist + apiKey);
		LOGGER.debug("LastFM request URL: " + lastFMRequest.toExternalForm());
		URLConnection lastFMConnection = lastFMRequest.openConnection();
		LOGGER.debug("Is connection null? " + lastFMConnection);
		return lastFMConnection.getInputStream();
	}


	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.ontology.LastFMOntology#makeWebSafeString(java.lang.String)
	 */
	public String makeWebSafeString(String unsafe){
		try {
			return URLEncoder.encode(unsafe, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return unsafe;
		}
	}



}
