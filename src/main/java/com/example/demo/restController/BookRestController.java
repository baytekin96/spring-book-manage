package com.example.demo.restController;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Book;
import com.example.demo.restResponse.BaseResponse;
import com.example.demo.service.BookService;

@Controller
@ResponseBody
@RequestMapping("/api/books")
public class BookRestController {
	
	@Autowired
	private BookService bookService;
	
	@GetMapping
	public ResponseEntity<?> getBooks() {
		BaseResponse response = new BaseResponse();
		List<Book> bookList = bookService.getBooks();
		response.setStatu(true);
		response.setCode(200);
		response.getData().put("books", bookList);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/book")
	public ResponseEntity<?> insertBook(@Valid @RequestBody Book b, Errors errors) {
		BaseResponse response = new BaseResponse();
		if (errors.hasErrors()) {
			response.setCode(203);
			response.setMessage(errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining("</br>")));
			response.setStatu(false);
			return ResponseEntity.ok(response);
		}
		Boolean status = bookService.insertBook(b);
		if(status) {
			response.setCode(200);
			response.setMessage("Successfully added");
			response.setStatu(true);
		}else {
			response.setCode(200);
			response.setMessage("Something went wrond");
			response.setStatu(false);
		}
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/book/{id}")
	public ResponseEntity<?> getBook(@Valid @PathVariable int id) {
		BaseResponse response = new BaseResponse();
		Book book = this.bookService.getBookById(id);
		if(book!=null) {
			response.setStatu(true);
			response.setCode(200);
			response.getData().put("book", book);
			return ResponseEntity.ok(response);			
		}
		response.setStatu(false);
		response.setCode(201);
		response.setMessage("Book not found");
		return ResponseEntity.ok(response);	
	}
	
	@PutMapping("/book/{id}")
	public ResponseEntity<?> updateBook(@Valid @PathVariable int id, @Valid @RequestBody Book b, Errors errors) {
		BaseResponse response = new BaseResponse();
		if (errors.hasErrors()) {
			response.setCode(203);
			response.setMessage(errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining("</br>")));
			response.setStatu(false);
			return ResponseEntity.ok(response);
		}
		Boolean status = bookService.updateBook(id, b);
		if(status) {
			response.setCode(200);
			response.setMessage("Successfully updated");
			response.setStatu(true);
		}else {
			response.setCode(200);
			response.setMessage("Something went wrond");
			response.setStatu(false);
		}
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/book/{id}")
	public ResponseEntity<?> deleteBook(@Valid @PathVariable int id) {
		BaseResponse response = new BaseResponse();
		Boolean status = bookService.deleteBook(id);
		if(status) {
			response.setCode(200);
			response.setMessage("Successfully deleted");
			response.setStatu(true);
		}else {
			response.setCode(200);
			response.setMessage("Something went wrond");
			response.setStatu(false);
		}
		return ResponseEntity.ok(response);
	}

}
