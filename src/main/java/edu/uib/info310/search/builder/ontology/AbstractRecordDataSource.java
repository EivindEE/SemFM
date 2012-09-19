package edu.uib.info310.search.builder.ontology;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.hpl.jena.rdf.model.Model;

public abstract class AbstractRecordDataSource implements  RecordDataSource {
	protected Model model;
	protected String albumUri;
	protected String album;
	protected String artistName;
	
	private Logger LOGGER = LoggerFactory.getLogger(AbstractRecordDataSource.class); 

	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.ontology.RecordDataSource#getRecordModel(java.lang.String, java.lang.String, java.lang.String)
	 */
	public abstract Model getRecordModel(String albumUri, String album, String artistName);
	
	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.ontology.RecordDataSource#run()
	 */
	public void run() {
		Model recordModel = this.getRecordModel(albumUri, album, artistName);
		LOGGER.debug("Record model size from " + this.getClass() + recordModel.size());
		this.model.add(recordModel);	
		LOGGER.debug("Total model size " + model.size());
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
