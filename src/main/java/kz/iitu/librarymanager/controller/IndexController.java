package kz.iitu.librarymanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	//This controller acts as a "default page"
	@GetMapping("/")
	public String list() {
		return "index"; //Redirects to index HTML page
	}
}
