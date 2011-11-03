package edu.uib.info310.search;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import edu.uib.info310.transformation.XslTransformer;

@Component
public class LastFMSearch {
	private static final String similarArtistRequest = "http://ws.audioscrobbler.com/2.0/?method=artist.getsimilar&artist=";
	private static final String artistEvents = "http://ws.audioscrobbler.com/2.0/?method=artist.getevents&artist=";
	private static final String apiKey = "&api_key=a7123248beb0bbcb90a2e3a9ced3bee9";
	private static final Logger LOGGER = LoggerFactory.getLogger(LastFMSearch.class);
	
	public InputStream getArtistEvents (String artist) throws Exception {
		
        URL lastFMRequest = new URL(artistEvents+artist+apiKey);
        URLConnection lastFMConnection = lastFMRequest.openConnection();
        		
		return lastFMConnection.getInputStream();
    }
	
	public InputStream getSimilarArtist(String artist) throws Exception {
        URL lastFMRequest = new URL(similarArtistRequest+artist+apiKey);
        URLConnection lastFMConnection = lastFMRequest.openConnection();
        		
		return lastFMConnection.getInputStream();
    }
	
	
	public static void main(String[] args) throws Exception {
		
		File xsl = new File("src/main/resources/XSL/SimilarArtistLastFM.xsl");
		
		LastFMSearch search = new LastFMSearch();
		XslTransformer transform = new XslTransformer();
		
		transform.setXml(search.getSimilarArtist("Metallica"));
		transform.setXsl(xsl);
		
		File file = new File("log/rdf-artist.xml");
		
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		transform.transform().writeTo(fileOutputStream);
//		System.out.println(transform.transform());
		
	}
	
	
	

}
