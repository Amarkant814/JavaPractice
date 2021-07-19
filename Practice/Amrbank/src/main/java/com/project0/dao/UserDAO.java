package com.project0.dao;

import java.util.List;

import com.project0.exception.BusinessException;
import com.project0.model.User;



public interface UserDAO {
	
	public List<User> getUsersByName(String name) throws BusinessException;
	public User getUserByName(String name) throws BusinessException;
	public User getUserById(int userId)throws BusinessException;

	public User createUser(User user) throws BusinessException;
	public List<User> getAllUsers() throws BusinessException;
	public void deleteUser(int userId) throws BusinessException;

}
