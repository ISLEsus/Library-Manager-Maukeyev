package kz.iitu.librarymanager.controller;

import java.util.List;

import kz.iitu.librarymanager.model.Publisher;
import kz.iitu.librarymanager.service.PublisherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PublisherController {

	//Initializing the Publisher Service for the Publisher Controller
	private final PublisherService publisherService;
	public PublisherController(PublisherService publisherService) {
		this.publisherService = publisherService;
	}

	//List all Publishers
	@RequestMapping("/publishers")
	public String findAllPublishers(Model model) {
		final List<Publisher> publishers = publisherService.findAllPublishers();
		model.addAttribute("publishers", publishers);
		return "list-publishers"; //html
	}

	//Fetch a specific Publisher by its ID number
	@RequestMapping("/publisher/{id}")
	public String findPublisherById(@PathVariable("id") Long id, Model model) {
		final Publisher publisher = publisherService.findPublisherById(id);
		model.addAttribute("publisher", publisher);
		return "list-publisher"; //html
	}

	//Add a new Publisher to the database
	@RequestMapping("/add-publisher")
	public String createPublisher(Publisher publisher, BindingResult result, Model model) {
		if (result.hasErrors()) {
			//Loop back on itself in case of an error.
			return "add-publisher";
		}
//		--->   proceed   --->
		publisherService.createPublisher(publisher);
		model.addAttribute("publisher", publisherService.findAllPublishers());
		//Throw to "/publishers" in case of success; This will display all Publishers from the database.
		return "redirect:/publishers";
	}

	//Change an existing Publisher; Requires a specific ID for the reference.
	@RequestMapping("/update-publisher/{id}")
	public String updatePublisher(@PathVariable("id") Long id, Publisher publisher, BindingResult result, Model model) {
		if (result.hasErrors()) {
			publisher.setId(id);
			//Loop back on itself in case of an error.
			return "update-publishers";
		}
//		--->   proceed   --->
		publisherService.updatePublisher(publisher);
		model.addAttribute("publisher", publisherService.findAllPublishers());
		//Throw to "/publisher" in case of success; This will display all Publishers from the database.
		return "redirect:/publishers";
	}

	//Remove an existing Category; Requires a specific ID for the reference.
	@RequestMapping("/remove-publisher/{id}")
	public String deletePublisher(@PathVariable("id") Long id, Model model) {
		publisherService.deletePublisher(id);
		model.addAttribute("publisher", publisherService.findAllPublishers());
		//Throw to "/publishers" to display a fresh list of Publisher from the database.
		return "redirect:/publishers";
	}

}
