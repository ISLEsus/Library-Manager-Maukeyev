package kz.iitu.librarymanager.controller;

import java.util.List;

import kz.iitu.librarymanager.model.Book;
import kz.iitu.librarymanager.service.CategoryService;
import kz.iitu.librarymanager.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kz.iitu.librarymanager.service.AuthorService;
import kz.iitu.librarymanager.service.BookService;

@Controller
public class BookController {

	//Initializing all Services
	private final BookService bookService;
	private final AuthorService authorService;
	private final CategoryService categoryService;
	private final PublisherService publisherService;

	//Constructor
	@Autowired
	public BookController(BookService bookService, AuthorService authorService, CategoryService categoryService,
			PublisherService publisherService) {
		this.bookService = bookService;
		this.authorService = authorService;
		this.categoryService = categoryService;
		this.publisherService = publisherService;
	}

	//List all Books from the database
	@RequestMapping("/books")
	public String findAllBooks(Model model) {
		final List<Book> books = bookService.findAllBooks();
		model.addAttribute("books", books);
		return "list-books"; //html
	}

	//Search for through Books using a specific Keyword
	@RequestMapping("/searchBook")
	public String searchBook(@Param("keyword") String keyword, Model model) {
		final List<Book> books = bookService.searchBooks(keyword);
		model.addAttribute("books", books);
		model.addAttribute("keyword", keyword);
		return "list-books";
	}

	//Fetch a specific Book using its ID as a reference.
	@RequestMapping("/book/{id}")
	public String findBookById(@PathVariable("id") Long id, Model model) {
		final Book book = bookService.findBookById(id);
		model.addAttribute("book", book);
		return "list-book";
	}

	//Add a new Book to the database
	@RequestMapping("/add-book")
	public String createBook(Book book, BindingResult result, Model model) {
		if (result.hasErrors()) {
			//Loop on itself in case of an error
			return "add-book";
		}
//		--> proceed -->
		bookService.createBook(book);
		model.addAttribute("book", bookService.findAllBooks());
		//Display a fresh list of Books in case of success.
		return "redirect:/books";
	}

	//Change an already existing book; Requires its ID as a reference
	@RequestMapping("/update-book/{id}")
	public String updateBook(@PathVariable("id") Long id, Book book, BindingResult result, Model model) {
		if (result.hasErrors()) {
			book.setId(id);
			//Loop on itself in case of error
			return "update-book";
		}
		bookService.updateBook(book);
		model.addAttribute("book", bookService.findAllBooks());
		//Display a fresh new list of Books in case of success
		return "redirect:/books";
	}

	//Delete a specific book from the database
	@RequestMapping("/remove-book/{id}")
	public String deleteBook(@PathVariable("id") Long id, Model model) {
		bookService.deleteBook(id);
		model.addAttribute("book", bookService.findAllBooks());
		//Display a fresh new list of Books after deletion
		return "redirect:/books";
	}

}
