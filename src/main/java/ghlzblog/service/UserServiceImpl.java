package ghlzblog.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ghlzblog.dao.UserRepository;
import ghlzblog.po.User;
import ghlzblog.po.watch;
import ghlzblog.util.MD5Utils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
    
    @Override
    public void AddUser(String username,String password,String avatar,String email) {
        User user1 = new User();
        user1.setUsername(username);
        user1.setPassword(MD5Utils.code(password));
        user1.setAvatar(avatar);
        user1.setEmail(email);
        user1.setType(1);
        user1.setNickname(username);
        user1.setCreateTime(new Date());
        user1.setUpdateTime(new Date());
        user1.setWatch(0);
        user1.setWatched(0);
        userRepository.saveAndFlush(user1);
        return;
    }
    @Override
    public User checkUser(Long id) {
        User user = userRepository.findById(id);
        return user;
    }
    @Override
    public User checkUser(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }

	@Override
	public List<User> findwatch(List<watch> watch) {
		List<User> ul=new ArrayList<User>();
		for(int i=0;i<watch.size();i++) {
			User u=userRepository.findById(watch.get(i).getWatchedid());
			if(u!=null)
				ul.add(u);
		}
		return ul;
	}

	@Override
	public List<User> findwatched(List<watch> watch) {
		List<User> ul=new ArrayList<User>();
		for(int i=0;i<watch.size();i++) {
			User u=userRepository.findById(watch.get(i).getWatchid());
			if(u!=null)
				ul.add(u);
		}
		return ul;
	}
}
