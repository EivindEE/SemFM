package edu.uib.info310.search.builder.ontology.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryException;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

import edu.uib.info310.search.builder.ontology.AbstractRecordDataSource;
import edu.uib.info310.search.builder.ontology.DBTuneDataSource;
import edu.uib.info310.sparql.QueryEndPoint;

@Component
public class DBTuneDataSourceImpl extends AbstractRecordDataSource implements ApplicationContextAware, DBTuneDataSource  {

	@Autowired
	QueryEndPoint qep;
	
	private static final String prefix = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
					"PREFIX dc: <http://purl.org/dc/elements/1.1/>" +
					"PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
					"PREFIX mo: <http://purl.org/ontology/mo/>" +
					"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
					"PREFIX dc: <http://purl.org/dc/terms/>" +
					"PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>"+
					"PREFIX owl: <http://www.w3.org/2002/07/owl#> ";

	private static final Logger LOGGER= LoggerFactory.getLogger(DBTuneDataSource.class);

	private ApplicationContext applicationContext;


	@Override
	public Model getRecordModel(String albumUri, String album, String artistName){
		String record = "<" + albumUri + ">";
		//String constructStr =  this.makeConstruct(artist);
		//String whereStr = this.makeWhere(artistName);

		
		String constructString = makeConstruct(record);
		String whereString = makeWhere(artistName);
		LOGGER.error("Query String: " + prefix + constructString + whereString);

		LOGGER.error("qep toSTring: " + qep.toString());
		qep.setQuery(prefix + constructString + whereString);
		qep.setEndPoint(QueryEndPoint.MUSICBRAINZ);
		Model model = qep.constructStatement();
//		QueryExecution exec = QueryExecutionFactory.create(prefix + constructString + whereString, model);
//		Model transalatedModel = exec.execConstruct();
		
		
		
//		Query query = QueryFactory.create(prefix + constructString + whereString);
//		QueryEngineHTTP queryExecution = QueryExecutionFactory.createServiceRequest(QueryEndPoint.MUSICBRAINZ, query);
//		Model model = queryExecution.execConstruct();
//		
		LOGGER.debug("modelsize=" + model.size());
		return model;

	}


	public static void main(String[] args) {
		String artist_name ="Rhianna";
		String album ="Loud";
		String albumUri ="http://dbtune.org/album/" + album;

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("main-context.xml"); 
		DBTuneDataSourceImpl mag = context.getBean(DBTuneDataSourceImpl.class);

	//	System.out.println("String=" + mag.queryEndPoint(artist_name));
					File file = new File("album");
					FileOutputStream fileOutputStream;
					try {
						fileOutputStream = new FileOutputStream(file);
						mag.getRecordModel(albumUri,album,artist_name).write(fileOutputStream,"TURTLE");
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


	}

	public String queryEndPoint(String artist_name){

		String selectString = "SELECT ?artist";
		String whereString = makeWhere(artist_name);

		LOGGER.debug("selectString= " + selectString + whereString);
		qep.setQuery(prefix + selectString + whereString);
		qep.setEndPoint(QueryEndPoint.MUSICBRAINZ);


		ResultSet albumResult = qep.selectStatement();
	
		String albums =""; 
		while(albumResult.hasNext()){
			QuerySolution querySolution = albumResult.next();
			albums += "\n" + querySolution.get("artist").toString();
		}

		return albums;
	}

	private String makeWhere(String artist_name) {
		return " WHERE {?artist foaf:name \"" + artist_name + "\" " + "." +
				"?album foaf:maker ?artist;" +
				"rdf:type ?type;" +
				"OPTIONAL{?album dc:title ?title}" +
				"OPTIONAL{?album rdfs:label ?label}" +
				"OPTIONAL{?album mo:release ?release}" +
				"}"
				;
	}

	private String makeConstruct(String album) {

		return "CONSTRUCT { " + album + " foaf:maker ?artist;" +
				"rdf:type ?type;" +
				"dc:title ?title;" +
				"rdfs:label ?label;" +
				"mo:release ?release;" +
				"} " ;
	}

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub

	}

	

}
