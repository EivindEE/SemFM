package edu.uib.info310.search.builder.ontology;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.shared.Lock;

public abstract class AbstractArtistDataSource implements ArtistDataSource {
	protected Model model;
	protected String artistName;
	protected String artistUri;

	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.ontology.ArtistDataSource#setModel(com.hp.hpl.jena.rdf.model.Model)
	 */
	public void setModel(Model model) {
		this.model = model;
	}
	
	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.ontology.ArtistDataSource#getArtistModel(java.lang.String, java.lang.String)
	 */
	public abstract Model getArtistModel(String artistName, String artistUri);

	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.ontology.ArtistDataSource#run()
	 */
	public void run() {
		this.model.add(this.getArtistModel(artistName, artistUri));	
	}
	
	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.ontology.ArtistDataSource#setArtistName(java.lang.String)
	 */
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	
	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.ontology.ArtistDataSource#setArtistUri(java.lang.String)
	 */
	public void setArtistUri(String artistUri) {
		this.artistUri = artistUri;
	}

}
