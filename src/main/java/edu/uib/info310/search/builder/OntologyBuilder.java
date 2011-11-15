package edu.uib.info310.search.builder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import edu.uib.info310.search.ArtistNotFoundException;
import edu.uib.info310.search.DiscogSearch;
import edu.uib.info310.search.ITunesSearcher;
import edu.uib.info310.search.LastFMSearch;
import edu.uib.info310.search.MasterNotFoundException;
import edu.uib.info310.transformation.XslTransformer;
import edu.uib.info310.util.GetArtistInfo;

@Component
public class OntologyBuilder {

	@Autowired
	private LastFMSearch search;

	@Autowired
	private XslTransformer transformer;
	
	@Autowired
	private ITunesSearcher itunes;
	
	@Autowired
	private DiscogSearch discog;

	private static final String SIMILAR_XSL = "src/main/resources/XSL/SimilarArtistLastFM.xsl";
	private static final String ARTIST_EVENTS_XSL = "src/main/resources/XSL/Events.xsl";
	private static final Logger LOGGER = LoggerFactory.getLogger(OntologyBuilder.class);

	
	public Model createArtistOntology(String search_string) throws ArtistNotFoundException {
		Model model = ModelFactory.createDefaultModel();
		String correctName = search.correctArtist(search_string);
		
		try {
			transformer.setXml(search.getSimilarArtist(correctName));
			transformer.setXsl(new File(SIMILAR_XSL));

			InputStream in = new ByteArrayInputStream(transformer.transform().toByteArray());
			model.read(in, null);
			LOGGER.debug("Model size after getting similar artists: " + model.size());
			transformer.setXml(search.getArtistEvents(correctName));
			transformer.setXsl(new File(ARTIST_EVENTS_XSL));

			in = new ByteArrayInputStream(transformer.transform().toByteArray());
			model.read(in, null);
			LOGGER.debug("Model size after getting artist events: " + model.size());
		} catch(Exception e) {
			LOGGER.error("Threw exception" + e);
		}
		String id = "";
		String artistName = "";
		String getIdQuery = "PREFIX foaf:<http://xmlns.com/foaf/0.1/> PREFIX mo:<http://purl.org/ontology/mo/> SELECT ?id ?name WHERE {?id mo:similar-to ?something; foaf:name ?name . }";
		QueryExecution exec = QueryExecutionFactory.create(getIdQuery, model);
		ResultSet ids = exec.execSelect();
		if(ids.hasNext()){
			QuerySolution sol =ids.next(); 
			id = sol.get("id").toString();
			artistName = sol.get("name").toString();
			LOGGER.debug("Sending id:" + id + ", and name: " + artistName + "  to iTunes, BBC and DBPedia");
		}else{
			LOGGER.debug("Did not find id for artist with name " + correctName);
		}

		if(artistName.isEmpty() || artistName==null || model.size() < 3) {
			LOGGER.debug("Artist not found");
			throw new ArtistNotFoundException("Last.FM found name, but no data.");
		}
		
		try{
			model.add(itunes.getRecords(artistName, id));
			LOGGER.debug("Model size after adding record info from iTunes: " + model.size());

			model.add(GetArtistInfo.BBCMusic(artistName, id));
			LOGGER.debug("Model size after adding artist info from BBC: " + model.size());

			model.add(GetArtistInfo.DBPedia(artistName, id));
			LOGGER.debug("Model size after adding artis info from DBPedia: " + model.size());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		try {
			FileOutputStream out = new FileOutputStream(new File("log/ontout.ttl"));
			model.write(out,"TURTLE");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return  model;
	}
	
	public Model createRecordOntology(String search_string){
			
			LOGGER.debug(search_string);
			File xsl = new File("src/main/resources/XSL/AlbumXSLT.xsl");
				
			XslTransformer transform = new XslTransformer();
			
			try {
				transform.setXml(discog.getAlbumURI(search_string));
			} catch (MasterNotFoundException e1) {
				LOGGER.error(e1.toString());
			}
			transform.setXsl(xsl);
			
			Model model = ModelFactory.createDefaultModel();
			InputStream in = null;
			try {
				in = new ByteArrayInputStream(transform.transform().toByteArray());
			} catch (TransformerException e1) {
				LOGGER.error(e1.toString());
			}
			model.read(in,null);
			LOGGER.debug("Model size after getting album info: " + model.size());
			try {
				FileOutputStream out = new FileOutputStream(new File("log/albumout.ttl"));
				model.write(out,"TURTLE");
			} catch (FileNotFoundException e) {
				LOGGER.error(e.toString());
			}
			
			return model;
		
		
	}

}
