package edu.uib.info310.search.builder.ontology;

import com.hp.hpl.jena.rdf.model.Model;

public interface RecordDataSource extends Runnable {

	public Model getRecordModel(String albumUri, String album, String artistName);

	public void run();

	public void setAlbum(String album);

	public void setAlbumUri(String albumUri);

	public void setArtistName(String artistName);

	public void setModel(Model model);

}