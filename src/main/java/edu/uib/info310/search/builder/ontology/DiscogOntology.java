package edu.uib.info310.search.builder.ontology;

import java.io.InputStream;

import com.hp.hpl.jena.rdf.model.Model;

import edu.uib.info310.exception.MasterNotFoundException;

public interface DiscogOntology {

	public Model getDiscography(String search_string);

	public Model getTracks(String search_string);

	public Model getAlbum(String search_string);

	public Model getAlbums(String search_string);

	public InputStream getAlbumURI(String releaseId)
			throws MasterNotFoundException;

	public String getRecordReleaseId(String record_name, String artist_name)
			throws MasterNotFoundException;

}