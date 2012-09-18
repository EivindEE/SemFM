package edu.uib.info310.sparql;

import java.net.URL;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * Convenience class for sending SPARQL queries to SPARQL endpoints
 * @author eivindelseth
 */
public interface QueryEndPoint {

	public static final String BBC_MUSIC = "http://api.talis.com/stores/bbc-backstage/services/sparql";
	public static final String DB_PEDIA = "http://dbpedia.org/sparql";
	public static final String DB_PEDIA_LIVE = "http://live.dbpedia.org/sparql";
	public static final String DISCOGS = "http://kasabi.com/dataset/discogs/apis/sparql";
	public static final String MUSICBRAINZ = "http://dbtune.org/musicbrainz/sparql";
	/**
	 * Returns a ResultSet containg the results of the query
	 * @return A ResultSet
	 */
	public abstract ResultSet selectStatement();

	/**
	 * Returns the model of the query on the selected service 
	 * @return the Model
	 */
	public abstract Model describeStatement();

	/**
	 * Returns the model of the query on the selected service 
	 * @return the Model
	 */
	public abstract Model constructStatement();

	/**
	 * Returns true if the requested graph exists 
	 * @return true iff the requested graph exits
	 */
	public abstract boolean askStatement();

	/**
	 * @return the query
	 */
	public abstract Query getQuery();

	/**
	 * @param query the query to set
	 */
	public abstract void setQuery(Query query);

	/**
	 * @param query A string representation of the query to set
	 */
	public abstract void setQuery(String query);

	/**
	 * @return the endPoint
	 */
	public abstract String getEndPoint();

	/**
	 * @param endPoint the endPoint to set
	 */
	public abstract void setEndPoint(String endPoint);

	/**
	 * @param endPoint the URL of the endPoint to set
	 */
	public abstract void setEndPoint(URL endPoint);

}