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
			"PREFIX owl: <http://www.w3.org/2002/07/owl#> ";

	/**
	 * Returns a model with data from BBC_MUSIC SPARQL search
	 * @returns a Model
	 */
	public static Model BBCMusic(String artistName, String artistUri) throws Exception{
		if(artistName.isEmpty()){
			throw new NullPointerException("artistName cannot be empty string in GetArtistInfo.DbPediaArtistInfo");
		}
		else {
			QueryEndPoint qep = new QueryEndPointImp();
			String artist = "<" + artistUri + ">";
			String constructStr = "CONSTRUCT { "+artist+" 	 " +
					"mo:fanpage ?fanpage ; " +
					"mo:imdb ?imdb ; " +
					"mo:myspace ?myspace ; " +
					"mo:homepage ?homepage ; " +
					"rdfs:comment ?comment ; " +
					"mo:image ?image ;" +
					"owl:sameAs ?artist" +
					"} " ;
			String whereStr =" WHERE {?artist foaf:name \"" + artistName + "\" " + "." +
					"OPTIONAL{?artist mo:fanpage ?fanpage}" +
					"OPTIONAL{?artist mo:imdb ?imdb}" +
					"OPTIONAL{?artist mo:myspace ?myspace}" +
					"OPTIONAL{?artist foaf:homepage ?homepage}" +
					"OPTIONAL{?artist rdfs:comment ?comment. FILTER (lang(?comment) = '')}" +
					"OPTIONAL{?artist mo:image ?image}}";

			LOGGER.debug("Sending Query: " + prefix + constructStr + whereStr );
			qep.setQuery(prefix + constructStr + whereStr);
			qep.setEndPoint(QueryEndPoint.BBC_MUSIC);
			Model model = qep.describeStatement();
			LOGGER.debug("BBC with URI search found " + model.size() + " statements" );
			FileOutputStream out = new FileOutputStream(new File("log/bbcout.ttl"));
			model.write(out, "TURTLE");
			return model;
		}
	}

	/**
	 * Returns a model with data from DB_PEDIA SPARQL search
	 * @returns a Model
	 */
	public static Model DBPedia(String artistName, String artistUri) throws Exception{
		if(artistName.isEmpty()){
			throw new NullPointerException("artistName cannot be empty string in GetArtistInfo.DbPediaArtistInfo");
		}
		else {
			QueryEndPoint qep = new QueryEndPointImp();

			//			Fanger ikke opp Michael Jacksom siden han er oppført med foaf:name Michael Joseph Jackson
			String artist = "<" + artistUri + ">";
			String constructStr = "CONSTRUCT { "+artist +" " +
					"rdfs:comment ?comment ; " +
					"mo:biography ?bio ; " +
					"dbont:birthname ?birthname ; " +
					"dbont:hometown ?hometown ; " +
					"mo:origin ?origin ; " +
					"mo:activity_start ?start; " +
					"mo:activity_end ?end ;" +
					"dbont:birthDate ?birth ;" +
					"dbont:deathDate ?death ;" +
					"mo:wikipedia ?wikipedia ;" +
					"owl:sameAs ?artist" ;


			String whereStr ="} WHERE {?artist foaf:name \"" + artistName + "\"@en." +
					"{?artist rdf:type dbont:Artist.} UNION {?artist rdf:type dbont:Band.}. " +
					"OPTIONAL{?artist dbpedia:shortDescription ?comment} . " +
					"OPTIONAL{?artist dbont:abstract ?bio . FILTER(lang(?bio) = 'en')} . " +
					"OPTIONAL{?artist dbont:birthname ?birthname} ." +
					"OPTIONAL{?artist dbont:hometown ?hometown} ." +
					"OPTIONAL{?artist dbpedia:origin ?origin} ." +
					"OPTIONAL{?artist dbont:activeYearsEndYear ?end} ." +
					"OPTIONAL{?artist dbont:activeYearsStartYear ?start} ." +
					"OPTIONAL{?artist dbont:birthDate ?birth} ." +
					"OPTIONAL{?artist dbont:deathDate ?death} ." +
					"OPTIONAL{?artist foaf:page ?wikipedia}" +
					"}";
			qep.setQuery(prefix + constructStr + whereStr);
			qep.setEndPoint(QueryEndPoint.DB_PEDIA);
			Model model = qep.describeStatement();
			LOGGER.debug("DBPedia search found " + model.size() + " statements" );
			return model;
		}				
	}

	public static void main(String[] args) throws Exception{
		LOGGER.debug("started query");
		Model test = ModelFactory.createDefaultModel();
		//test.add(BBCMusic("Moby"));
		//test.add(DBPedia("Moby"));
		LOGGER.debug("ended query");

		FileOutputStream out = new FileOutputStream(new File("log/getartistinfoout.ttl"));
		test.write(out, "TURTLE");
	}
}
