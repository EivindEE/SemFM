package edu.uib.info310.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.DCTerms;
import com.hp.hpl.jena.vocabulary.DC_10;
import com.hp.hpl.jena.vocabulary.DC_11;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;

import edu.uib.info310.vocabulary.MO;




public class ITunesSearcher {
	private static String DEFAULT_URL = "http://itunes.apple.com/search?entity=album&limit=200&term=";
	private static final Logger LOGGER = LoggerFactory.getLogger(ITunesSearcher.class);

	public Model getRecords(String artist, String artistUri){
		Model model = ModelFactory.createDefaultModel();
		Resource subject;
		Property property;
		RDFNode  rdfObject;
		try {
			URL url = new URL((DEFAULT_URL + URLEncoder.encode(artist, "UTF-8")));
			URLConnection connection = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(reader);
			JSONObject response = (JSONObject) obj;
			JSONArray results = (JSONArray) response.get("results");
			for (Object object : results) {
				JSONObject jObject = (JSONObject) object;
				String iTunesArtist = jObject.get("artistName").toString().toLowerCase();
				if(iTunesArtist.equals(artist.toLowerCase()) && !jObject.get("collectionName").toString().contains(")")){
					subject = model.createResource(jObject.get("collectionViewUrl").toString());
					property = FOAF.depiction;
					rdfObject = model.createResource(jObject.get("artworkUrl100").toString());
					model.add(subject, property, rdfObject);

					property = DCTerms.title;
					rdfObject = model.createResource(jObject.get("collectionName").toString());
					model.add(subject, property, rdfObject);
					
					property = RDF.type;
					rdfObject = MO.Record;
					model.add(subject, property, rdfObject);
					
					property = DCTerms.issued;
					rdfObject = model.createLiteral(jObject.get("releaseDate").toString());
					model.add(subject, property, rdfObject);

					property = MO.genre;
					rdfObject = model.createLiteral(jObject.get("primaryGenreName").toString());
					model.add(subject, property, rdfObject);

					property = FOAF.maker;
					rdfObject = model.createResource(artistUri);
					model.add(subject, property, rdfObject);
					
					subject = model.createResource(artistUri);
					property = FOAF.made;
					rdfObject = model.createResource(jObject.get("collectionViewUrl").toString());
					model.add(subject, property, rdfObject);
					
					property = OWL.sameAs;
					rdfObject = model.createResource(jObject.get("artistViewUrl").toString());
					model.add(subject, property, rdfObject);
				}
			}

		} catch (MalformedURLException e) {
			LOGGER.error("Got MalformedURLException: " + e.toString());
		} catch (IOException e) {
			LOGGER.error("Got IOException: " + e.toString());
		} catch (ParseException e) {
			LOGGER.error("Got ParseException: " + e.toString());
		}

		try {
			FileOutputStream out = new FileOutputStream(new File("log/iTunesOut.ttl"));
			model.write(out, "TURTLE");
		} catch (FileNotFoundException e) {	LOGGER.error("Got FileNotFoundException: " + e.toString());	}
		
		if(model.isEmpty()){
			try {
				LOGGER.debug("Got 0 results for query: " + DEFAULT_URL + URLEncoder.encode(artist, "UTF-8"));
			} catch (UnsupportedEncodingException e) {			}
		}
		return model;
	}
}
