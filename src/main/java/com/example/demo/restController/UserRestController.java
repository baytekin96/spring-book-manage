package com.example.demo.restController;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import com.example.demo.model.User;
import com.example.demo.restResponse.BaseResponse;
import com.example.demo.service.UserService;

@Controller
@ResponseBody
@RequestMapping("/api/users")
public class UserRestController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<?> getUsers() {
		BaseResponse response = new BaseResponse();
		List<User> userList = userService.getUsers();
		response.setStatu(true);
		response.setCode(200);
		response.getData().put("users", userList);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/user")
	public ResponseEntity<?> insertUser(@Valid @RequestBody User u, Errors errors) {
		BaseResponse response = new BaseResponse();
		if (errors.hasErrors()) {
			response.setCode(203);
			response.setMessage(errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining("</br>")));
			response.setStatu(false);
			return ResponseEntity.ok(response);
		}
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(u.getUser_password());  
		u.setUser_password(encodedPassword);
		Boolean status = userService.insertUser(u);
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
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUser(@Valid @PathVariable int id) {
		BaseResponse response = new BaseResponse();
		User user = this.userService.getUserById(id);
		if(user!=null) {
			response.setStatu(true);
			response.setCode(200);
			response.getData().put("user", user);
			return ResponseEntity.ok(response);			
		}
		response.setStatu(false);
		response.setCode(201);
		response.setMessage("User not found");
		return ResponseEntity.ok(response);	
	}
	@PutMapping("/user/{id}")
	public ResponseEntity<?> updateUser(@Valid @PathVariable int id, @Valid @RequestBody User u, Errors errors) {
		BaseResponse response = new BaseResponse();
		if (errors.hasErrors()) {
			response.setCode(203);
			response.setMessage(errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining("</br>")));
			response.setStatu(false);
			return ResponseEntity.ok(response);
		}
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(u.getUser_password());  
		u.setUser_password(encodedPassword);
		Boolean status = userService.updateUser(id, u);
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

	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@Valid @PathVariable int id) {
		BaseResponse response = new BaseResponse();
		Boolean status = userService.deleteUser(id);
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
