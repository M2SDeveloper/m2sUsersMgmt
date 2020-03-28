package com.m2s.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.m2s.entities.User;
import com.m2s.entities.UserDTO;
import com.m2s.message.MessageStatus;

public interface UserService {
	public MessageStatus updateUser(UserDTO user);
	public MessageStatus createUser(UserDTO user);
	public MessageStatus<List<UserDTO>> findAll(Integer orderId);
	public MessageStatus synchToDB();
	public UserDTO getUser(Integer userId);
}
