package edu.uib.info310.util;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import edu.uib.info310.vocabulary.MO;

public class Standard {
	public static String prefix = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
			"PREFIX dc: <http://purl.org/dc/elements/1.1/>" +
			"PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
			"PREFIX mo: <http://purl.org/ontology/mo/>" +
			"PREFIX rel: <http://purl.org/vocab/relationship/>" +
			"PREFIX rev: <http://purl.org/stuff/rev#>";
	
	public static Model InfoFraSide(String artistURI){
		String queryString;

	 		if(artistURI.isEmpty()){
	 			System.out.println("No artist selected");
	 			return null;
	 		}
	 	else {

	 			queryString = prefix + "DESCRIBE * WHERE { ?group rdf:about '"+ artistURI + "'. }";
	 		
	 		//System.out.println(queryString);
	 	      Query query = QueryFactory.create(queryString); 
	 	      QueryExecution qexec = QueryExecutionFactory.sparqlService("http://api.talis.com/stores/bbc-backstage/services/sparql", query); 
	 	     try {
	
	 	      Model results = qexec.execDescribe(); 
	 	      return results;
  
	 	       }
	 	     
	 	     finally { qexec.close() ;
	 	     	
	 	     	}
	 	     }			
	 	}

	/**
	 * @param args
	 * @return 
	 */
	public String getBio(String artistURI){
		Model test = InfoFraSide(artistURI);
		 String bio = null;
		
		Resource artist = test.getResource(artistURI); 
		
		StmtIterator itr = artist.listProperties(MO.bio);
		while(itr.hasNext()){
		 Statement s = itr.nextStatement();
		 if (s.getLanguage().equals("en"))
			bio = (s.getObject().toString());
		 else bio = "no string";
		} 

		return bio;
	}
	
	public static void main(String []args){
		Standard s = new Standard();
		
	System.out.println(s.getBio("http://dbpedia.org/resource/Metallica"));
	

	}
}
