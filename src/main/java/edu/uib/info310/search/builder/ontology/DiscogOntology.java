package edu.uib.info310.search.builder.ontology;

import java.io.InputStream;

import com.hp.hpl.jena.rdf.model.Model;

import edu.uib.info310.exception.MasterNotFoundException;

public interface DiscogOntology {

	public InputStream getAlbumURI(String releaseId)
			throws MasterNotFoundException;

	public String getRecordReleaseId(String record_name, String artist_name)
			throws MasterNotFoundException;

}