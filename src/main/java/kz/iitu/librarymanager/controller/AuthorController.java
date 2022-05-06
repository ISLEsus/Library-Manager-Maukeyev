package kz.iitu.librarymanager.controller;

import java.util.List;

import kz.iitu.librarymanager.model.Author;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kz.iitu.librarymanager.service.AuthorService;

@Controller
public class AuthorController {

	//Initializing the Author Service for the Author Controller
	private final AuthorService authorService;
	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	//List all Authors
	@RequestMapping("/authors")
	public String findAllAuthors(Model model) {
		final List<Author> authors = authorService.findAllAuthors();
		model.addAttribute("authors", authors);
		return "list-authors"; //html
	}

	//Fetch a specific Author by its ID number
	@RequestMapping("/author/{id}")
	public String findAuthorById(@PathVariable("id") Long id, Model model) {
		final Author author = authorService.findAuthorById(id);
		model.addAttribute("author", author);
		return "list-author"; //html
	}

	//Add a new Author to the database
	@RequestMapping("/add-author")
	public String createAuthor(Author author, BindingResult result, Model model) {
		if (result.hasErrors()) {
			//Loop back on itself in case of an error.
			return "add-author";
		}
//		--->   proceed   --->
		authorService.createAuthor(author);
		model.addAttribute("author", authorService.findAllAuthors());
		//Throw to "/authors" in case of success; This will display all Authors from the database.
		return "redirect:/authors";
	}

	@RequestMapping("/update-author/{id}")
	public String updateAuthor(@PathVariable("id") Long id, Author author, BindingResult result, Model model) {
		if (result.hasErrors()) {
			author.setId(id);
			//Loop back on itself in case of an error.
			return "update-author";
		}
//		--->   proceed   --->
		authorService.updateAuthor(author);
		model.addAttribute("author", authorService.findAllAuthors());
		//Throw to "/authors" in case of success; This will display all Authors from the database.
		return "redirect:/authors";
	}

	//Remove an existing Author; Requires a specific ID for the reference.
	@RequestMapping("/remove-author/{id}")
	public String deleteAuthor(@PathVariable("id") Long id, Model model) {
		authorService.deleteAuthor(id);
		model.addAttribute("author", authorService.findAllAuthors());
		//Throw to "/authors" to display a fresh list of Authors from the database.
		return "redirect:/authors";
	}

}
