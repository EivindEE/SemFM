package edu.uib.info310.util;

import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import edu.uib.info310.sparql.QueryEndPoint;
import edu.uib.info310.sparql.QueryEndPointImp;

/**
 * Class for getting artist information from BBC and DBPedia
 * @author torsteinthune
 */
public abstract class GetArtistInfo implements QueryEndPoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetArtistInfo.class);
	public static String prefix = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
			"PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
			"PREFIX mo: <http://purl.org/ontology/mo/>" +
			"PREFIX dbpedia: <http://dbpedia.org/property/>" +
			"PREFIX dbont: <http://dbpedia.org/ontology/>" +
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
			"PREFIX owl: <http://www.w3.org/2002/07/owl#>";

	/**
	 * Returns a model with data from BBC_MUSIC SPARQL search
	 * @returns a Model
	 */
	public static Model BBCMusic(String artistName) throws Exception{
		if(artistName.isEmpty()){
			throw new NullPointerException("artistName cannot be empty string in GetArtistInfo.DbPediaArtistInfo");
		}
		else {
			QueryEndPoint qep = new QueryEndPointImp();
			
			String queryStr = "DESCRIBE ?artist WHERE { " +
					"?artist foaf:name \""+ artistName + "\". " +
					" }";
			qep.setQuery(prefix + queryStr);
			qep.setEndPoint(QueryEndPoint.BBC_MUSIC);
			Model model = qep.describeStatement();
			LOGGER.debug("BBC search found " + model.size() + " statements" );
//			FileOutputStream out = new FileOutputStream(new File("log/bbcout.ttl"));
//			model.write(out, "TURTLE");
			return model;
		}
	}
	
	/**
	 * Returns a model with data from DB_PEDIA SPARQL search
	 * @returns a Model
	 */
	public static Model DBPedia(String artistName) throws Exception{
		if(artistName.isEmpty()){
			throw new NullPointerException("artistName cannot be empty string in GetArtistInfo.DbPediaArtistInfo");
		}
		else {
			QueryEndPoint qep = new QueryEndPointImp();
			
//			Fanger ikke opp Michael Jacksom siden han er oppført med foaf:name Michael Joseph Jackson
			
			String queryStr = "DESCRIBE ?artist WHERE { " +
					"?artist foaf:name \""+ artistName + "\"@en. " +
					" }";
			qep.setQuery(prefix + queryStr);
			qep.setEndPoint(QueryEndPoint.DB_PEDIA);
			Model model = qep.describeStatement();
			LOGGER.debug("DBPedia search found " + model.size() + " statements" );
//			FileOutputStream out = new FileOutputStream(new File("log/dbpediaout.ttl"));
//			model.write(out, "TURTLE");
			return model;
		}				
	}

	public static void main(String[] args) throws Exception{
		LOGGER.debug("started query");
		Model test = ModelFactory.createDefaultModel();
		test.add(BBCMusic("Moby"));
		test.add(DBPedia("Moby"));
		LOGGER.debug("ended query");

		FileOutputStream out = new FileOutputStream(new File("log/getartistinfoout.ttl"));
		test.write(out, "TURTLE");
	}
}
