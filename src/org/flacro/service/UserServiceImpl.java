package org.flacro.service;

import org.flacro.po.User;
import org.flacro.po.UserMapper;
import org.mybatis.guice.transactional.Transactional;

import com.google.inject.Inject;

public class UserServiceImpl implements UserService {

	@Inject
	private UserMapper usermapper;
	
	public Boolean login(String username, String password) {
		if (username != null && username.equals("flacro")) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public int createUser(User u) {
		try {
			return usermapper.insert(u);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public User getUser(int id) {
		try {
			User record = usermapper.selectByPrimaryKey(id);
			return record;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional
	public int updateUser(User u) {
		try{
		return usermapper.updateByPrimaryKey(u);
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int deleteUser(int userid) {
		try{
			return usermapper.deleteByPrimaryKey(userid);
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}
	
	

	
}
