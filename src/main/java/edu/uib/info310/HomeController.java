package edu.uib.info310;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Sample controller for going to the home page with a message
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	/**
	 * Selects the home page and populates the model with a message
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		String helloWrold = null;
		try {
			helloWrold = GetUrl.getContent("http://www.tastekid.com/ask/ws?q=rihanna"); // RIHANNA FUCK YEAH!
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		logger.debug("Debug logging is enabled");
		logger.info("Info logging is enabled");
		logger.warn("Warning logging is enabled");
		logger.error("Error logging is enabled");
		model.addAttribute("controllerMessage",
				helloWrold);
		return "home";
	}
	

}
