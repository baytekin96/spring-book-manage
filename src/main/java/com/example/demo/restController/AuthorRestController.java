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

import com.example.demo.model.Author;
import com.example.demo.restResponse.BaseResponse;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;

@Controller
@ResponseBody
@RequestMapping("/api/authors")
public class AuthorRestController {
	
	@Autowired
	private AuthorService authorService;
	@Autowired
	private BookService bookService;

	@GetMapping
	public ResponseEntity<?> getAuthors() {
		BaseResponse response = new BaseResponse();
		List<Author> authorList = authorService.getAuthors();
		response.setStatu(true);
		response.setCode(200);
		response.getData().put("authors", authorList);
		return ResponseEntity.ok(response);
	}
	@GetMapping("/author/{id}")
	public ResponseEntity<?> getAuthor(@Valid @PathVariable int id) {
		BaseResponse response = new BaseResponse();
		Author author = this.authorService.getAuthorById(id);
		if(author!=null) {
			response.setStatu(true);
			response.setCode(200);
			response.getData().put("author", author);
			return ResponseEntity.ok(response);			
		}
		response.setStatu(false);
		response.setCode(201);
		response.setMessage("Book not found");
		return ResponseEntity.ok(response);	
	}
	
	@PostMapping("/author")
	public ResponseEntity<?> insertAuthor(@Valid @RequestBody Author a, Errors errors) {
		BaseResponse response = new BaseResponse();
		if (errors.hasErrors()) {
			response.setCode(203);
			response.setMessage(errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining("</br>")));
			response.setStatu(false);
			return ResponseEntity.ok(response);
		}
		Boolean status = authorService.insertAuthor(a);
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
	
	@PutMapping("/author/{id}")
	public ResponseEntity<?> updateAuthor(@Valid @PathVariable int id, @Valid @RequestBody Author a, Errors errors) {
		BaseResponse response = new BaseResponse();
		if (errors.hasErrors()) {
			response.setCode(203);
			response.setMessage(errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining("</br>")));
			response.setStatu(false);
			return ResponseEntity.ok(response);
		}
		Boolean status = authorService.updateAuthor(id, a);
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
	
	@DeleteMapping("/author/{id}")
	public ResponseEntity<?> deleteAuthor(@Valid @PathVariable int id) {
		BaseResponse response = new BaseResponse();
		Boolean status = this.bookService.deleteBookByAuthorId(id);
		if(!status) {
			response.setCode(200);
			response.setMessage("Something went wrong (1)");
			response.setStatu(false);	
			return ResponseEntity.ok(response);
		}
		status = authorService.deleteAuthor(id);
		if(status) {
			response.setCode(200);
			response.setMessage("Successfully deleted");
			response.setStatu(true);
		}else {
			response.setCode(200);
			response.setMessage("Something went wrong");
			response.setStatu(false);
		}
		return ResponseEntity.ok(response);
		
	}

}
