package edu.uib.info310.sparql;

import java.net.URL;

import org.springframework.stereotype.Component;

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
 */

@Component
public class QueryEndPointImp implements QueryEndPoint {
	private Query query;
	private String endPoint;

	/* (non-Javadoc)
	 * @see edu.uib.info310.sparql.QueryEndPoint#selectStatement()
	 */
	public ResultSet selectStatement(){
		QueryExecution qexec = QueryExecutionFactory.sparqlService(this.endPoint,query);
		return qexec.execSelect();
	}

	
	/* (non-Javadoc)
	 * @see edu.uib.info310.sparql.QueryEndPoint#describeStatement()
	 */
	public Model describeStatement(){
		QueryExecution qexec = QueryExecutionFactory.sparqlService(endPoint,query);
		return qexec.execDescribe();
	}
	
	/* (non-Javadoc)
	 * @see edu.uib.info310.sparql.QueryEndPoint#constructStatement()
	 */
	public Model constructStatement(){
		QueryExecution qexec = QueryExecutionFactory.sparqlService(endPoint,query);
		return qexec.execConstruct();
	}

	
	/* (non-Javadoc)
	 * @see edu.uib.info310.sparql.QueryEndPoint#askStatement()
	 */
	public  boolean askStatement(){
		QueryExecution qexec = QueryExecutionFactory.sparqlService(endPoint,query);
		return qexec.execAsk();
	}	
	
	/* (non-Javadoc)
	 * @see edu.uib.info310.sparql.QueryEndPoint#getQuery()
	 */
	public Query getQuery() {
		return query;
	}

	/* (non-Javadoc)
	 * @see edu.uib.info310.sparql.QueryEndPoint#setQuery(com.hp.hpl.jena.query.Query)
	 */
	public void setQuery(Query query) {
		this.query = query;
	}
	
	/* (non-Javadoc)
	 * @see edu.uib.info310.sparql.QueryEndPoint#setQuery(java.lang.String)
	 */
	public void setQuery(String query) {
		this.query = QueryFactory.create(query);
	}

	/* (non-Javadoc)
	 * @see edu.uib.info310.sparql.QueryEndPoint#getEndPoint()
	 */
	public String getEndPoint() {
		return endPoint;
	}

	/* (non-Javadoc)
	 * @see edu.uib.info310.sparql.QueryEndPoint#setEndPoint(java.lang.String)
	 */
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	
	/* (non-Javadoc)
	 * @see edu.uib.info310.sparql.QueryEndPoint#setEndPoint(java.net.URL)
	 */
	public void setEndPoint(URL endPoint) {
		this.endPoint = endPoint.toString();
	}

	/**
	 * Example of use
	 * @param args
	 */
	public static void main(String[] args) {
		QueryEndPoint qep = new QueryEndPointImp();
		String query = "PREFIX rdf:<http://www.w3.org/2000/01/rdf-schema#> PREFIX dbp:<http://dbpedia.org/property/> SELECT * WHERE { ?person rdf:label 'George Washington'@en. ?location dbp:namedFor ?person }";
		qep.setQuery(query);
		qep.setEndPoint(QueryEndPoint.DB_PEDIA);
		
		ResultSet rs = qep.selectStatement();
		ResultSetFormatter.out(rs);
	}
}
