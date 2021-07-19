package com.project0.service;

import java.util.List;

import com.project0.exception.BusinessException;
import com.project0.model.User;

public interface UserService {
	
	public List<User> getUsersByName(String name) throws BusinessException;
	public User getUserByName(String name) throws BusinessException;
	public User getUserById(int userId)throws BusinessException;

	public User createUser(User user) throws BusinessException;
	public List<User> getAllUsers() throws BusinessException;
	public void deleteUser(int userId) throws BusinessException;

	
}
