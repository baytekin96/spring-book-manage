package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.model.Book;

@Component
public class BookDaoImpl implements BookDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Book> getBooks() {
		String sql = "SELECT * FROM book b LEFT JOIN author a ON (b.author_id = a.author_id) ";
		List<Book> books = new ArrayList<Book>();

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map row : rows) {
			Book book = new Book();
			book.setBook_id((int) (row.get("book_id")));
			book.setBook_name((String) (row.get("book_name")));
			book.setAuthor_id((int) (row.get("author_id")));
			book.setAuthor_name((String)row.get("author_name"));
			book.setAuthor_surname((String)row.get("author_surname"));
			books.add(book);
		}
		return books;
	}

	@Override
	public List<Book> getBooksByAuthorId(int author_id){
		String sql = "SELECT * FROM book b LEFT JOIN author a ON (b.author_id = a.author_id )WHERE author_id='" + (int) author_id + "'";
		List<Book> books = new ArrayList<Book>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map row : rows) {
			Book book = new Book();
			book.setBook_id((int) (row.get("book_id")));
			book.setBook_name((String) (row.get("book_name")));
			book.setAuthor_id((int) (row.get("author_id")));
			book.setAuthor_name((String)row.get("author_name"));
			book.setAuthor_surname((String)row.get("author_surname"));
			books.add(book);
		}
		return books;
	}
	
	@Override
	public Book getBookById(int book_id) {
		String sql = "SELECT * FROM book WHERE book_id='" + (int) book_id + "'";
		Map<String, Object> row = null;
		try {
			row = jdbcTemplate.queryForMap(sql);
		}catch(Exception e) {
			return null;
		}
		if(row==null) {
			return null;
		}
		Book book = new Book();
		book.setBook_id((int) (row.get("book_id")));
		book.setBook_name((String) (row.get("book_name")));
		book.setAuthor_id((int) (row.get("author_id")));
		return book;
	}

	@Override
	public Boolean insertBook(Book b) {
		String sql = "INSERT INTO book " + "(book_name, author_id, book_added_date) VALUES (?, ?, ?)";
		int result = jdbcTemplate.update(sql, new Object[] { b.getBook_name(), b.getAuthor_id(), b.getBook_added_date() });
		if(result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean updateBook(int id, Book b) {
		String sql = "UPDATE book SET book_name= '" + b.getBook_name() + "', author_id='" + b.getAuthor_id()
				+ "' WHERE book_id=" + id;
		int result = jdbcTemplate.update(sql);
		if(result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean deleteBook(int id) {
		String sql = "DELETE FROM book WHERE book_id='" + id + "'";
		int result = jdbcTemplate.update(sql);
		if(result > 0) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public Boolean deleteBookByAuthorId(int author_id){
		String sql = "DELETE FROM book WHERE author_id='" + (int) author_id + "'";
		int result = jdbcTemplate.update(sql);
		if(result > 0) {
			return true;
		} else {
			return false;
		}
	}
}
