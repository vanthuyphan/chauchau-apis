package com.chauchau.rest;

import com.chauchau.model.Respond;
import com.chauchau.model.User;
import com.chauchau.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = GET, value = "/user/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public User loadById(@PathVariable Long userId) {
		return this.userService.findById(userId);
	}

	@RequestMapping(method = GET, value = "/user/all")
	@PreAuthorize("hasRole('ADMIN')")
	public List<User> loadAll() {
		return this.userService.findAll();
	}

	/*
	 * We are not using userService.findByUsername here(we could), so it is good
	 * that we are making sure that the user has role "ROLE_USER" to access this
	 * endpoint.
	 */
	@RequestMapping("/whoami")
	@PreAuthorize("hasRole('USER')")
	public User user(Principal user) {
		return this.userService.findByUsername(user.getName());
	}

	@RequestMapping(method = RequestMethod.POST, value = "/findPhone")
	// @PreAuthorize("hasRole('ADMIN')")
	public User findUserByPhone(@RequestBody User user) {
		User userPhone = userService.findUserbyPhone(user.getPhoneNumber());
		return userPhone;
		// this.userService.createUser(user);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/register")
	// @PreAuthorize("hasRole('ADMIN')")
	public User createUser(@RequestBody User user) {
		User newUser =  this.userService.createUser(user);
		return newUser;

	}
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	 @PreAuthorize("hasRole('USER')")
	public Respond updateUser(@RequestBody User user) {
		int success =  userService.updateUser(user);
		Respond value= new Respond();
		if(success>0){
			value.setMessage("Update success user");
		}else{
			value.setMessage("Update fail");
		}
		return value;
		// this.userService.createUser(user);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/delete")
	 @PreAuthorize("hasRole('USER')")
	public Respond delete(@RequestBody User user) {
		int delete =  userService.delete(user);
		Respond value= new Respond();
		if(delete>0){
			value.setMessage("User: "+user.getId() + "deleted");
		}else{
			value.setMessage("Fail. Try to delete again");
		}
		return value;
	}
	@RequestMapping(method = RequestMethod.POST, value = "/sendEmail")
	 @PreAuthorize("hasRole('USER')")
	public void sendEmail(@RequestBody String toEmail) {
		userService.sendMail(toEmail);
	}
}
