package edu.uib.info310.search.builder;

import com.hp.hpl.jena.rdf.model.Model;

import edu.uib.info310.exception.ArtistNotFoundException;

public interface OntologyBuilder {

	public Model createArtistOntology(String search_string)
			throws ArtistNotFoundException;

	public Model createRecordOntology(String recordName, String artistName);

}