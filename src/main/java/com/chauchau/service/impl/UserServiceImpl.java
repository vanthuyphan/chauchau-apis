package com.chauchau.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chauchau.model.User;
import com.chauchau.repository.UserRepository;
import com.chauchau.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername( String username ) throws UsernameNotFoundException {
        User u = userRepository.findByUsername( username );
        return u;
    }

    public User findById( Long id ) throws AccessDeniedException {
        User u = userRepository.findOne( id );
        return u;
    }

    public List<User> findAll() throws AccessDeniedException {
        List<User> result = userRepository.findAll();
        return result;
    }

	

	@Override
	public User createUser(User user) {
		// TODO Auto-generated method stub
//		userRepository.createUser(user.getEmail(),user.isEnabled(),user.getFirstName(),user.getId());
		return userRepository.save(user);
	}

	@Override
	public User findUserbyPhone(String phone) {
		// TODO Auto-generated method stub
		User u = userRepository.getUserbyPhone(phone);
        return u;
	}

	@Override
	public int updateUser(User user) {
		int returnValue = userRepository.updateUser(user.getEmail(),user.getFirstName(),user.getId());
		return returnValue;
	}

	@Override
	public int delete(User user) {
		int returnValue = userRepository.deleteUser(user.getId());
		return returnValue;
	}
	
}
