package edu.uib.info310.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

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
 					"?artist foaf:name '"+ artistName + "'. " +
	 				//"?artist owl:sameAs ?uri. " +
 					//"?artist mo:image ?image. " +
	 				//"?artist mo:fanpage ?fanpage. " + 
 					//" ?artist dbpedia:url ?homepage." +
 					//" ?artist dbpedia:lifetimeProperty ?lifetimeProperty." +
 					//" ?artist dbpedia:yearsActive ?active." +
	 				//" ?artist dbpedia:abstract ?description. Filter (lang(?description) = 'en')" +
	 				" }";
	 		qep.setQuery(prefix + query);
	 		qep.setEndPoint(QueryEndPoint.BBC_MUSIC);
	 		
	 		Model rm = qep.describeStatement();
	 		//ResultSetFormatter.out(rs);
	 		
	 		return rm;
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
