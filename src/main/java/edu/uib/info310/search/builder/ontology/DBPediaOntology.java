package edu.uib.info310.search.builder.ontology;

import com.hp.hpl.jena.rdf.model.Model;

public interface DBPediaOntology {

	public Model getArtistModel(String artistName, String artistUri)
			throws Exception;

}