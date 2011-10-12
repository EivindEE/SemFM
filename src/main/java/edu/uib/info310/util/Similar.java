package edu.uib.info310.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

import edu.uib.info310.sparql.QueryEndPoint;
import edu.uib.info310.sparql.QueryEndPointImp;

public class Similar {
	private String defaultURL = "http://www.tastekid.com/ask/ws?q=";
	
	@Autowired
	private QueryEndPoint endpoint;
	
	public Model addSimilarArtists(Model model, String artist) throws MalformedURLException{
		URL queryUrl = new URL(createQueryURL(artist));

		return model;
	}

	public List<String> similarArtists(String artist) throws ParserConfigurationException, SAXException, IOException{
		SAXParserFactory pf = SAXParserFactory.newInstance();
		SAXParser parser = pf.newSAXParser();
		SimilarHandeler handeler = new SimilarHandeler();
		parser.parse(createQueryURL(artist), handeler);
		return handeler.getSimilar();
	}



	private String createQueryURL(String artist) {
		String artistQuery = artist.replace(' ', '+');
		return defaultURL + artistQuery;
	}


	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		Similar s = new Similar();
		List<String> list = s.similarArtists("Rihanna");
		QueryEndPoint endpoint = new QueryEndPointImp();
		endpoint.setEndPoint(QueryEndPoint.BBC_MUSIC);
		String start = "PREFIX foaf: <http://xmlns.com/foaf/0.1/> PREFIX mo:<http://purl.org/ontology/mo/> SELECT ?artist WHERE{ ?artist foaf:name'";
		String stop = "'}";
		System.out.println("d");
		for(String artist : list){
			endpoint.setQuery(start + artist + stop);
			ResultSet rs = endpoint.selectStatement();
			while(rs.hasNext())
				  System.out.println(rs.next());
		}
		System.out.println("j");
		
	}

}
