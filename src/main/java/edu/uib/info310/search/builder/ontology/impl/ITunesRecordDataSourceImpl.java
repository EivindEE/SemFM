package edu.uib.info310.search.builder.ontology.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

import edu.uib.info310.search.builder.ontology.AbstractRecordDataSource;
import edu.uib.info310.search.builder.ontology.ITunesRecordDataSource;
import edu.uib.info310.vocabulary.MO;

@Component
public class ITunesRecordDataSourceImpl extends AbstractRecordDataSource implements ITunesRecordDataSource {
	protected static final String ALBUM = "http://itunes.apple.com/search?entity=album&limit=200&country=NO&term=";
	private static String TRACK = "http://itunes.apple.com/search?entity=musicTrack&limit=1000&country=NO&term=";
	private static String SINGLE = " - Single";
	private static String EP = " - EP";
	private static Logger LOGGER = LoggerFactory.getLogger(ITunesRecordDataSourceImpl.class);
	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.ontology.impl.ITunesRecordDataSource#getRecordModel(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Model getRecordModel(String albumUri, String album, String artistName){
		Model model = ModelFactory.createDefaultModel();
		Resource subject;
		Property property;
		RDFNode  rdfObject;
		try {
			URL url = new URL((ALBUM + URLEncoder.encode(artistName, "UTF-8")));
			URLConnection connection = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(reader);
			JSONObject response = (JSONObject) obj;
			JSONArray results = (JSONArray) response.get("results");
			for (Object object : results) {
				JSONObject jObject = (JSONObject) object;
				String iTunesArtist = jObject.get("artistName").toString().toLowerCase();
				String iTunesAlbum = jObject.get("collectionName").toString().toLowerCase();
				if(iTunesArtist.equals(artistName.toLowerCase()) && iTunesAlbum.equals(album.toLowerCase())){
					subject = model.createResource(albumUri);
					property = MO.image;
					rdfObject = model.createResource(jObject.get("artworkUrl100").toString());
					model.add(subject, property, rdfObject);
					
					property = MO.genre;
					rdfObject = model.createLiteral(jObject.get("primaryGenreName").toString());
					model.add(subject, property, rdfObject);
					
					property = RDFS.label;
					rdfObject = model.createLiteral(jObject.get("collectionName").toString());
					model.add(subject, property, rdfObject);
					
					
					property = RDF.type;
					rdfObject = MO.Record;
					model.add(subject, property, rdfObject);
					
					property = OWL.sameAs;
					rdfObject = model.createResource(jObject.get("collectionViewUrl").toString());
					model.add(subject, property, rdfObject);
					
					property = FOAF.maker;
					rdfObject = model.createResource(jObject.get("artistName").toString());
					model.add(subject, property, rdfObject);
					
					subject = model.createResource(jObject.get("artistName").toString());
					property = FOAF.name;
					rdfObject = model.createLiteral(jObject.get("artistName").toString());
					model.add(subject, property, rdfObject);

					
				}
				
			}
			
			url = new URL((TRACK + URLEncoder.encode(artistName + " " + album, "UTF-8")));
			connection = url.openConnection();
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			parser = new JSONParser();
			obj = parser.parse(reader);
			response = (JSONObject) obj;
			results = (JSONArray) response.get("results");
			for (Object object : results) {
				JSONObject jObject = (JSONObject) object;
				String iTunesArtist = jObject.get("artistName").toString().toLowerCase();
				String iTunesAlbum = jObject.get("collectionName").toString().toLowerCase();
				if(iTunesArtist.equals(artistName.toLowerCase()) && iTunesAlbum.equals(album.toLowerCase())){
					String trackUri = albumUri + "/track/" + jObject.get("trackNumber").toString();
					subject = model.createResource(albumUri);
					property = MO.track;
					rdfObject = model.createResource(trackUri);
					model.add(subject, property, rdfObject);
					
					subject = model.createResource(trackUri);
					property = MO.preview;
					rdfObject = model.createLiteral(jObject.get("previewUrl").toString());
					model.add(subject, property, rdfObject);
					
					property = MO.track_number;
					rdfObject = model.createLiteral(jObject.get("trackNumber").toString());
					model.add(subject, property, rdfObject);
					
					property = RDF.type;
					rdfObject = MO.Track;
					model.add(subject, property, rdfObject);
					
					property = model.createProperty("http://www.w3.org/2006/time#duration");
					long timeMillis = Long.parseLong(jObject.get("trackTimeMillis").toString());
					long time = timeMillis / 1000;  
					String seconds = Integer.toString((int)(time % 60));  
					String minutes = Integer.toString((int)((time % 3600) / 60));  
					for (int i = 0; i < 2; i++) {  
						if (seconds.length() < 2) {  
						seconds = "0" + seconds;  
						}
					}
					rdfObject = model.createLiteral(minutes + ":" + seconds);  
					model.add(subject, property, rdfObject);
					
					property = FOAF.name;
					rdfObject = model.createLiteral(jObject.get("trackName").toString());
					model.add(subject, property, rdfObject);
				}
				
			}
			
		}
		catch (Exception e) {
			LOGGER.error("Caught an exception: " + e.toString());
		}
		return model;
	}

}
