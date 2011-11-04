package edu.uib.info310.search;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import edu.uib.info310.transformation.XslTransformer;

public class DiscogSearch {
	private static final String searchAlbum = "http://api.discogs.com/search?q=";
	private static final String searchEnd = "&f=xml";
	private static final String release = "http://api.discogs.com/release/1264945";
	
	public InputStream searchAlbum (String artist, String album) throws Exception {
		
        URL DiscogsRequest = new URL(searchAlbum+artist.replace(' ', '+')+"+"+album.replace(' ', '+')+searchEnd);
        URLConnection DiscogsConnection = DiscogsRequest.openConnection();
        		
		return DiscogsConnection.getInputStream();
    }
	public static void main(String[] args) throws Exception {
		
		File xsl = new File("src/main/resources/XSL/SearchXLST.xsl");
		
		DiscogSearch search = new DiscogSearch();
		XslTransformer transform = new XslTransformer();
		
		transform.setXml(search.searchAlbum("Michael Jackson", "Thriller"));
		transform.setXsl(xsl);
		
		File file = new File("log/rdf-album.xml");
		
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		transform.transform().writeTo(fileOutputStream);
		System.out.println(transform.transform());
	}	
}
