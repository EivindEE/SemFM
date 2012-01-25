package edu.uib.info310.search.builder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.hp.hpl.jena.rdf.model.Model;

import edu.uib.info310.search.builder.ontology.AbstractArtistDataSource;
import edu.uib.info310.search.builder.ontology.ArtistDataSource;
import edu.uib.info310.search.builder.ontology.BBCArtistDataSource;

@Component
public class ArtistDataSources {
	
	@Autowired
	@Qualifier("ITunesArtistDataSourceImpl")
	private ArtistDataSource itunes;

	@Autowired
	private BBCArtistDataSource bbc;

	@Autowired
	@Qualifier("DBPediaArtistDataSourceImpl")
	private ArtistDataSource dbp;	
	
	private List<ArtistDataSource> dataSources;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ArtistDataSources.class);

	public void init(){
		this.dataSources = Arrays.asList(new ArtistDataSource[]{itunes,bbc,dbp});
		LOGGER.debug("Running the query will gather information from these sources: " + dataSources);
	}

	public void getArtistModel(){
		List<Thread> threads = new LinkedList<Thread>();
		for(ArtistDataSource source : dataSources){
			threads.add(new Thread(source));
		}
		for(Thread thread :  threads){
			thread.start();
		}
		while(running(threads)){
			// Wait for threads to stop running
		}
	}


	private boolean running(List<Thread> threads) {
		for(Thread thread : threads){
			if(thread.isAlive()){
				return true;
			}
		}
		return false;
	}

	public void setArtistName(String artistName) {
		for(ArtistDataSource source : dataSources){
			source.setArtistName(artistName);
		}
	}

	public void setArtistUri(String artistUri) {
		for(ArtistDataSource source : dataSources){
			source.setArtistUri(artistUri);
		}
	}

	public void setModel(Model model) {
		for(ArtistDataSource source : dataSources){
			source.setModel(model);
		}
	}

}
