package edu.uib.info310.search.builder.ontology;

import com.hp.hpl.jena.rdf.model.Model;

public interface ITunesOntology {

	public Model getRecordsByArtistName(String artist, String artistUri);

	public Model getRecordWithNameAndArtist(String albumUri, String album,
			String artist);

}