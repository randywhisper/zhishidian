package org.flacro.service;

import java.util.List;

import org.flacro.po.User;


public interface UserService {
	Boolean login(String username, String password);
	int createUser(User u);
	User getUser(int id);
	int updateUser(User u);
	int deleteUser(int userid);
}
