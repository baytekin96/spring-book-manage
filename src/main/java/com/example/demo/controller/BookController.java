package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;
	@Autowired
	private AuthorService authorService;

//	/books -> open books.html - lists all books
	@RequestMapping(value = {"","/books","/admin/books"})
	public ModelAndView Books() {
		ModelAndView model = new ModelAndView();
		model.setViewName("books");
		List<Book> books = this.bookService.getBooks();
		model.addObject("books", books);
		return model;
	}
// /books/add -> open add-book.html - lists authors in select box - create book object
	@RequestMapping(value = "/admin/books/add", method = RequestMethod.GET)
	public ModelAndView BookAddGet() {
		ModelAndView model = new ModelAndView();
		model.setViewName("add-book");
		List<Author> authors = this.authorService.getAuthors();
		model.addObject("book", new Book());
		model.addObject("authors",authors);
		return model;
	}
//	/books/add -> open add-book.html - lists authors in select box - fills book class with form values - prints errors
	@RequestMapping(value = "/admin/books/add", method = RequestMethod.POST)
	public ModelAndView BookAddPost(@Valid @ModelAttribute Book book, BindingResult result) {
		ModelAndView model = new ModelAndView();
		model.setViewName("add-book");
		List<Author> authors = this.authorService.getAuthors();
		model.addObject("authors",authors);
		if (!result.hasErrors()) {
			this.bookService.insertBook(book);
			model.setViewName("redirect:" + "/books");
		}
		return model;
	}
//	/books/delete -> delete book and go /books page
	@RequestMapping(value = "/admin/books/delete", method = RequestMethod.GET)
	public ModelAndView BookDeletePost(@RequestParam("id") int id) {
		ModelAndView model = new ModelAndView();
		bookService.deleteBook(id);
		model.setViewName("redirect:" + "/books");
		return model;
	}
//	/books/edit -> edit'lenecek objenin id'sini al, bu id'nin bulunduğu nesneyi döndür
	@RequestMapping(value = "/admin/books/edit", method = RequestMethod.GET)
	public ModelAndView BookEditGet(@RequestParam("id") int id) {
		ModelAndView model = new ModelAndView();
		List<Book> books = this.bookService.getBooks();
		List<Author> authors = this.authorService.getAuthors();
		model.addObject("authors",authors);
		for(Book b:books) {
			if(id == b.getBook_id()) {
				model.addObject("book",b);
				model.setViewName("edit-book");
			}
		}
		return model;
	}
//	/books/edit -> update et ve /books sayfasına dön
	@RequestMapping(value = "/admin/books/edit", method = RequestMethod.POST)
	public ModelAndView BookEditPost(@RequestParam("id") int id, @Valid @ModelAttribute Book book, BindingResult result) {
		ModelAndView model = new ModelAndView();
		this.bookService.updateBook(id, book);
		model.setViewName("redirect:" + "/books");
		
		return model;
	}
	


}
