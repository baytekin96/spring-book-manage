package com.example.demo.dao;

import java.util.List;

import com.example.demo.model.User;

public interface UserDao {

	public List<User> getUsers();

	public User getUser(String u);

	public User getUserById(int id);

	public Boolean insertUser(User u);

	public Boolean updateUser(int id, User u);

	public Boolean deleteUser(int id);

	public int getUserIdByEmail(String email);
}
