package com.m2s.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.m2s.dto.UserDTO;
import com.m2s.message.MessageStatus;
import com.m2s.service.UserService;


@RestController
@RequestMapping(value = "/api/v1")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;
	
	@PostMapping(value = { "create/user" })
	public MessageStatus CreateUser(@Valid @RequestBody UserDTO user,
			HttpServletRequest req, HttpServletResponse resp) {
		MessageStatus msg = new MessageStatus();
		logger.info("Inside controller");
		msg = userService.createUser(user);
		System.out.println("testing....");
		logger.info("controller exection completed");
		return msg;
	}
	
	@PutMapping(value = { "update/user" })
	public MessageStatus UpdateUser(@Valid @RequestBody UserDTO user,
			HttpServletRequest req, HttpServletResponse resp) {
		MessageStatus msg = new MessageStatus();
		msg = userService.updateUser(user);
		return msg;
	}

	@GetMapping("/users")
	public MessageStatus<List<UserDTO>> getAllUser(HttpServletRequest req,
			@RequestParam(value = "orderBy", required = false) Integer orderBy) {
		return userService.findAll(orderBy);
	}
	
	@GetMapping(value = "users/{userId}")
	public MessageStatus getUser(@PathVariable("userId") Integer userId,
			HttpServletRequest req, HttpServletResponse resp) {
		return userService.getUser(userId);
	}
	
	@DeleteMapping("delete/{id}")
	public MessageStatus deleteUser(@PathVariable("id") Long id) {
		return  userService.deleteUser(id);
	}
	
}
