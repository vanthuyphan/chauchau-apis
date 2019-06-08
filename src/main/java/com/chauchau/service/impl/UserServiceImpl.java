package com.chauchau.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
	@Autowired private JavaMailSender javaMailSender;
	@Override
	public void sendMail(String email) {
		SimpleMailMessage msg = new SimpleMailMessage();
        String[] emailTo = new String[1];
        emailTo[0] = email;
//        emailTo[2] = "";
		msg.setTo(emailTo);

        msg.setSubject("Test email Chau Chau");
        msg.setText("Hello World ");
        javaMailSender.send(msg);
	}
}
