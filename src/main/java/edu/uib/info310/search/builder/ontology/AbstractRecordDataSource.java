package edu.uib.info310.search.builder.ontology;

import com.hp.hpl.jena.rdf.model.Model;

public abstract class AbstractRecordDataSource implements  RecordDataSource {
	protected Model model;
	protected String albumUri;
	protected String album;
	protected String artistName;

	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.ontology.RecordDataSource#getRecordModel(java.lang.String, java.lang.String, java.lang.String)
	 */
	public abstract Model getRecordModel(String albumUri, String album, String artistName);
	
	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.ontology.RecordDataSource#run()
	 */
	public void run() {
		this.model.add(this.getRecordModel(albumUri, album, artistName));	
	}

	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.ontology.RecordDataSource#setAlbum(java.lang.String)
	 */
	public void setAlbum(String album) {
		this.album = album;
	}
	
	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.ontology.RecordDataSource#setAlbumUri(java.lang.String)
	 */
	public void setAlbumUri(String albumUri) {
		this.albumUri = albumUri;
	}

	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.ontology.RecordDataSource#setArtistName(java.lang.String)
	 */
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.ontology.RecordDataSource#setModel(com.hp.hpl.jena.rdf.model.Model)
	 */
	public void setModel(Model model) {
		this.model = model;
	}	
}
