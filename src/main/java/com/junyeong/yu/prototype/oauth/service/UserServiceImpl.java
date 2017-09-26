package com.junyeong.yu.prototype.oauth.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.junyeong.yu.prototype.oauth.model.AnotherUser;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	private static final AtomicLong counter = new AtomicLong();
	
	private static List<AnotherUser> users;
	
	static{
		users= populateDummyUsers();
	}

	public List<AnotherUser> findAllUsers() {
		return users;
	}
	
	public AnotherUser findById(long id) {
		for(AnotherUser user : users){
			if(user.getId() == id){
				return user;
			}
		}
		return null;
	}
	
	public AnotherUser findByName(String name) {
		for(AnotherUser user : users){
			if(user.getName().equalsIgnoreCase(name)){
				return user;
			}
		}
		return null;
	}
	
	public void saveUser(AnotherUser user) {
		user.setId(counter.incrementAndGet());
		users.add(user);
	}

	public void updateUser(AnotherUser user) {
		int index = users.indexOf(user);
		users.set(index, user);
	}

	public void deleteUserById(long id) {
		
		for (Iterator<AnotherUser> iterator = users.iterator(); iterator.hasNext(); ) {
			AnotherUser user = iterator.next();
		    if (user.getId() == id) {
		        iterator.remove();
		    }
		}
	}

	public boolean isUserExist(AnotherUser user) {
		return findByName(user.getName())!=null;
	}
	
	public void deleteAllUsers(){
		users.clear();
	}

	private static List<AnotherUser> populateDummyUsers(){
		List<AnotherUser> users = new ArrayList<AnotherUser>();
		users.add(new AnotherUser(counter.incrementAndGet(),"Sam",30, 70000));
		users.add(new AnotherUser(counter.incrementAndGet(),"Tom",40, 50000));
		users.add(new AnotherUser(counter.incrementAndGet(),"Jerome",45, 30000));
		users.add(new AnotherUser(counter.incrementAndGet(),"Silvia",50, 40000));
		return users;
	}

}
