package edu.uib.info310.search.builder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletContextResourceLoader;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sparql.function.library.e;

import edu.uib.info310.exception.ArtistNotFoundException;
import edu.uib.info310.exception.MasterNotFoundException;
import edu.uib.info310.search.builder.ontology.BBCOntology;
import edu.uib.info310.search.builder.ontology.DBPediaOntology;
import edu.uib.info310.search.builder.ontology.DiscogOntology;
import edu.uib.info310.search.builder.ontology.ITunesOntology;
import edu.uib.info310.search.builder.ontology.LastFMOntology;
import edu.uib.info310.transformation.XslTransformer;

@Component
public class OntologyBuilderImpl implements OntologyBuilder, ApplicationContextAware {

	@Autowired
	private LastFMOntology search;

	@Autowired
	private XslTransformer transformer;

	@Autowired
	private ITunesOntology itunes;

	@Autowired
	private DiscogOntology discog;

	@Autowired
	private BBCOntology bbc;

	@Autowired
	private DBPediaOntology dbp;				
	private static final String SIMILAR_XSL = "WEB-INF/xsl/SimilarArtistLastFM.xsl";
	private static final String ARTIST_EVENTS_XSL = "WEB-INF/xsl/Events.xsl";
	private static final String ALBUM_XSL = "WEB-INF/xsl/AlbumXSLT.xsl";

	private static final Logger LOGGER = LoggerFactory.getLogger(OntologyBuilderImpl.class);

	@Autowired
	private ApplicationContext applicationContext;


	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.OntologyBuilder#createArtistOntology(java.lang.String)
	 */
	public Model createArtistOntology(String search_string) throws ArtistNotFoundException  {
		Model model = ModelFactory.createDefaultModel();
		String correctName = search.correctArtist(search_string);
		
		try {
			transformer.setXml(search.getSimilarArtist(correctName));
			LOGGER.debug(applicationContext.toString());
			File xsl = applicationContext.getResource(SIMILAR_XSL).getFile();
			LOGGER.debug("Using XSL" + xsl.getAbsolutePath());
			transformer.setXsl(xsl);

			InputStream in = new ByteArrayInputStream(transformer.transform().toByteArray());

			model.read(in, null);
			LOGGER.debug("Model size after getting similar artists: " + model.size());
			transformer.setXml(search.getArtistEvents(correctName));
			xsl = applicationContext.getResource(ARTIST_EVENTS_XSL).getFile();
			transformer.setXsl(xsl);

			in = new ByteArrayInputStream(transformer.transform().toByteArray());
			model.read(in, null);
			LOGGER.debug("Model size after getting artist events: " + model.size());
		} catch(Exception e) {
			StringBuilder stackTrace = new StringBuilder();
			for(StackTraceElement ee : e.getStackTrace()){
				stackTrace.append(ee.toString() + "\n");
			}
			LOGGER.error("Threw exception" + e + ", with stack trace " + stackTrace.toString());
		}

		String id = "";
		String artistName = "";
		String getIdQuery = "PREFIX foaf:<http://xmlns.com/foaf/0.1/> PREFIX mo:<http://purl.org/ontology/mo/> SELECT ?id ?name WHERE {?id mo:similar-to ?something; foaf:name ?name . }";
		QueryExecution exec = QueryExecutionFactory.create(getIdQuery, model);
		ResultSet ids = exec.execSelect();
		if(ids.hasNext()){
			QuerySolution sol = ids.next(); 
			id = sol.get("id").asResource().getURI();
			artistName = sol.get("name").asLiteral().getString();
			LOGGER.debug("Sending id:" + id + ", and name: " + artistName + "  to iTunes, BBC and DBPedia");
		}else{
			LOGGER.debug("Did not find id for artist with name " + correctName);
		}

		if(artistName.isEmpty() || artistName == null || model.size() < 3) {
			LOGGER.debug("Artist not found");
			throw new ArtistNotFoundException("Last.FM found name, but no data.");
		}


		model.add(itunes.getRecordsByArtistName(artistName, id));
		LOGGER.debug("Model size after adding record info from iTunes: " + model.size());
		try{
			model.add(bbc.getArtistModel(artistName, id));
			LOGGER.debug("Model size after adding artist info from BBC: " + model.size());
		}
		catch (Exception e) {
			LOGGER.error("Error retreiving model from BBC, maybe the server is down: " + e.toString());
		}
		try{
			model.add(dbp.getArtistModel(artistName, id));
			LOGGER.debug("Model size after adding artist info from DBPedia: " + model.size());
		} 
		catch (Exception e) {
			LOGGER.error("Error retreiving model from DBPedia, maybe the server is down: " + e.toString());
		}

		return  model;
	}

	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.OntologyBuilder#createRecordOntology(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Model createRecordOntology(String releaseId, String record_name, String artist_name){

		LOGGER.debug("Got releaseID: " + releaseId);
		File xsl = null;
		try {
			xsl = applicationContext.getResource(ALBUM_XSL).getFile();
		} catch (IOException e2) {
			LOGGER.error("Got IOException: " + e2.getLocalizedMessage());
		}

		XslTransformer transform = new XslTransformer();

		try {
			transform.setXml(discog.getAlbumURI(releaseId));
		} catch (MasterNotFoundException e1) {
			LOGGER.error(e1.toString());
		}
		transform.setXsl(xsl);

		InputStream in = null;
		try {
			in = new ByteArrayInputStream(transform.transform().toByteArray());
		} catch (TransformerException e1) {
			LOGGER.error(e1.toString());
		}
		Model model = ModelFactory.createDefaultModel();
		model.read(in,null);
		LOGGER.debug("Model size after getting album info: " + model.size());

		String newUriQuery = "PREFIX mo: <http://purl.org/ontology/mo/> SELECT ?discogs WHERE {?o mo:discogs ?discogs.}";
		QueryExecution execution = QueryExecutionFactory.create(newUriQuery, model);
		ResultSet albumResults = execution.execSelect();
		QuerySolution uriResult = albumResults.next();
		String albumUri = "http://api.discogs.com/release/" + uriResult.get("discogs").toString();
		Model itunesModel = itunes.getRecordWithNameAndArtist(albumUri, record_name, artist_name);
		model.add(itunesModel);
		LOGGER.debug("Model size after getting iTunes info: " + model.size());

		return model;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		
	}

}
