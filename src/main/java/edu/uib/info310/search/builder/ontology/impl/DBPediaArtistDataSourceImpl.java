package edu.uib.info310.search.builder.ontology.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.rdf.model.Model;

import edu.uib.info310.search.builder.ontology.AbstractArtistDataSource;
import edu.uib.info310.search.builder.ontology.DBpediaArtistDataSource;
import edu.uib.info310.sparql.QueryEndPoint;

@Component
public class DBPediaArtistDataSourceImpl extends AbstractArtistDataSource implements DBpediaArtistDataSource {
	@Autowired
	protected QueryEndPoint qep;
	private static final String prefix = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
			"PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
			"PREFIX mo: <http://purl.org/ontology/mo/>" +
			"PREFIX dbpedia: <http://dbpedia.org/property/>" +
			"PREFIX dbont: <http://dbpedia.org/ontology/>" +
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
			"PREFIX rc: <http://umbel.org/umbel/rc/>" +
			"PREFIX owl: <http://www.w3.org/2002/07/owl#> ";
	private static final Logger LOGGER = LoggerFactory.getLogger(DBPediaArtistDataSourceImpl.class);
	
	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.ontology.impl.DBPediaOntology#getArtistModel(java.lang.String, java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see edu.uib.info310.search.builder.ontology.impl.DBpediaArtistDataSource#getArtistModel(java.lang.String, java.lang.String)
	 */
	@Override
	public Model getArtistModel(String artistName, String artistUri){

		String artist = "<" + artistUri + ">";
		String constructStr = makeConstructString(artist);
		String whereStr = makeWhereString(artistName);
		
		qep.setQuery(prefix + constructStr + whereStr);
		qep.setEndPoint(QueryEndPoint.DB_PEDIA);
		
		Model model = qep.constructStatement();
		
		boolean isUpperCase = Character.isUpperCase(artistName.charAt(0));

		if(model.isEmpty() && isUpperCase == false){
			artistName = artistName.substring(0,1).toUpperCase() + artistName.substring(1);
			model = this.getArtistModel(artistName, artistUri);
		}

		String translateConstructStr = makeConstructString(artist);

		String translateWhere = makeTranslate();
		QueryExecution exec = QueryExecutionFactory.create(prefix + translateConstructStr + translateWhere, model);
		Model translatedModel = exec.execConstruct();

		LOGGER.debug("DBPedia search found " + translatedModel.size() + " statements" );
		return translatedModel;

	}
	private String makeTranslate() {
		
		return "} WHERE {?artist rdf:type mo:MusicArtist . " +
				"OPTIONAL{?artist rdfs:comment ?comment} . " +
				"OPTIONAL{?artist mo:biography ?bio } ." + 
				"OPTIONAL{?artist dbont:birthname ?birthname} ." +
				"OPTIONAL{?artist dbont:hometown ?hometown} ." +
				"OPTIONAL{?artist mo:origin ?origin} ." +
				"OPTIONAL{?artist mo:activity_start ?start} ." +
				"OPTIONAL{?artist mo:activity_end ?end} ." +
				"OPTIONAL{?artist dbont:birthDate ?birth} ." +
				"OPTIONAL{?artist dbont:deathDate ?death} ." +
				"OPTIONAL{?artist mo:wikipedia ?wikipedia}. "+
				"OPTIONAL{?artist mo:image ?image}. " +
				"OPTIONAL {{{?artist dbpedia:currentMembers ?currentMembers. ?currentMembers rdfs:label ?name3. FILTER(lang(?name3) = 'en') } UNION {?artist dbont:bandMember ?currentMember. ?currentMember rdfs:label ?name1. FILTER(lang(?name1) = 'en')}} UNION"+
				"{ {?artist dbpedia:pastMembers ?pastMembers. ?pastMembers rdfs:label ?name4. FILTER(lang(?name4) = 'en')} UNION {?artist dbont:formerBandMember ?pastMember. ?pastMember rdfs:label ?name2. FILTER(lang(?name2) = 'en')}}}" +
				"OPTIONAL {?artist rdfs:label ?name}}" +
				"";
	}
	private String makeWhereString(String artistName) {
		return "} WHERE { {?artist foaf:name \"" + artistName + "\"@en.} UNION {?artist rdfs:label \"" + artistName + "\"@it} . " +
				"{?artist rdf:type dbont:Artist.} UNION {?artist rdf:type dbont:Band.}  UNION {?artist rdf:type rc:Artist } UNION {?artist rdf:type rc:Band_MusicGroup } UNION {?artist rdf:type ?o. ?o rdfs:subClassOf ?o2. ?o2 rdfs:subClassOf ?o3. ?o3 rdfs:label 'musician'@en }." + //UNION {?s dbont:musicalArtist ?artist. ?s a dbont:Single } 
				"OPTIONAL{?artist dbpedia:shortDescription ?comment} . " +
				"OPTIONAL{?artist dbont:abstract ?bio . FILTER(lang(?bio) = 'en')} . " +
				"OPTIONAL{?artist dbont:birthname ?birthname} ." +
				"OPTIONAL{?artist dbont:hometown ?hometown} ." +
				"OPTIONAL{?artist dbpedia:origin ?origin} ." +
				"OPTIONAL{?artist dbont:activeYearsEndYear ?end} ." +
				"OPTIONAL{?artist dbont:activeYearsStartYear ?start} ." +
				"OPTIONAL{?artist dbont:birthDate ?birth} ." +
				"OPTIONAL{?artist dbont:deathDate ?death} ." +
				"OPTIONAL{?artist foaf:page ?wikipedia}. " +
				"OPTIONAL{?artist foaf:depiction ?image}. " +
				"OPTIONAL {{{?currentMembers dbpedia:currentMembers ?artist. ?currentMembers rdfs:label ?name3. FILTER(lang(?name3) = 'en') } UNION {?artist dbont:bandMember ?currentMember. ?currentMember rdfs:label ?name1. FILTER(lang(?name1) = 'en')}} UNION"+
				"{ {?pastMembers dbpedia:pastMembers ?artist. ?pastMembers rdfs:label ?name4. FILTER(lang(?name4) = 'en')} UNION {?artist dbont:formerBandMember ?pastMember. ?pastMember rdfs:label ?name2. FILTER(lang(?name2) = 'en')}}}" +
				"} limit 100000";
	}
	private String makeConstructString(String artist) {
		return "CONSTRUCT { "+artist +" " +
				"rdfs:comment ?comment ; " +
				"rdf:type mo:MusicArtist ;" +
				"mo:biography ?bio ; " +
				"dbont:birthname ?birthname ; " +
				"dbont:hometown ?hometown ; " +
				"mo:origin ?origin ; " +
				"mo:activity_start ?start; " +
				"mo:activity_end ?end ;" +	
				"dbont:birthDate ?birth ;" +
				"dbont:deathDate ?death ;" +
				"mo:wikipedia ?wikipedia ; " +
				"mo:image ?image ;" +
				"owl:sameAs ?artist;" +
				"dbont:bandMember ?currentMember;" +
				"dbont:formerBandMember ?pastMember;" +
				"dbpedia:currentMembers ?currentMembers;" +
				"dbpedia:pastMembers ?pastMembers." +
				"?currentMember rdfs:label ?name1." +
				"?pastMember rdfs:label ?name2." +
				"?currentMembers rdfs:label ?name3." +
				"?pastMembers rdfs:label ?name4.";
	}
}
