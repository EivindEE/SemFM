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
import edu.uib.info310.search.builder.ontology.AbstractRecordDataSource;
import edu.uib.info310.search.builder.ontology.RecordDataSource;

@Component
public class RecordDataSources {
	private List<RecordDataSource> dataSources;
	
	@Autowired
	@Qualifier("ITunesRecordDataSourceImpl")
	RecordDataSource itunes;
	
	@Autowired
	@Qualifier("discogsRecordDataSourceImpl")
	RecordDataSource discogs;

	private static final Logger LOGGER = LoggerFactory.getLogger(ArtistDataSources.class);

	public void init(){
		this.dataSources = Arrays.asList(new RecordDataSource[]{itunes, discogs});
		LOGGER.debug("Running the query will gather information from these sources: " + dataSources);
	}
	
	public void buildRecordModel(){
		List<Thread> threads = new LinkedList<Thread>();
		for(RecordDataSource source : dataSources){
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

	public void setRecord(String record){
		for(RecordDataSource ds : dataSources){
			ds.setAlbum(record);
		}
	}
	
	public void setModel(Model model){
		for(RecordDataSource ds : dataSources){
			ds.setModel(model);
		}
	}
	
	public void setAlbumUri(String albumUri){
		for(RecordDataSource ds : dataSources){
			ds.setAlbumUri(albumUri);
		}
	}
	
	public void setArtistName(String artistName){
		for(RecordDataSource ds : dataSources){
			ds.setArtistName(artistName);
		}
	}
	
	
}
