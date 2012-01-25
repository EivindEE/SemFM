package edu.uib.info310.search.builder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import edu.uib.info310.exception.ArtistNotFoundException;
import edu.uib.info310.search.builder.ontology.LastFMDataSource;
import edu.uib.info310.search.builder.ontology.impl.DiscogsRecordDataSourceImpl;
import edu.uib.info310.search.builder.ontology.impl.ITunesRecordDataSourceImpl;
import edu.uib.info310.transformation.XslTransformer;

@Component
public class OntologyBuilderImpl implements OntologyBuilder, ApplicationContextAware {

	@Autowired
	private LastFMDataSource search;

	@Autowired
	private XslTransformer transformer;

	@Autowired
	private RecordDataSources recordDataSources;

	
	
	@Autowired
	private ArtistDataSources artistDataSources;

	private static final String SIMILAR_XSL = "WEB-INF/xsl/SimilarArtistLastFM.xsl";
	private static final String ARTIST_EVENTS_XSL = "WEB-INF/xsl/Events.xsl";
//	private static final String ALBUM_XSL = "WEB-INF/xsl/AlbumXSLT.xsl";

	private static final String ARTIST_WITH_URI_XSL = "WEB-INF/xsl/ArtistWithURI.xsl";
	private static final String EVENT_WITH_URI_XSL = "WEB-INF/xsl/EventWithURI.xsl";
	private static final String LOCAL_RECORD_URI = "http://csaba.dyndns.ws:8080/SemFM/album?q=";

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
		artistDataSources.init();
		artistDataSources.setArtistName(artistName);
		artistDataSources.setArtistUri(id);
		artistDataSources.setModel(model);
		artistDataSources.getArtistModel();
		

		return  model;
	}

	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.OntologyBuilder#createRecordOntology(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Model createRecordOntology(String recordName, String artistName){
		Model model = ModelFactory.createDefaultModel();
		LOGGER.debug("Creating ontology for record " + recordName + " by artist " + artistName);
		String recordUri = LOCAL_RECORD_URI + webSafe(recordName) + "&artist=" +  webSafe(artistName) ;
		
		recordDataSources.init();
		recordDataSources.setModel(model);
		recordDataSources.setAlbumUri(recordUri);
		recordDataSources.setRecord(recordName);
		recordDataSources.setArtistName(artistName);
		recordDataSources.buildRecordModel();
		LOGGER.debug("Model size after getting iTunes info: " + model.size());

		return model;
	}
	
	private String webSafe(String item){
		try {
			return URLEncoder.encode(item, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("{item} was not encoded, got exception {e}. Returning unmodified string");
			return item;
		}
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		
	}

}
