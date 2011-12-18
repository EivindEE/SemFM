package edu.uib.info310.search.builder.ontology;

import java.io.InputStream;

import edu.uib.info310.exception.MasterNotFoundException;

public interface DiscogDataSource {

	public InputStream getAlbumURI(String releaseId)
			throws MasterNotFoundException;

	public String getRecordReleaseId(String record_name, String artist_name)
			throws MasterNotFoundException;

}