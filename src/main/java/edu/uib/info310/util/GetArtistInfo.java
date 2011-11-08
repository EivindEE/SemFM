package edu.uib.info310.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;

import edu.uib.info310.sparql.QueryEndPoint;
import edu.uib.info310.sparql.QueryEndPointImp;


public abstract class GetArtistInfo implements QueryEndPoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetArtistInfo.class);
	public static String prefix = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
			"PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
			"PREFIX mo: <http://purl.org/ontology/mo/>" +
			"PREFIX dbpedia: <http://dbpedia.org/property/>" +
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
			"PREFIX owl: <http://www.w3.org/2002/07/owl#>";

	public static Model ArtistInfo(String artistName){
		String queryString;

		if(artistName.isEmpty()){
			System.out.println("No artist selected");
			return null;
		}
		else {
			QueryEndPoint qep = new QueryEndPointImp();
			String query = "DESCRIBE *  WHERE { " +
					"?artist foaf:name \""+ artistName + "\". " +
					" }";
			qep.setQuery(prefix + query);
			qep.setEndPoint(QueryEndPoint.BBC_MUSIC);

			Model rm = qep.describeStatement();
			//ResultSetFormatter.out(rs);

			return rm;
		}				
	}

	public static Model DbPediaArtistInfo(String artistName) throws Exception{
		if(artistName.isEmpty()){
			throw new Exception("artistName cannot be empty string in GetArtistInfo.DbPediaArtistInfo");
		}
		else {
			QueryEndPoint qep = new QueryEndPointImp();
			String queryStr = "DESCRIBE *  WHERE { " +
					"?artist foaf:name \""+ artistName + "\"@en. " +
					" }";
			qep.setQuery(prefix + queryStr);
			qep.setEndPoint(QueryEndPoint.DB_PEDIA);
			Model model = qep.describeStatement();
			LOGGER.debug("DBPedia search found " + model.size() + " statements" );
			FileOutputStream out = new FileOutputStream(new File("log/dbpediaout.ttl"));
			model.write(out, "TURTLE");
			return model;
		}				
	}

	public static void main(String[] args) throws FileNotFoundException{
		System.out.println("started query");
		Model test = ArtistInfo("Moby");
		System.out.println("ended query");
		Model model = ModelFactory.createDefaultModel();
		model.add(test);
		FileOutputStream out = new FileOutputStream(new File("log/artistinfo.ttl"));
		model.write(out, "TURTLE");
	}
}
