package edu.uib.info310.search.builder.ontology.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.rdf.model.Model;

import edu.uib.info310.search.builder.ontology.AbstractArtistDataSource;
import edu.uib.info310.search.builder.ontology.BBCDataSource;
import edu.uib.info310.sparql.QueryEndPoint;

@Component
public class BBCDataSourceImpl extends AbstractArtistDataSource implements BBCDataSource{
	protected String artistName;
	protected String artistUri;
	
	@Autowired
	protected QueryEndPoint qep;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BBCDataSourceImpl.class);
	private static final String prefix = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
			"PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
			"PREFIX mo: <http://purl.org/ontology/mo/>" +
			"PREFIX dbpedia: <http://dbpedia.org/property/>" +
			"PREFIX dbont: <http://dbpedia.org/ontology/>" +
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
			"PREFIX rc: <http://umbel.org/umbel/rc/>" +
			"PREFIX owl: <http://www.w3.org/2002/07/owl#> ";
	private static final String reducedWhere = " WHERE { OPTIONAL{?artist mo:fanpage ?fanpage}" +
			"OPTIONAL{?artist mo:fanpage ?fanpage}" +
			"OPTIONAL{?artist mo:imdb ?imdb}" +
			"OPTIONAL{?artist mo:myspace ?myspace}" +
			"OPTIONAL{?artist foaf:homepage ?homepage}" +
			"OPTIONAL{?artist rdfs:comment ?comment. FILTER (lang(?comment) = '')}" +
			"OPTIONAL{?artist mo:image ?image}}";

	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.ontology.impl.BBCOntology#getArtistModel(java.lang.String, java.lang.String)
	 */
	public Model getArtistModel(String artistName, String artistUri){
		String artist = "<" + artistUri + ">";
		String constructStr =  this.makeConstruct(artist);
		String whereStr = this.makeWhere(artistName);

		qep.setQuery(prefix + constructStr + whereStr);
		qep.setEndPoint(QueryEndPoint.BBC_MUSIC);
		Model model = qep.constructStatement();
		QueryExecution exec = QueryExecutionFactory.create(prefix + constructStr + reducedWhere, model);
		Model translatedModel = exec.execConstruct();

		LOGGER.debug("BBC search found " + translatedModel.size() + " statements" );
		return translatedModel;
	}

	private String makeWhere(String artistName) {
		return " WHERE {?artist foaf:name \"" + artistName + "\" " + "." +
				"OPTIONAL{?artist mo:fanpage ?fanpage}" +
				"OPTIONAL{?artist mo:fanpage ?fanpage}" +
				"OPTIONAL{?artist mo:imdb ?imdb}" +
				"OPTIONAL{?artist mo:myspace ?myspace}" +
				"OPTIONAL{?artist foaf:homepage ?homepage}" +
				"OPTIONAL{?artist rdfs:comment ?comment. FILTER (lang(?comment) = '')}" +
				"OPTIONAL{?artist mo:image ?image}}";
	}

	private String makeConstruct(String artist) {
		
		return "CONSTRUCT { " + artist +
				"mo:fanpage ?fanpage ; " +
				"mo:imdb ?imdb ; " +
				"mo:myspace ?myspace ; " +
				"mo:homepage ?homepage ; " +
				"rdfs:comment ?comment ; " +
				"mo:image ?image ;" +
				"owl:sameAs ?artist" +
				"} " ;
	}
}
