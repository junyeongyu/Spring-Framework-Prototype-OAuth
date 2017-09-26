package com.junyeong.yu.prototype.oauth.service;

import java.util.List;

import com.junyeong.yu.prototype.oauth.model.AnotherUser;

public interface UserService {

	AnotherUser findById(long id);

	AnotherUser findByName(String name);
	
	void saveUser(AnotherUser user);
	
	void updateUser(AnotherUser user);
	
	void deleteUserById(long id);

	List<AnotherUser> findAllUsers();
	
	void deleteAllUsers();
	
	public boolean isUserExist(AnotherUser user);
	
}
