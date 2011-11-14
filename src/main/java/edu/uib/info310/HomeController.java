package edu.uib.info310;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import edu.uib.info310.search.ArtistNotFoundException;
import edu.uib.info310.search.Searcher;

/**
 * Sample controller for going to the home page with a message
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
	private Searcher searcher;


	/**
	 * Selects the home page and populates the model with a message
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		String helloWrold = null;
		try {
			helloWrold = GetUrl.getContent("http://www.tastekid.com/ask/ws?q=rihanna"); // RIHANNA FUCK YEAH!
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		model.addAttribute("controllerMessage",
				helloWrold);
		return "home";
	}


	@RequestMapping(value = "/search")
	@ResponseStatus(value = HttpStatus.OK)
	public ModelAndView search(@RequestParam String q){
		logger.debug("Artist got search string: " + q);
		ModelAndView mav = new ModelAndView();
		mav.addObject("q", q);
		try {
			mav.addObject("artist", searcher.searchArtist(q));
			mav.setViewName("artist");
		} catch (ArtistNotFoundException e) {
			
			mav.addObject("records", searcher.searchRecords(q));
			mav.setViewName("searchResults");
		}

		return mav;
	}
	
	
	@RequestMapping(value = "/artist")
	@ResponseStatus(value = HttpStatus.OK)
	public ModelAndView artist(@RequestParam String q){
		logger.debug("Artist got search string: " + q);
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
	public ModelAndView album(@RequestParam String q){
		logger.debug("Album got search string: " + q);
		ModelAndView mav = new ModelAndView();
		mav.addObject("q", q);
		mav.addObject("record", searcher.searchRecord(q));
		mav.setViewName("record");
		return mav;
	}
}
