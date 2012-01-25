package edu.uib.info310.search.builder.ontology;

import com.hp.hpl.jena.rdf.model.Model;

public interface ArtistDataSource extends Runnable{

	public void setModel(Model model);

	public Model getArtistModel(String artistName, String artistUri);

	public void run();

	public void setArtistName(String artistName);

	public void setArtistUri(String artistUri);

}