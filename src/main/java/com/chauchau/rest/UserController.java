package com.chauchau.rest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chauchau.model.Mail;
import com.chauchau.model.Respond;
import com.chauchau.model.User;
import com.chauchau.service.EmailService;
import com.chauchau.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	protected final Log LOGGER = LogFactory.getLog(getClass());

	@Autowired
	private UserService userService;
	@Autowired
	private EmailService emailService;

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
	//Test connect from front end
	@RequestMapping(method = GET, value = "/user/listall")
	public List<User> listAllUser() {
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
	
	@DeleteMapping(value = "/delete")
	public String delete(@RequestBody String jsonUser) {
		List<Integer> idsDelete = new ArrayList<>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode actualObj = mapper.readTree(jsonUser);
			JsonNode jsonNode1 = actualObj.get("listUserId");
			idsDelete = new ObjectMapper().convertValue(jsonNode1, ArrayList.class);
		} catch (Exception e) {
			LOGGER.info("Cannot parse data: " + jsonUser);
			LOGGER.info(e.toString());
		}
		int delete = userService.delete(idsDelete);
		String message;
		if (delete > 0) {
			message = "User deleted";
		} else {
			message = "Fail. Try to delete again";
		}
		return message;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/sendEmail")
	@PreAuthorize("hasRole('USER')")
	public void sendEmail(@RequestBody Mail mail) {
		emailService.sendEmail(mail);

	}
}
