package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.UserDao;
import com.example.demo.model.User;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public List<User> getUsers() {
		return userDao.getUsers();
	}

	@Override
	public User getUser(String u) {
		return userDao.getUser(u);
	}
	@Override
	public User getUserById(int id) {
		return userDao.getUserById(id);
	}
	@Override
	public Boolean insertUser(User u) {
		return userDao.insertUser(u);
	}

	@Override
	public Boolean updateUser(int id, User u) {
		return userDao.updateUser(id, u);
	}

	@Override
	public Boolean deleteUser(int id) {
		return userDao.deleteUser(id);
	}

	@Override
	public int getUserIdByEmail(String email) {
		return userDao.getUserIdByEmail(email);
	}
}
