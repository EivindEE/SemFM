package edu.uib.info310;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * Convenience class for sending SPARQL queries to SPARQL endpoints
 * @author eivindelseth
 *
 */


public class QueryEndPoint {
	public static final String BBC_MUSIC = "http://api.talis.com/stores/bbc-backstage/services/sparql";
	public static final String DB_PEDIA = "http://dbpedia.org/sparql";
	/**
	 * Returns the ResultSet of the query on the selected service 
	 * @param service, A String representation the URI of the SPARQL endpoint
	 * @param queryString, A String representation of a SPARQL query
	 * @return the ResultSet
	 */
	public static ResultSet selectStatement(String service, String queryString){
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(service,query);
	
		return qexec.execSelect();
	}
	
	/**
	 * Returns the ResultSet of the query on the selected service 
	 * @param service, A String representation the URI of the SPARQL endpoint
	 * @param query, A SPARQL query
	 * @return the ResultSet
	 */
	public static ResultSet selectStatement(String service, Query query){
		QueryExecution qexec = QueryExecutionFactory.sparqlService(service,query);
		return qexec.execSelect();
	}
	
	/**
	 * Returns the model of the query on the selected service 
	 * @param service, A String representation the URI of the SPARQL endpoint
	 * @param queryString, A String representation of a SPARQL query
	 * @return the Model
	 */
	public static Model describeStatement(String service, String queryString){
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(service,query);
		
		return qexec.execDescribe();
	}
	
	/**
	 * Returns the model of the query on the selected service 
	 * @param service, A String representation the URI of the SPARQL endpoint
	 * @param query, A SPARQL query
	 * @return the Model
	 */
	public static Model describeStatement(String service, Query query){
		QueryExecution qexec = QueryExecutionFactory.sparqlService(service,query);
		return qexec.execDescribe();
	}
	
	/**
	 * Returns the model of the query on the selected service 
	 * @param service, A String representation the URI of the SPARQL endpoint
	 * @param queryString, A String representation of a SPARQL query
	 * @return the Model
	 */
	public static Model constructStatement(String service, String queryString){
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(service,query);
		
		return qexec.execConstruct();
	}
	
	/**
	 * Returns the model of the query on the selected service 
	 * @param service, A String representation the URI of the SPARQL endpoint
	 * @param query, A SPARQL query
	 * @return the Model
	 */
	public static Model constructStatement(String service, Query query){
		QueryExecution qexec = QueryExecutionFactory.sparqlService(service,query);
		return qexec.execConstruct();
	}
	
	/**
	 * Returns true if the requested graph exists 
	 * @param service, A String representation the URI of the SPARQL endpoint
	 * @param queryString, A String representation of a SPARQL query
	 * @return true iff the requested graph exits
	 */
	public static boolean askStatement(String service, String queryString){
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(service,query);
		
		return qexec.execAsk();
	}
	
	/**
	 * Returns true if the requested graph exists 
	 * @param service, A String representation the URI of the SPARQL endpoint
	 * @param query, A SPARQL query
	 * @return true iff the requested graph exits
	 */
	public static boolean askStatement(String service, Query query){
		QueryExecution qexec = QueryExecutionFactory.sparqlService(service,query);
		return qexec.execAsk();
	}
	
	
	/**
	 * Example of use
	 * @param args
	 */
	public static void main(String[] args) {
		String query = "PREFIX rdf:<http://www.w3.org/2000/01/rdf-schema#> PREFIX dbp:<http://dbpedia.org/property/> SELECT ?location WHERE { ?person rdf:label 'George Washington'@en. ?location dbp:namedFor ?person }";
		ResultSet rs = QueryEndPoint.selectStatement(QueryEndPoint.DB_PEDIA, query);
		ResultSetFormatter.out(rs);
	}
}
