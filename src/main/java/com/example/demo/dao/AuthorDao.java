package com.example.demo.dao;

import java.util.List;
import com.example.demo.model.Author;

public interface AuthorDao {
	
	public List<Author> getAuthors();
	
	public Author getAuthorById(int id);
	
	public Boolean insertAuthor(Author a);
	
	public Boolean updateAuthor(int id, Author a);
	
	public Boolean deleteAuthor(int id);
}
