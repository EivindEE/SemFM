package edu.uib.info310.search.builder.ontology;

import com.hp.hpl.jena.rdf.model.Model;

public interface DBPediaDataSource {

	public Model getArtistModel(String artistName, String artistUri)
			throws Exception;
	
	public void setArtistName(String artistName);
	
	public void setArtistUri(String artistUri);

}