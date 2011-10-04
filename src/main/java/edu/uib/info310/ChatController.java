package edu.uib.info310;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ChatController {
	private static final Logger logger = LoggerFactory
			.getLogger(ChatController.class);
	
	@RequestMapping(value = "chat", method = RequestMethod.GET) // Value = BASEDIR/chat
	public String anything(Model model) {
		logger.debug("I guess this should initiate the chatroom");

		model.addAttribute("chatroom", "This is where we keep the chatroom!"); // Name of the Key and Value of the key
		
		return "chat"; // The name of the .jsp file
	}
}
