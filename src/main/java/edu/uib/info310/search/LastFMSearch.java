package edu.uib.info310.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

import edu.uib.info310.transformation.XslTransformer;

public class LastFMSearch {
	private static final String similarArtistRequest = "http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=";
	private static final String artistEvents = "http://ws.audioscrobbler.com/2.0/?method=artist.getevents&artist=";
	private static final String apiKey = "&api_key=a7123248beb0bbcb90a2e3a9ced3bee9";
	
	
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
	
	
//	public static void main(String[] args) throws Exception {
//		
//		File xsl = new File("c:/users/john fredrik/dropbox/workshop/SemFM/src/main/resources/XSL/Events.xsl");
//		XslTransformer transform = new XslTransformer();
//		
//		transform.setXml(getContentLastFM(artistEvents, "Metallica"));
//		transform.setXsl(xsl);
//		
//		System.out.println(transform.transform());
//		
//		
//	
//	}
	
	
	

}
