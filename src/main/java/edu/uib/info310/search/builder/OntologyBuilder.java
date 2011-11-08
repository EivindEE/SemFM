package edu.uib.info310.search.builder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;

import edu.uib.info310.search.ArtistNotFoundException;
import edu.uib.info310.search.DiscogSearch;
import edu.uib.info310.search.ITunesSearcher;
import edu.uib.info310.search.LastFMSearch;
import edu.uib.info310.transformation.XslTransformer;
import edu.uib.info310.util.GetArtistInfo;

@Component
public class OntologyBuilder {
	
	
	private LastFMSearch search = new LastFMSearch();
	
	
	private XslTransformer transformer = new XslTransformer();

	private static final String SIMILAR_XSL = "src/main/resources/XSL/SimilarArtistLastFM.xsl";
	private static final String ARTIST_EVENTS_XSL = "src/main/resources/XSL/Events.xsl";
	private static final Logger LOGGER = LoggerFactory.getLogger(OntologyBuilder.class);
//	private DiscogSearch disc = new DiscogSearch();
	private ITunesSearcher itunes = new ITunesSearcher();
	public Model createArtistOntology(String search_string) throws ArtistNotFoundException {
		Model model = ModelFactory.createDefaultModel();
		String correctName = search.correctArtist(search_string);
		try{
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
			
			model.add(itunes.getRecords(correctName));
			LOGGER.debug("Model size after adding record info from iTunes: " + model.size());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		// get BBC_MUSIC & DB_PEDIA model and add to model
		try {
			model.add(GetArtistInfo.ArtistInfo(correctName));
			LOGGER.debug("Finished BBC");
			model.add(GetArtistInfo.DbPediaArtistInfo(correctName));
			LOGGER.debug("Finished DBPedia");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String queryStr = "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX mo:<http://purl.org/ontology/mo/> PREFIX foaf:<http://xmlns.com/foaf/0.1/> PREFIX owl:<http://www.w3.org/2002/07/owl#> " +
				"	CONSTRUCT {?artist owl:sameAs ?artist2.} " +
				" WHERE {?artist foaf:name ?name. ?artist2 foaf:name ?name.  FILTER(?artist != ?artist2)}";
		QueryExecution execution = QueryExecutionFactory.create(queryStr, model);
		model.add(execution.execConstruct());
		LOGGER.debug("Model size after using construct statement: " + model.size());
		
//		Reasoner reasoner = ReasonerFactoryAssembler.
		Reasoner reasoner = ReasonerRegistry.getOWLMicroReasoner();
		reasoner.bindSchema(model);
		return ModelFactory.createInfModel(reasoner, model);
	}

}
