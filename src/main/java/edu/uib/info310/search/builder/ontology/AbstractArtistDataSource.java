package edu.uib.info310.search.builder.ontology;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.shared.Lock;

public abstract class AbstractArtistDataSource implements Runnable {
	protected Model model;
	protected String artistName;
	protected String artistUri;

	public void setModel(Model model) {
		this.model = model;
	}
	
	public abstract Model getArtistModel(String artistName, String artistUri);

	public void run() {
		this.model.add(this.getArtistModel(artistName, artistUri));	
	}
	
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	
	public void setArtistUri(String artistUri) {
		this.artistUri = artistUri;
	}

}
