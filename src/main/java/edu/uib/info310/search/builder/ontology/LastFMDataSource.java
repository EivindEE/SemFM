package edu.uib.info310.search.builder.ontology;

import java.io.InputStream;

import edu.uib.info310.exception.ArtistNotFoundException;

public interface LastFMDataSource {

	public String correctArtist(String artist) throws ArtistNotFoundException;

	public InputStream getArtistEvents(String artist) throws Exception;

	public InputStream getSimilarArtist(String artist) throws Exception;

}