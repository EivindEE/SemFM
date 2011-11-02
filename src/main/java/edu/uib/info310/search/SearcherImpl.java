package edu.uib.info310.search;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;

import javassist.bytecode.ByteArray;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import edu.uib.info310.model.Artist;
import edu.uib.info310.model.Event;
import edu.uib.info310.model.Record;
import edu.uib.info310.model.Track;
import edu.uib.info310.transformation.XslTransformer;

public class SearcherImpl implements Searcher {

	public Artist searchArtist(String search_string) {
		LastFMSearch search = new LastFMSearch();
		XslTransformer transformer = new XslTransformer();
		Model model = ModelFactory.createDefaultModel();
		try {
			transformer.setXml(search.getArtistEvents(search_string));
			transformer.setXsl(new File("src/main/resources/XSL/Events.xsl"));
			
			
			InputStream in = new ByteArrayInputStream(transformer.transform().toByteArray());
			
			System.out.println(model.size());
			model.read(in, null);
			System.out.println(model.size());
			StmtIterator itr = model.listStatements();
			while(itr.hasNext()){
				
				System.out.println(itr.next());
						
			}
			
			

//			System.out.println(transformer.transform());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return null;
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
		searcher.searchArtist("Metallica");
	}

}
