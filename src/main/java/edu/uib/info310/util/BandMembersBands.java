package edu.uib.info310.util;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class BandMembersBands {
	public static String prefix = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
			"PREFIX dc: <http://purl.org/dc/elements/1.1/>" +
			"PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
			"PREFIX mo: <http://purl.org/ontology/mo/>" +
			"PREFIX rel: <http://purl.org/vocab/relationship/>" +
			"PREFIX rev: <http://purl.org/stuff/rev#>";


	public static ResultSet MembersInBand(String artistName){
		String queryString;

	 		if(artistName.isEmpty()){
	 			System.out.println("No artist selected");
	 			return null;
	 		}
	 	else {

	 			queryString = prefix + "SELECT ?artist ?name WHERE { ?group foaf:name '"+ artistName + "'. " +
		 				"?group mo:member ?artist. ?artist foaf:name ?name.}";
	 		
	 		//System.out.println(queryString);
	 	      Query query = QueryFactory.create(queryString); 
	 	      QueryExecution qexec = QueryExecutionFactory.sparqlService("http://api.talis.com/stores/bbc-backstage/services/sparql", query); 
	 	     try {
	
	 	      ResultSet results = qexec.execSelect(); 
	 	      return results;
  
	 	       }
	 	     
	 	     finally { qexec.close() ;
	 	     	
	 	     	}
	 	     }			
	 	}
	
	public static ResultSet MemberOfBand(String artistName){
		String queryString;

	 		if(artistName.isEmpty()){
	 			System.out.println("No artist selected");
	 			return null;
	 		}
	 	else {

		 		queryString = prefix + "SELECT ?group ?name WHERE { ?artist foaf:name '"+ artistName + "'. " +
		 				"?artist mo:member_of ?group. ?group foaf:name ?name.}";

	 		//System.out.println(queryString);
	 	      Query query = QueryFactory.create(queryString); 
	 	      QueryExecution qexec = QueryExecutionFactory.sparqlService("http://api.talis.com/stores/bbc-backstage/services/sparql", query); 
	 	     try {
	
	 	      ResultSet results = qexec.execSelect(); 
	 	      return results;
  
	 	       }
	 	     
	 	     finally { qexec.close() ;
	 	     	
	 	     	}
	 	     }			
	 	}
	 		
	 	
		public static void main(String[] args){
			ResultSet test = MembersInBand("Metallica");
 	        for ( ; test.hasNext() ; )
 	       {

 	        QuerySolution soln = test.nextSolution() ;
 	        RDFNode x = soln.get("artist") ;  
 	        RDFNode y = soln.get("name");
 	        System.out.println(y + " have been in following bands:");
 	        ResultSet test2 = MemberOfBand(y.toString());

 	       
 	        for ( ; test2.hasNext(); )
 	        	{
  	        	QuerySolution soln2 = test2.nextSolution() ;
  	        	RDFNode x2 = soln2.get("group") ;  
  	        	RDFNode y2 = soln2.get("name") ; 
  	        	System.out.println(y2 + "!");
 	         	}
 	         }
 	          			
		}

}
