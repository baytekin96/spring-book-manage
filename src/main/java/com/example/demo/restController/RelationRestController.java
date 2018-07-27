package com.example.demo.restController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Relation;
import com.example.demo.service.RelationService;
import com.example.demo.service.UserService;

@Controller
@ResponseBody
@RequestMapping("/api/relations")
public class RelationRestController {

	@Autowired
	private RelationService relationService;

	@Autowired
	private UserService userService;

	@GetMapping
	public List<Relation> getRelations() {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String email = auth.getName();
//		int user_id = userService.getUserIdByEmail(email);
		List<Relation> relationList = relationService.getRelations(1);
		return relationList;
	}
	
	@GetMapping("/addToFavorite/book/{book_id}")
	public Map<String,Object> addToFavorite(@PathVariable() int book_id) {
		Map<String,Object> response = new HashMap<>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		int user_id = userService.getUserIdByEmail(email);
		if(!relationService.isUserFavourite(book_id, user_id)) {
			relationService.addToFavourite(book_id, user_id);
			response.put("status", true);
			response.put("message", "Book is added to your favourites");
		}else {
			response.put("status", false);
			response.put("message", "This book is already in your favourites");			
		}
		return response;
	}
	
	@GetMapping("/addToList/book/{book_id}")
	public Map<String,Object> addToList(@PathVariable() int book_id) {
		Map<String,Object> response = new HashMap<>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		int user_id = userService.getUserIdByEmail(email);
		if(!relationService.isUserWishList(book_id, user_id)) {
			relationService.addToWishList(book_id, user_id);
			response.put("status", true);
			response.put("message", "");
		}else {
			response.put("status", false);
			response.put("message", "This book is already in your wish-list");			
		}
		return response;
	}
	
	@DeleteMapping("/deleteFromFavourite/book/{book_id}")
	public Map<String,Object> deleteFavouriteBook(@PathVariable int book_id) {
		Map<String,Object> response = new HashMap<>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		int user_id = userService.getUserIdByEmail(email);
		if(relationService.isUserFavourite(book_id, user_id)) {
			relationService.deleteFavouriteBook(book_id, user_id);
			response.put("status", true);
			response.put("message", "");
		}else {
			response.put("status", false);
			response.put("message", "You do not have this book");			
		}
		return response;
		
	}
	
	@DeleteMapping("/deleteFromList/book/{book_id}")
	public Map<String,Object> deleteListBook(@PathVariable int book_id) {
		Map<String,Object> response = new HashMap<>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		int user_id = userService.getUserIdByEmail(email);
		if(relationService.isUserWishList(book_id, user_id)) {
			relationService.deleteListBook(book_id, user_id);
			response.put("status", true);
			response.put("message", "");
		}else {
			response.put("status", false);
			response.put("message", "You do not have this book");			
		}
		return response;
		
	}
}
