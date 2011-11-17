package edu.uib.info310.search.builder.ontology;

import com.hp.hpl.jena.rdf.model.Model;

public interface BBCOntology {

	/**
	 * Returns a model with data from BBC_MUSIC SPARQL search
	 * @returns a Model
	 */
	public Model getArtistModel(String artistName, String artistUri)
			throws Exception;

}