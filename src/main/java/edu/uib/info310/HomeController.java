package edu.uib.info310;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import edu.uib.info310.exception.ArtistNotFoundException;
import edu.uib.info310.exception.MasterNotFoundException;
import edu.uib.info310.model.Artist;
import edu.uib.info310.model.Record;
import edu.uib.info310.search.Searcher;

/**
 * Sample controller for going to the home page with a message
 */
@Controller
public class HomeController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
	private Searcher searcher;


	/**
	 * Selects the home page and populates the model with a message
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}


	@RequestMapping(value = "/search")
	@ResponseStatus(value = HttpStatus.OK)
	public ModelAndView search(@RequestParam String q){
		LOGGER.debug("Artist got search string: " + q);
		ModelAndView mav = new ModelAndView();
		mav.addObject("q", q);
		Artist artist = null;
		try {
			artist = searcher.searchArtist(q);
			mav.addObject("artist", artist);
		} catch (ArtistNotFoundException e) {
			LOGGER.debug("Didn't find an artist with the name " + q);

		}
		LOGGER.debug("Looking for records called: " + q);
		List<Record> records = searcher.searchRecords(q);
		LOGGER.debug("Found " + records.size() + " records" );
		if(records.isEmpty() && artist != null){
			mav.setViewName("artist");
		}else if(records.isEmpty()){
			mav.setViewName("notFound");
		}else if(records.size() == 1 && artist == null){
			Record record = records.get(0);
			mav = album(record.getName(), record.getArtist().get(0).getName());
		}
		else{
			mav.addObject("records", records);
			mav.setViewName("searchResults");
		}
		
		LOGGER.debug("Returning view: " + mav.getViewName());
		return mav;
	}


	@RequestMapping(value = "/artist")
	@ResponseStatus(value = HttpStatus.OK)
	public ModelAndView artist(@RequestParam String q){
		LOGGER.debug("Artist got search string: " + q);
		ModelAndView mav = new ModelAndView();
		mav.addObject("q", q);
		try {
			mav.addObject("artist", searcher.searchArtist(q));
			mav.setViewName("artist");
		} catch (ArtistNotFoundException e) {
			mav.setViewName("notFound");
		}

		return mav;
	}



	@RequestMapping(value = "/album")
	@ResponseStatus(value = HttpStatus.OK)
	public ModelAndView album(@RequestParam String q,String artist){
		LOGGER.debug("Album got search string: " + q);
		ModelAndView mav = new ModelAndView();
		mav.addObject("q", q);

		try {
			mav.addObject("record", searcher.searchRecord(q,artist));
			mav.setViewName("record");
		} catch (MasterNotFoundException e) {
			mav.setViewName("notFound");
		}

		return mav;
	}
}
